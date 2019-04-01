package com.controllers.windows.stack.entity;

import com.controllers.windows.menu.MenuController;
import com.entity.DoctorEntity;
import com.entity.EmployeeStatusEntity;
import com.entity.SpecializationEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class ChangeDoctorController {
    @Autowired
    HttpResponse response;

    private MenuController menuController;
    private DoctorEntity doctorEntity;
    private ArrayList<SpecializationEntity> specializations;
    private ObservableList<SpecializationEntity> specializationEntities;
    private ArrayList<EmployeeStatusEntity> employeeStatus;
    private ObservableList<EmployeeStatusEntity> employeeStatusEntities;
    private boolean change;
    private Tooltip tooltip_ErrorName;
    private Tooltip tooltip_ErrorSurname;
    private Tooltip tooltip_ErrorSpecialization;
    private Tooltip tooltip_ErrorTelephone;
    private Tooltip tooltip_ErrorEmail;
    private Tooltip tooltip_ErrorStatus;
    private final String borderStatusRed = "-fx-border-color: red";
    private final String borderStatusInherit = "-fx-border-color: inherit";

    @FXML
    private Label label_PaneName;
    @FXML
    private TextField textField_Name;
    @FXML
    private Tooltip tooltip_Name;
    @FXML
    private TextField textField_Surname;
    @FXML
    private Tooltip tooltip_Surname;
    @FXML
    private ComboBox<SpecializationEntity> comboBox_Specialization;
    @FXML
    private Tooltip tooltip_Specialization;
    @FXML
    private TextField textField_Telephone;
    @FXML
    private Tooltip tooltip_Telephone;
    @FXML
    private TextField textField_Email;
    @FXML
    private Tooltip tooltip_Email;
    @FXML
    private ComboBox<EmployeeStatusEntity> comboBox_Status;

    public void init(MenuController menuController, ArrayList<EmployeeStatusEntity> employeeStatus, ArrayList<SpecializationEntity> specializations) {
        this.menuController = menuController;
        this.employeeStatus = employeeStatus;
        this.specializations = specializations;
        specializationEntities = FXCollections.observableList(this.specializations);
        comboBox_Specialization.setItems(specializationEntities);
        comboBox_Specialization.setCellFactory(new Callback<ListView<SpecializationEntity>, ListCell<SpecializationEntity>>() {
            @Override
            public ListCell<SpecializationEntity> call(ListView<SpecializationEntity> p) {
                ListCell cell = new ListCell<SpecializationEntity>() {
                    @Override
                    protected void updateItem(SpecializationEntity item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText("");
                        } else {
                            setText(item.getName());
                        }
                    }
                };
                return cell;
            }
        });
        comboBox_Specialization.setButtonCell(new ListCell<SpecializationEntity>() {
            @Override
            protected void updateItem(SpecializationEntity t, boolean bln) {
                super.updateItem(t, bln);
                if (bln) {
                    setText("");
                } else {
                    setText(t.getName());
                }
            }
        });
        comboBox_Specialization.setVisibleRowCount(5);
        comboBox_Specialization.getSelectionModel().select(0);

        employeeStatusEntities = FXCollections.observableList(this.employeeStatus);
        comboBox_Status.setItems(employeeStatusEntities);
        comboBox_Status.setCellFactory(new Callback<ListView<EmployeeStatusEntity>, ListCell<EmployeeStatusEntity>>() {
            @Override
            public ListCell<EmployeeStatusEntity> call(ListView<EmployeeStatusEntity> p) {
                ListCell cell = new ListCell<EmployeeStatusEntity>() {
                    @Override
                    protected void updateItem(EmployeeStatusEntity item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText("");
                        } else {
                            setText(item.getName());
                        }
                    }
                };
                return cell;
            }
        });
        comboBox_Status.setButtonCell(new ListCell<EmployeeStatusEntity>() {
            @Override
            protected void updateItem(EmployeeStatusEntity t, boolean bln) {
                super.updateItem(t, bln);
                if (bln) {
                    setText("");
                } else {
                    setText(t.getName());
                }
            }
        });
        comboBox_Status.setVisibleRowCount(5);
        comboBox_Status.getSelectionModel().select(0);
        tooltip_ErrorName = new Tooltip("Required:\n- not empty\n- minimum 2 characters");
        tooltip_ErrorSurname = new Tooltip("Required:\n- not empty\n- minimum 2 characters");
        tooltip_ErrorTelephone = new Tooltip("Maximum 15 characters");
        tooltip_ErrorEmail = new Tooltip("");
    }

    public void save(ActionEvent event) {
//        if (change) {
//            if (checkFields()) {
//                changeCurrent();
//            }
//        } else {
//            if (checkFields()) {
//                addNew();
//            }
//        }
    }

    public void cancel(ActionEvent event) {
    }

//    private void changeCurrent() {
//        boolean result = Constant.questionOkCancel("Do you really want to change Doctor "
//                + textField_Name.getText() + "?");
//        if (result) {
//            doctorEntity = new DoctorEntity();
//            doctorEntity.setId(Integer.parseInt(Constant
//                    .getMapByName(Constant.getMiscellaneousMapName()).get("doctor").toString()));
//            doctorEntity.setName(textField_Name.getText());
//            doctorEntity.setSurname(textField_Surname.getText());
//            doctorEntity.setSpecializationEntity(comboBox_Specialization.getSelectionModel().getSelectedItem());
//            doctorEntity.setTelephone(textField_Telephone.getText());
//            doctorEntity.setEmail(textField_Email.getText());
//            doctorEntity.setEmployeeStatusEntity(comboBox_Status.getSelectionModel().getSelectedItem());
//            response = ModelDeveloperController.createModelDeveloper(modelDeveloperEntity);
//            setStatusCode(response.getStatusLine().getStatusCode());
//            if (checkStatusCode(getStatusCode())) {
//                TableView<ModelDeveloperEntity> tableView = (TableView<ModelDeveloperEntity>) this.menuController.getStage().getScene().lookup("#tableView_ModelDeveloper");
//                for (ModelDeveloperEntity modelDeveloper : tableView.getItems()) {
//                    if (modelDeveloper.getId() == modelDeveloperEntity.getId()) {
//                        modelDeveloper.setName(modelDeveloperEntity.getName());
//                        modelDeveloper.setSurname(modelDeveloperEntity.getSurname());
//                        modelDeveloper.setTelephone(modelDeveloperEntity.getTelephone());
//                        modelDeveloper.setEmail(modelDeveloperEntity.getEmail());
//                        modelDeveloper.setEmployeeStatus(modelDeveloperEntity.getEmployeeStatus());
//                    }
//                }
//                tableView.refresh();
//                Constant.getAlert(null, "Employee status changed!", Alert.AlertType.INFORMATION);
//                TextField textField = (TextField) this.menuController.getStage().getScene().lookup("#textField_Name");
//                textField.clear();
//                Constant.getMapByName(Constant.getMiscellaneousMapName()).delete("modelDeveloper");
//                StackPane stackPane = (StackPane) this.menuController.getStage().getScene().lookup("#stackPane_ModelDeveloperChange");
//                stackPane.setDisable(true);
//                stackPane.setVisible(false);
//            }
//        }
//    }
}
