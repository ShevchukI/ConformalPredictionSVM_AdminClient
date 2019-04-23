package com.controllers.windows.stack.entity;

import com.controllers.requests.EmployeeStatusController;
import com.controllers.windows.menu.MainMenuController;
import com.controllers.windows.menu.MenuController;
import com.controllers.windows.tab.EmployeeStatusTabController;
import com.entity.EmployeeStatusEntity;
import com.tools.Constant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangeEmployeeStatusController extends MenuController implements Initializable{

    private static EmployeeStatusEntity employeeStatusEntity;
    private static boolean change;
    private static Label paneName;
    private static TextField name;
    private static CheckBox workEnable;


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneName = label_PaneName;
        name = textField_Name;
        workEnable = checkBox_WorkEnable;
    }

    public static void create(){
        change = false;
        paneName.setText("Add New Employee Status");
        name.clear();
        workEnable.setSelected(true);
    }

    public static void change(EmployeeStatusEntity employeeStatus){
        change = true;
        employeeStatusEntity = employeeStatus;
        paneName.setText("Change Employee Status");
        name.setText(employeeStatus.getName());
        workEnable.setSelected(employeeStatus.isWorkEnable());
    }

    public void save(ActionEvent event) throws IOException {
        if (change) {
            boolean result = Constant.questionOkCancel("Do you really want to change employee status "
                    + textField_Name.getText() + "?");
            if (result) {
                employeeStatusEntity.setName(textField_Name.getText());
                employeeStatusEntity.setWorkEnable(checkBox_WorkEnable.isSelected());
                HttpResponse response = EmployeeStatusController.changeEmployeeStatus(employeeStatusEntity);
                setStatusCode(response.getStatusLine().getStatusCode());
                if (checkStatusCode(getStatusCode())) {
                    TableView<EmployeeStatusEntity> tableView = EmployeeStatusTabController.getEmployeeStatusTable();
                    for (EmployeeStatusEntity employeeStatus : tableView.getItems()) {
                        if (employeeStatus.getId() == employeeStatusEntity.getId()) {
                            employeeStatus.setName(employeeStatusEntity.getName());
                            employeeStatus.setWorkEnable(employeeStatusEntity.isWorkEnable());
                        }
                    }
                    tableView.refresh();
                    Constant.getAlert(null, "Employee status changed!", Alert.AlertType.INFORMATION);
                    close();
                }
            }
        } else {
            if (textField_Name.getText() != null) {
                employeeStatusEntity = new EmployeeStatusEntity();
                employeeStatusEntity.setName(textField_Name.getText());
                employeeStatusEntity.setWorkEnable(checkBox_WorkEnable.isSelected());
                HttpResponse response = EmployeeStatusController.createEmployeeStatus(employeeStatusEntity);
                setStatusCode(response.getStatusLine().getStatusCode());
                if (checkStatusCode(getStatusCode())) {
                    employeeStatusEntity.setId(Integer.parseInt(Constant.responseToString(response)));
                    EmployeeStatusTabController.getEmployeeStatusTable().getItems().add(employeeStatusEntity);
                    EmployeeStatusTabController.getEmployeeStatusTable().refresh();
                    Constant.getAlert(null, "Employee status saved!", Alert.AlertType.INFORMATION);
                    close();
                }
            }
        }
    }

    private void close() {
        name.clear();
        workEnable.setSelected(true);
        MainMenuController.deactivateAllStackPane();
    }

    public void cancel() {
        boolean result = Constant.questionOkCancel("Do you want the cancel to operation?");
        if (result) {
            close();
        }
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
