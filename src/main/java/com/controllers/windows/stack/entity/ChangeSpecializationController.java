package com.controllers.windows.stack.entity;

import com.controllers.requests.SpecializationController;
import com.controllers.windows.menu.MainMenuController;
import com.controllers.windows.menu.MenuController;
import com.controllers.windows.tab.SpecializationTabController;
import com.entity.SpecializationEntity;
import com.tools.Constant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangeSpecializationController extends MenuController implements Initializable {

    private static SpecializationEntity specializationEntity;
    private static boolean change;
    private static Label paneName;
    private static TextField name;

    @FXML
    private Label label_PaneName;
    @FXML
    private TextField textField_Name;
    @FXML
    private Button button_Save;
    @FXML
    private Button button_Cancel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneName = label_PaneName;
        name = textField_Name;
    }

    public static void create() {
        change = false;
        paneName.setText("Add New Specialization");
        name.clear();
    }

    public static void change(SpecializationEntity specialization) {
        change = true;
        specializationEntity = specialization;
        paneName.setText("Change Specialization");
        name.setText(specializationEntity.getName());
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
//                    SpecializationTabController.getSpecializationTable().getItems().add(specializationEntity);
                    SpecializationTabController.getSpecializationTable().getItems().add(specializationEntity);
                    SpecializationTabController.getSpecializationTable().refresh();
                    Constant.getAlert(null, "Specialization saved!", Alert.AlertType.INFORMATION);
                    close();
//                    MainMenuController.deactivateAllStackPane();
                }
            }
        }
    }

    private void close() {
        name.clear();
        MainMenuController.deactivateAllStackPane();
    }

    public void cancel() {
        boolean result = Constant.questionOkCancel("Do you want the cancel to operation?");
        if (result) {
            close();
        }
    }
}
