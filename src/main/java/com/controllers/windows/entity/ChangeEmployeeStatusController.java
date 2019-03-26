package com.controllers.windows.entity;

import com.controllers.requests.EmployeeStatusController;
import com.controllers.windows.menu.MenuController;
import com.models.EmployeeStatusEntity;
import com.tools.Constant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ChangeEmployeeStatusController extends MenuController {

    @Autowired
    HttpResponse response;

    private MenuController menuController;
    private boolean change;
    private EmployeeStatusEntity employeeStatusEntity;
    private int statusCode;

    @FXML
    private Label label_PaneName;
    @FXML
    private TextField textField_Name;
    @FXML
    private CheckBox checkBox_WorkEnable;
    @FXML
    private Button button_Save;
    @FXML
    private Button button_Cancel;

    public void init(MenuController menuController) {
        this.menuController = menuController;
    }


    public void save(ActionEvent event) throws IOException {
        if (change) {
            boolean result = Constant.questionOkCancel("Do you really want to change employee status "
                    + textField_Name.getText() + "?");
            if (result) {
                employeeStatusEntity = new EmployeeStatusEntity();
                employeeStatusEntity.setId(Integer.parseInt(Constant
                        .getMapByName(Constant.getMiscellaneousMapName()).get("employeeStatus").toString()));
                employeeStatusEntity.setName(textField_Name.getText());
                employeeStatusEntity.setWorkEnable(checkBox_WorkEnable.isSelected());
                response = EmployeeStatusController.changeEmployeeStatus(employeeStatusEntity);
                statusCode = response.getStatusLine().getStatusCode();
                if (checkStatusCode(statusCode)) {
                    TableView<EmployeeStatusEntity> tableView = (TableView<EmployeeStatusEntity>) this.menuController.getStage().getScene().lookup("#tableView_EmployeeStatus");
                    for (EmployeeStatusEntity employeeStatus : tableView.getItems()) {
                        if (employeeStatus.getId() == employeeStatusEntity.getId()) {
                            employeeStatus.setName(employeeStatusEntity.getName());
                            employeeStatus.setWorkEnable(employeeStatusEntity.isWorkEnable());
                        }
                    }
                    tableView.refresh();
                    Constant.getAlert(null, "Employee status changed!", Alert.AlertType.INFORMATION);
                    TextField textField = (TextField) this.menuController.getStage().getScene().lookup("#textField_Name");
                    textField.clear();
                    Constant.getMapByName(Constant.getMiscellaneousMapName()).delete("employeeStatus");
                    StackPane stackPane = (StackPane) this.menuController.getStage().getScene().lookup("#stackPane_EmployeeStatusChange");
                    stackPane.setDisable(true);
                    stackPane.setVisible(false);
                }
            }
        } else {
            if (textField_Name.getText() != null) {
                employeeStatusEntity = new EmployeeStatusEntity();
                employeeStatusEntity.setName(textField_Name.getText());
                employeeStatusEntity.setWorkEnable(checkBox_WorkEnable.isSelected());
                response = EmployeeStatusController.createEmployeeStatus(employeeStatusEntity);
                statusCode = response.getStatusLine().getStatusCode();
                if (checkStatusCode(statusCode)) {
                    Constant.getAlert(null, "Employee status saved!", Alert.AlertType.INFORMATION);
                    TableView<EmployeeStatusEntity> tableView = (TableView<EmployeeStatusEntity>) this.menuController.getStage().getScene().lookup("#tableView_EmployeeStatus");
                    tableView.getItems().add(employeeStatusEntity);
                    TextField textField = (TextField) this.menuController.getStage().getScene().lookup("#textField_Name");
                    textField.clear();
                    StackPane stackPane = (StackPane) this.menuController.getStage().getScene().lookup("#stackPane_EmployeeStatusChange");
                    stackPane.setDisable(true);
                    stackPane.setVisible(false);
                }
            }
        }
    }

    public void cancel() {
        TextField textField = (TextField) this.menuController.getStage().getScene().lookup("#textField_Name");
        textField.clear();
        StackPane stackPane = (StackPane) this.menuController.getStage().getScene().lookup("#stackPane_EmployeeStatusChange");
        stackPane.setDisable(true);
        stackPane.setVisible(false);
    }

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public Label getLabel_PaneName() {
        return label_PaneName;
    }

    public void setLabel_PaneName(Label label_PaneName) {
        this.label_PaneName = label_PaneName;
    }

    public TextField getTextField_Name() {
        return textField_Name;
    }

    public void setTextField_Name(TextField textField_Name) {
        this.textField_Name = textField_Name;
    }

    public CheckBox getCheckBox_WorkEnable() {
        return checkBox_WorkEnable;
    }

    public void setCheckBox_WorkEnable(CheckBox checkBox_WorkEnable) {
        this.checkBox_WorkEnable = checkBox_WorkEnable;
    }
}
