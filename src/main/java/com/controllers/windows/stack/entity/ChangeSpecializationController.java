package com.controllers.windows.stack.entity;

import com.controllers.requests.SpecializationController;
import com.controllers.windows.menu.MainMenuController;
import com.controllers.windows.menu.MenuController;
import com.controllers.windows.tab.SpecializationTabController;
import com.entity.SpecializationEntity;
import com.tools.Constant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.http.HttpResponse;

import java.io.IOException;

public class ChangeSpecializationController extends MenuController{

    private MenuController menuController;
    private SpecializationEntity specializationEntity;
    private boolean change;

    @FXML
    private Label label_PaneName;
    @FXML
    private TextField textField_Name;
    @FXML
    private Button button_Save;
    @FXML
    private Button button_Cancel;

    public void init(MenuController menuController){
        this.menuController = menuController;
        button_Save.setGraphic(Constant.okIcon());
        button_Cancel.setGraphic(Constant.cancelIcon());
    }

    public void create() {
        change = false;
        label_PaneName.setText("Add New Specialization");
        textField_Name.clear();
    }

    public void change(SpecializationEntity specialization) {
        change = true;
        specializationEntity = specialization;
        label_PaneName.setText("Change Specialization");
        textField_Name.setText(specializationEntity.getName());
    }

    public void save(ActionEvent event) throws IOException {
        if (change) {
            boolean result = Constant.questionOkCancel("Do you really want to change specialization "
                    + textField_Name.getText() + "?");
            if (result) {
                specializationEntity.setName(textField_Name.getText());
                HttpResponse response = SpecializationController.changeSpecialization(specializationEntity);
                setStatusCode(response.getStatusLine().getStatusCode());
                if (checkStatusCode(getStatusCode())) {
                    TableView<SpecializationEntity> tableView = SpecializationTabController.getSpecializationTable();
                    for (SpecializationEntity specialization : tableView.getItems()) {
                        if (specialization.getId() == specializationEntity.getId()) {
                            specialization.setName(specializationEntity.getName());
                        }
                    }
                    tableView.refresh();
                    Constant.getAlert(null, "Specialization changed!", Alert.AlertType.INFORMATION);
                    close();
                }
            }
        } else {
            if (textField_Name.getText() != null) {
                specializationEntity = new SpecializationEntity();
                specializationEntity.setName(textField_Name.getText());
                HttpResponse response = SpecializationController.createSpecialization(specializationEntity);
                setStatusCode(response.getStatusLine().getStatusCode());
                if (checkStatusCode(getStatusCode())) {
                    specializationEntity.setId(Integer.parseInt(Constant.responseToString(response)));
                    SpecializationTabController.getSpecializationTable().getItems().add(specializationEntity);
                    SpecializationTabController.getSpecializationTable().refresh();
                    Constant.getAlert(null, "Specialization saved!", Alert.AlertType.INFORMATION);
                    close();
                }
            }
        }
    }

    private void close() {
        textField_Name.clear();
        MainMenuController.deactivateAllStackPane();
    }

    public void cancel() {
        boolean result = Constant.questionOkCancel("Do you want the cancel to operation?");
        if (result) {
            close();
        }
    }
}
