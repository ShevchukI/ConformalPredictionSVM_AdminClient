package com.controllers.windows.menu;

import com.controllers.windows.stack.entity.ChangeEmployeeStatusController;
import com.controllers.windows.stack.entity.ChangeSpecializationController;
import com.controllers.windows.stack.entityInfo.EmployeeStatusInfoController;
import com.controllers.windows.stack.entityInfo.SpecializationInfoController;
import com.controllers.windows.tab.EmployeeStatusTabController;
import com.controllers.windows.tab.SpecializationTabController;
import com.models.EmployeeStatusEntity;
import com.models.SpecializationEntity;
import com.tools.Constant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * Created by Admin on 16.03.2019.
 */
public class MainMenuController extends MenuController {

    @Autowired
    HttpResponse response;

    private TableView<SpecializationEntity> tableView_Specialization;
    private TableView<EmployeeStatusEntity> tableView_EmployeeStatus;
    private ArrayList<StackPane> stackPanes;
    private int statusCode;

    @FXML
    private MenuBarController menuBarController;
    @FXML
    private SpecializationTabController specializationTabController;
    @FXML
    private EmployeeStatusTabController employeeStatusTabController;
    @FXML
    private ChangeEmployeeStatusController changeEmployeeStatusController;
    @FXML
    private EmployeeStatusInfoController employeeStatusInfoController;
    @FXML
    private ChangeSpecializationController changeSpecializationController;
    @FXML
    private SpecializationInfoController specializationInfoController;
    @FXML
    private StackPane stackPane_DoctorInfo;
    @FXML
    private StackPane stackPane_ModelDeveloperInfo;
    @FXML
    private StackPane stackPane_DoctorRegistration;
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
    @FXML
    private TableView tableView_Doctor;
    @FXML
    private TableView tableView_ModelDeveloper;


    public void initialize(Stage stage) {
        stage.setOnHidden(event -> {
            Constant.getInstance().getLifecycleService().shutdown();
        });
        setStage(stage);
        this.menuBarController.init(this);
        prepareStackPanes();

        prepareSpecializationTab();
        prepareEmployeeStatusTab();

        this.changeEmployeeStatusController.init(this);
        this.employeeStatusInfoController.init(this);
        this.specializationInfoController.init(this);
        this.changeSpecializationController.init(this);


    }

    public void checkTab() {
        if (tab_Doctor.isSelected()) {
            activateStackPane(stackPane_DoctorInfo, stackPanes);
        } else if (tab_ModelDeveloper.isSelected()) {
            activateStackPane(stackPane_ModelDeveloperInfo, stackPanes);
        }
    }


    public void addSpecialization(ActionEvent event) {
        activateStackPane(stackPane_SpecializationChange, stackPanes);
        changeSpecializationController.setChange(false);
        changeSpecializationController.getLabel_PaneName().setText("Add New Specialization");
        changeSpecializationController.getTextField_Name().clear();
    }

    public void addEmployeeStatus(ActionEvent event) {
        activateStackPane(stackPane_EmployeeStatusChange, stackPanes);
        changeEmployeeStatusController.setChange(false);
        changeEmployeeStatusController.getLabel_PaneName().setText("Add New Employee Status");
        changeEmployeeStatusController.getTextField_Name().clear();
        changeEmployeeStatusController.getCheckBox_WorkEnable().setSelected(true);
    }



    public void change(ActionEvent event) {
        change();
    }

    public void change() {
        if (tab_Doctor.isSelected() && tableView_Doctor.getSelectionModel().getSelectedItem() != null) {

        } else if (tab_ModelDeveloper.isSelected() && tableView_ModelDeveloper.getSelectionModel().getSelectedItem() != null) {

        } else if (tab_Specialization.isSelected() && tableView_Specialization.getSelectionModel().getSelectedItem() != null) {
            changeSpecializationController.setChange(true);
            specializationTabController.changeSpecialization();
        } else if (tab_EmployeeStatus.isSelected()) {
            changeEmployeeStatusController.setChange(true);
            employeeStatusTabController.changeEmployeeStatus();
        }
    }

    public void delete(ActionEvent event) {
        delete();
    }

    public void delete() {
        if (tab_Doctor.isSelected() && tableView_Doctor.getSelectionModel().getSelectedItem() != null) {

        } else if (tab_ModelDeveloper.isSelected() && tableView_ModelDeveloper.getSelectionModel().getSelectedItem() != null) {

        } else if (tab_Specialization.isSelected() && tableView_Specialization.getSelectionModel().getSelectedItem() != null) {
            specializationTabController.deleteSpecialization();
        } else if (tab_EmployeeStatus.isSelected()) {
            employeeStatusTabController.deleteEmployee();
        } else {
            Constant.getAlert(null, "Please, select an item to delete!", Alert.AlertType.ERROR);
        }
    }


    private void prepareStackPanes(){
        stackPanes = new ArrayList<>();
        stackPanes.add(stackPane_DoctorInfo);
        stackPanes.add(stackPane_ModelDeveloperInfo);
        stackPanes.add(stackPane_DoctorRegistration);
        stackPanes.add(stackPane_SpecializationInfo);
        stackPanes.add(stackPane_SpecializationChange);
        stackPanes.add(stackPane_EmployeeStatusInfo);
        stackPanes.add(stackPane_EmployeeStatusChange);
    }

    private void prepareSpecializationTab(){
        this.specializationTabController.init(this);
        this.specializationTabController.setLabel_PaneNameChange(changeSpecializationController.getLabel_PaneName());
        this.specializationTabController.setTextField_NameChange(changeSpecializationController.getTextField_Name());
        this.specializationTabController.setStackPaneChange(stackPane_SpecializationChange);
        this.specializationTabController.setLabel_NameInfo(specializationInfoController.getLabel_Name());
        this.specializationTabController.setStackPaneInfo(stackPane_SpecializationInfo);
        tableView_Specialization = this.specializationTabController.getTableView_Specialization();
        this.specializationTabController.setStackPanes(stackPanes);
    }

    private void prepareEmployeeStatusTab(){
        this.employeeStatusTabController.init(this);
        this.employeeStatusTabController.setLabel_PaneNameChange(changeEmployeeStatusController.getLabel_PaneName());
        this.employeeStatusTabController.setTextField_NameChange(changeEmployeeStatusController.getTextField_Name());
        this.employeeStatusTabController.setCheckBox_WorkEnableChange(changeEmployeeStatusController.getCheckBox_WorkEnable());
        this.employeeStatusTabController.setStackPaneChange(stackPane_EmployeeStatusChange);
        this.employeeStatusTabController.setLabel_NameInfo(employeeStatusInfoController.getLabel_Name());
        this.employeeStatusTabController.setLabel_WorkEnableInfo(employeeStatusInfoController.getLabel_WorkEnable());
        this.employeeStatusTabController.setStackPaneInfo(stackPane_EmployeeStatusInfo);
        tableView_EmployeeStatus = this.employeeStatusTabController.getTableView_EmployeeStatus();
        this.employeeStatusTabController.setStackPanes(stackPanes);
    }

}
