package com.controllers.windows.menu;

import com.controllers.windows.stack.entity.ChangeDoctorController;
import com.controllers.windows.stack.entity.ChangeEmployeeStatusController;
import com.controllers.windows.stack.entity.ChangeModelDeveloperController;
import com.controllers.windows.stack.entity.ChangeSpecializationController;
import com.controllers.windows.stack.entityInfo.DoctorInfoController;
import com.controllers.windows.stack.entityInfo.ModelDeveloperInfoController;
import com.controllers.windows.tab.DoctorTabController;
import com.controllers.windows.tab.EmployeeStatusTabController;
import com.controllers.windows.tab.ModelDeveloperTabController;
import com.controllers.windows.tab.SpecializationTabController;
import com.entity.DoctorEntity;
import com.entity.EmployeeStatusEntity;
import com.entity.ModelDeveloperEntity;
import com.tools.Constant;
import com.tools.HazelCastMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by User on 16.03.2019.
 */
public class MainMenuController extends MenuController {

    private TableView<DoctorEntity> tableView_Doctor;
    private TableView<ModelDeveloperEntity> tableView_ModelDeveloper;
    private TableView<EmployeeStatusEntity> tableView_EmployeeStatus;
    private static ArrayList<StackPane> stackPanes;
    //    private ArrayList<EmployeeStatusEntity> statusEntities;
//    private ArrayList<SpecializationEntity> specializationEntities;
    private static final String ADD_NEW_DOCTOR = "Add new Doctor";
    private static final String ADD_NEW_MODEL_DEVELOPER = "Add new Model Developer";

    @FXML
    private MenuBarController menuBarController;
    @FXML
    private ChangeDoctorController changeDoctorController;
    @FXML
    private DoctorTabController doctorTabController;
    @FXML
    private DoctorInfoController doctorInfoController;
    @FXML
    private ModelDeveloperTabController modelDeveloperTabController;
    @FXML
    private ModelDeveloperInfoController modelDeveloperInfoController;
    @FXML
    private ChangeModelDeveloperController changeModelDeveloperController;
    @FXML
    private StackPane stackPane_DoctorInfo;
    @FXML
    private StackPane stackPane_DoctorChange;
    @FXML
    private StackPane stackPane_ModelDeveloperInfo;
    @FXML
    private StackPane stackPane_ModelDeveloperChange;
    @FXML
    private StackPane stackPane_SpecializationInfo;
    @FXML
    private StackPane stackPane_SpecializationChange;
    @FXML
    private StackPane stackPane_EmployeeStatusInfo;
    @FXML
    private StackPane stackPane_EmployeeStatusChange;
    @FXML
    private Tab tab_Doctor;
    @FXML
    private Tab tab_ModelDeveloper;
    @FXML
    private Tab tab_Specialization;
    @FXML
    private Tab tab_EmployeeStatus;


    public void initialize(Stage stage) {
        stage.setOnHidden(event -> {
            HazelCastMap.getInstance().getLifecycleService().shutdown();
        });
        setStage(stage);
        this.menuBarController.init(this);
        this.doctorInfoController.init(this);
        this.modelDeveloperInfoController.init(this);

        prepareStackPanes();

        prepareDoctorTab();
        prepareModelDeveloperTab();

        SpecializationTabController.setStackPaneInfo(stackPane_SpecializationInfo);
        SpecializationTabController.setStackPaneChange(stackPane_SpecializationChange);

        EmployeeStatusTabController.setStackPaneInfo(stackPane_EmployeeStatusInfo);
        EmployeeStatusTabController.setStackPaneChange(stackPane_EmployeeStatusChange);

        this.changeDoctorController.init(this, EmployeeStatusTabController.getStatusEntities(), SpecializationTabController.getSpecializations());
        this.changeModelDeveloperController.init(this, EmployeeStatusTabController.getStatusEntities());
    }

