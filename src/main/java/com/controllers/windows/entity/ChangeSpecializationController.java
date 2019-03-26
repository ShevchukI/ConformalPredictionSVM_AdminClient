package com.controllers.windows.entity;

import com.controllers.requests.SpecializationController;
import com.controllers.windows.menu.MenuController;
import com.models.SpecializationEntity;
import com.tools.Constant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ChangeSpecializationController extends MenuController {
    @Autowired
    HttpResponse response;

    private MenuController menuController;
    private boolean change;
    private SpecializationEntity specializationEntity;
    private int statusCode;

    @FXML
    private Label label_PaneName;
    @FXML
    private TextField textField_Name;
    @FXML
    private Button button_Save;
    @FXML
    private Button button_Cancel;

    public void init(MenuController menuController) {
        this.menuController = menuController;
    }

    public void save(ActionEvent event) throws IOException {
        if (change) {
            boolean result = Constant.questionOkCancel("Do you really want to change specialization "
                    + textField_Name.getText() + "?");
            if (result) {
                specializationEntity = new SpecializationEntity();
                specializationEntity.setId(Integer.parseInt(Constant
                        .getMapByName(Constant.getMiscellaneousMapName()).get("specialization").toString()));
                specializationEntity.setName(textField_Name.getText());
                response = SpecializationController.changeEmployeeStatus(specializationEntity);
                statusCode = response.getStatusLine().getStatusCode();
                if (checkStatusCode(statusCode)) {
                    TableView<SpecializationEntity> tableView = (TableView<SpecializationEntity>) this.menuController.getStage().getScene().lookup("#tableView_Specialization");
                    for (SpecializationEntity specialization : tableView.getItems()) {
                        if (specialization.getId() == specializationEntity.getId()) {
                            specialization.setName(specializationEntity.getName());
                        }
                    }
                    tableView.refresh();
                    Constant.getAlert(null, "Specialization changed!", Alert.AlertType.INFORMATION);
                    TextField textField = (TextField) this.menuController.getStage().getScene().lookup("#textField_Name");
                    textField.clear();
                    Constant.getMapByName(Constant.getMiscellaneousMapName()).delete("specialization");
                    StackPane stackPane = (StackPane) this.menuController.getStage().getScene().lookup("#stackPane_SpecializationChange");
                    stackPane.setDisable(true);
                    stackPane.setVisible(false);
                }
            }
        } else {
            if (textField_Name.getText() != null) {
                specializationEntity = new SpecializationEntity();
                specializationEntity.setName(textField_Name.getText());
                response = SpecializationController.createSpecialization(specializationEntity);
                statusCode = response.getStatusLine().getStatusCode();
                if (checkStatusCode(statusCode)) {
                    Constant.getAlert(null, "Specialization saved!", Alert.AlertType.INFORMATION);
                    TableView<SpecializationEntity> tableView = (TableView<SpecializationEntity>) this.menuController.getStage().getScene().lookup("#tableView_Specialization");
                    tableView.getItems().add(specializationEntity);
                    TextField textField = (TextField) this.menuController.getStage().getScene().lookup("#textField_Name");
                    textField.clear();
                    StackPane stackPane = (StackPane) this.menuController.getStage().getScene().lookup("#stackPane_SpecializationChange");
                    stackPane.setDisable(true);
                    stackPane.setVisible(false);
                }
            }
        }
    }

    public void cancel() {
        TextField textField = (TextField) this.menuController.getStage().getScene().lookup("#textField_Name");
        textField.clear();
        StackPane stackPane = (StackPane) this.menuController.getStage().getScene().lookup("#stackPane_SpecializationChange");
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
}