    public void addDoctor(ActionEvent event) {
        activateStackPane(stackPane_DoctorChange, stackPanes);
        changeDoctorController.setChange(false);
        changeDoctorController.getLabel_PaneName().setText(ADD_NEW_DOCTOR);
        changeDoctorController.clearFields();
    }

    public void addModelDeveloper(ActionEvent event) {
        activateStackPane(stackPane_ModelDeveloperChange, stackPanes);
        changeModelDeveloperController.setChange(false);
        changeModelDeveloperController.getLabel_PaneName().setText(ADD_NEW_MODEL_DEVELOPER);
        changeDoctorController.clearFields();
//        changeModelDeveloperController.setEmployeeStatus(statusEntities);
    }

    public void addSpecialization(ActionEvent event) {
        activateStackPane(stackPane_SpecializationChange, stackPanes);
        ChangeSpecializationController.create();
    }

    public void addEmployeeStatus(ActionEvent event) {
        activateStackPane(stackPane_EmployeeStatusChange, stackPanes);
        ChangeEmployeeStatusController.create();
    }

    public void change() {
        if (tab_Doctor.isSelected() && tableView_Doctor.getSelectionModel().getSelectedItem() != null) {
            changeDoctorController.setChange(true);
            doctorTabController.changeDoctor();
        } else if (tab_ModelDeveloper.isSelected() && tableView_ModelDeveloper.getSelectionModel().getSelectedItem() != null) {
            changeModelDeveloperController.setChange(true);
            modelDeveloperTabController.changeModelDeveloper();
        } else if (tab_Specialization.isSelected() && SpecializationTabController.getSpecializationTable().getSelectionModel().getSelectedItem() != null) {
            activateStackPane(stackPane_SpecializationChange, MainMenuController.getStackPanes());
            ChangeSpecializationController.change(SpecializationTabController.getSpecializationTable().getSelectionModel().getSelectedItem());
        } else if (tab_EmployeeStatus.isSelected() && EmployeeStatusTabController.getEmployeeStatusTable().getSelectionModel().getSelectedItem() != null) {
            activateStackPane(stackPane_EmployeeStatusChange, MainMenuController.getStackPanes());
            ChangeEmployeeStatusController.change(EmployeeStatusTabController.getEmployeeStatusTable().getSelectionModel().getSelectedItem());
        }
    }

    public void delete() {
        if (tab_Doctor.isSelected() && tableView_Doctor.getSelectionModel().getSelectedItem() != null) {

        } else if (tab_ModelDeveloper.isSelected() && tableView_ModelDeveloper.getSelectionModel().getSelectedItem() != null) {

        } else if (tab_Specialization.isSelected() && SpecializationTabController.getSpecializationTable().getSelectionModel().getSelectedItem() != null) {
            SpecializationTabController.deleteSpecialization(SpecializationTabController.getSpecializationTable().getSelectionModel().getSelectedItem());
        } else if (tab_EmployeeStatus.isSelected()) {
            EmployeeStatusTabController.deleteEmployeeStatus(EmployeeStatusTabController.getEmployeeStatusTable().getSelectionModel().getSelectedItem());
//            employeeStatusTabController.deleteEmployee();
        } else {
            Constant.getAlert(null, "Please, select an item to delete!", Alert.AlertType.ERROR);
        }
    }


    private void prepareStackPanes() {
        stackPanes = new ArrayList<>();
        stackPanes.add(stackPane_DoctorInfo);
        stackPanes.add(stackPane_DoctorChange);
        stackPanes.add(stackPane_ModelDeveloperInfo);
        stackPanes.add(stackPane_ModelDeveloperChange);

        stackPanes.add(stackPane_SpecializationInfo);
        stackPanes.add(stackPane_SpecializationChange);
        stackPanes.add(stackPane_EmployeeStatusInfo);
        stackPanes.add(stackPane_EmployeeStatusChange);
    }

    public void prepareDoctorTab() {
        this.doctorTabController.init(this);
        this.doctorTabController.setStackPaneChange(stackPane_DoctorChange);
        this.doctorTabController.setLabel_PaneNameChange(changeDoctorController.getLabel_PaneName());
        this.doctorTabController.setTextField_NameChange(changeDoctorController.getTextField_Name());
        this.doctorTabController.setTextField_SurnameChange(changeDoctorController.getTextField_Surname());
        this.doctorTabController.setComboBox_Specialization(changeDoctorController.getComboBox_Specialization());
        this.doctorTabController.setTextField_TelephoneChange(changeDoctorController.getTextField_Telephone());
        this.doctorTabController.setTextField_EmailChange(changeDoctorController.getTextField_Email());
        this.doctorTabController.setComboBox_Status(changeDoctorController.getComboBox_Status());

        this.doctorTabController.setSpecializationEntities(SpecializationTabController.getSpecializations());
        this.doctorTabController.setEmployeeStatusEntities(EmployeeStatusTabController.getStatusEntities());

        this.doctorTabController.setLabel_NameInfo(doctorInfoController.getLabel_Name());
        this.doctorTabController.setLabel_SurnameInfo(doctorInfoController.getLabel_Surname());
        this.doctorTabController.setLabel_SpecializationInfo(doctorInfoController.getLabel_Specialization());
        this.doctorTabController.setLabel_TelephoneInfo(doctorInfoController.getLabel_Telephone());
        this.doctorTabController.setLabel_EmailInfo(doctorInfoController.getLabel_Email());
        this.doctorTabController.setLabel_StatusInfo(doctorInfoController.getLabel_Status());
        this.doctorTabController.setStackPaneInfo(stackPane_DoctorInfo);
        tableView_Doctor = this.doctorTabController.getTableView_Doctor();
        this.doctorTabController.setStackPanes(stackPanes);
    }

    public void prepareModelDeveloperTab() {
        this.modelDeveloperTabController.init(this);

        this.modelDeveloperTabController.setStackPaneChange(stackPane_ModelDeveloperChange);
        this.modelDeveloperTabController.setLabel_PaneNameChange(changeModelDeveloperController.getLabel_PaneName());
        this.modelDeveloperTabController.setTextField_NameChange(changeModelDeveloperController.getTextField_Name());
        this.modelDeveloperTabController.setTextField_SurnameChange(changeModelDeveloperController.getTextField_Surname());
        this.modelDeveloperTabController.setTextField_TelephoneChange(changeModelDeveloperController.getTextField_Telephone());
        this.modelDeveloperTabController.setTextField_EmailChange(changeModelDeveloperController.getTextField_Email());
        this.modelDeveloperTabController.setComboBox_Status(changeModelDeveloperController.getComboBox_Status());

        this.modelDeveloperTabController.setEmployeeStatusEntities(EmployeeStatusTabController.getStatusEntities());
        this.modelDeveloperTabController.setLabel_NameInfo(modelDeveloperInfoController.getLabel_Name());
        this.modelDeveloperTabController.setLabel_SurnameInfo(modelDeveloperInfoController.getLabel_Surname());
        this.modelDeveloperTabController.setLabel_TelephoneInfo(modelDeveloperInfoController.getLabel_Telephone());
        this.modelDeveloperTabController.setLabel_EmailInfo(modelDeveloperInfoController.getLabel_Email());
        this.modelDeveloperTabController.setLabel_StatusInfo(modelDeveloperInfoController.getLabel_Status());
        this.modelDeveloperTabController.setStackPaneInfo(stackPane_ModelDeveloperInfo);
        tableView_ModelDeveloper = this.modelDeveloperTabController.getTableView_ModelDeveloper();
        this.modelDeveloperTabController.setStackPanes(stackPanes);
    }

    public void refresh(ActionEvent event) {
        doctorTabController.refreshPage();
        modelDeveloperTabController.refreshPage();
    }

    public static void deactivateAllStackPane() {
        for (StackPane stackPane : stackPanes) {
            stackPane.setDisable(true);
            stackPane.setVisible(false);
        }
    }

    public static ArrayList<StackPane> getStackPanes() {
        return stackPanes;
    }
}
