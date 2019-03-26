package com.controllers.windows.menu;

import com.controllers.requests.EmployeeStatusController;
import com.controllers.requests.SpecializationController;
import com.controllers.windows.entity.ChangeEmployeeStatusController;
import com.controllers.windows.entity.ChangeSpecializationController;
import com.controllers.windows.entityInfo.EmployeeStatusInfoController;
import com.controllers.windows.entityInfo.SpecializationInfoController;
import com.models.EmployeeStatusEntity;
import com.models.SpecializationEntity;
import com.tools.Constant;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Admin on 16.03.2019.
 */
public class MainMenuController extends MenuController {

    @Autowired
    HttpResponse response;

    private ArrayList<StackPane> stackPanes;
    private ArrayList<SpecializationEntity> specializations;
    private ObservableList<SpecializationEntity> specializationEntities;
    private ArrayList<EmployeeStatusEntity> statusEntities;
    private ObservableList<EmployeeStatusEntity> employeeStatusEntities;
    private int statusCode;

    @FXML
    private MenuBarController menuBarController;
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
    @FXML
    private TableView<SpecializationEntity> tableView_Specialization;
    @FXML
    private TableColumn<SpecializationEntity, Number> tableColumnSpecialization_Number;
    @FXML
    private TableColumn tableColumnSpecialization_Name;

    @FXML
    private TableView<EmployeeStatusEntity> tableView_EmployeeStatus;
    @FXML
    private TableColumn<EmployeeStatusEntity, Number> tableColumnEmployeeStatus_Number;
    @FXML
    private TableColumn tableColumnEmployeeStatus_Name;
    @FXML
    private TableColumn tableColumnEmployeeStatus_WorkEnable;

    public void initialize(Stage stage) {
        stage.setOnHidden(event -> {
            Constant.getInstance().getLifecycleService().shutdown();
        });
        setStage(stage);
        this.menuBarController.init(this);
        this.changeEmployeeStatusController.init(this);
        this.employeeStatusInfoController.init(this);
        this.specializationInfoController.init(this);
        this.changeSpecializationController.init(this);

        stackPanes = new ArrayList<>();
        stackPanes.add(stackPane_DoctorInfo);
        stackPanes.add(stackPane_ModelDeveloperInfo);
        stackPanes.add(stackPane_DoctorRegistration);
        stackPanes.add(stackPane_SpecializationInfo);
        stackPanes.add(stackPane_SpecializationChange);
        stackPanes.add(stackPane_EmployeeStatusInfo);
        stackPanes.add(stackPane_EmployeeStatusChange);

        tableColumnEmployeeStatus_Number.setSortable(false);
        tableColumnEmployeeStatus_Number.setCellValueFactory(column ->
                new ReadOnlyObjectWrapper<Number>((tableView_EmployeeStatus.getItems().
                        indexOf(column.getValue()) + 1)));
        tableColumnEmployeeStatus_Name.setCellValueFactory(new PropertyValueFactory<EmployeeStatusEntity, String>("name"));
        tableColumnEmployeeStatus_WorkEnable.setCellValueFactory(new PropertyValueFactory<EmployeeStatusEntity, String>("visibleWorkEnable"));
        try {
            response = EmployeeStatusController.getAllEmployeeStatus();
            statusCode = response.getStatusLine().getStatusCode();
            if (checkStatusCode(statusCode)) {
                statusEntities = new EmployeeStatusEntity().getListFromResponse(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        employeeStatusEntities = FXCollections.observableList(statusEntities);
        tableView_EmployeeStatus.setItems(employeeStatusEntities);

        tableColumnSpecialization_Number.setSortable(false);
        tableColumnSpecialization_Number.setCellValueFactory(column ->
                new ReadOnlyObjectWrapper<Number>((tableView_Specialization.getItems().
                        indexOf(column.getValue()) + 1)));
        tableColumnSpecialization_Name.setCellValueFactory(new PropertyValueFactory<SpecializationEntity, String>("name"));
        try {
            response = SpecializationController.getAllSpecialization();
            statusCode = response.getStatusLine().getStatusCode();
            if (checkStatusCode(statusCode)) {
                specializations = new SpecializationEntity().getListFromResponse(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        specializationEntities = FXCollections.observableArrayList(specializations);
        tableView_Specialization.setItems(specializationEntities);

    }

    public void checkTab() {
        if (tab_Doctor.isSelected()) {
            activateStackPane(stackPane_DoctorInfo);
        } else if (tab_ModelDeveloper.isSelected()) {
            activateStackPane(stackPane_ModelDeveloperInfo);
        } else if (tab_Specialization.isSelected()) {
            activateStackPane(stackPane_SpecializationInfo);
        } else if (tab_EmployeeStatus.isSelected()) {
            activateStackPane(stackPane_EmployeeStatusInfo);
        }
    }

    public void activateStackPane(StackPane pane) {
        for (StackPane stackPane : stackPanes) {
            if (stackPane.equals(pane)) {
                stackPane.setDisable(false);
                stackPane.setVisible(true);
            } else {
                stackPane.setDisable(true);
                stackPane.setVisible(false);
            }
        }
    }

    public void addEmployeeStatus(ActionEvent event) {
        activateStackPane(stackPane_EmployeeStatusChange);
        changeEmployeeStatusController.setChange(false);
        changeEmployeeStatusController.getLabel_PaneName().setText("Add New Employee Status");
        changeEmployeeStatusController.getTextField_Name().clear();
        changeEmployeeStatusController.getCheckBox_WorkEnable().setSelected(true);
    }

    public void addSpecialization(ActionEvent event) {
        activateStackPane(stackPane_SpecializationChange);
        changeSpecializationController.setChange(false);
        changeSpecializationController.getLabel_PaneName().setText("Add New Specialization");
        changeSpecializationController.getTextField_Name().clear();
    }

    public void viewSpecialization() {
        if (tableView_Specialization.getSelectionModel().getSelectedItem() != null) {
            activateStackPane(stackPane_SpecializationInfo);
            specializationInfoController.getLabel_Name()
                    .setText(tableView_Specialization.getSelectionModel().getSelectedItem().getName());
        }
    }

    public void viewEmployeeStatus(MouseEvent mouseEvent) {
        if (tableView_EmployeeStatus.getSelectionModel().getSelectedItem() != null) {
            activateStackPane(stackPane_EmployeeStatusInfo);
            employeeStatusInfoController.getLabel_Name()
                    .setText(tableView_EmployeeStatus.getSelectionModel().getSelectedItem().getName());
            employeeStatusInfoController.getLabel_WorkEnable()
                    .setText(tableView_EmployeeStatus.getSelectionModel().getSelectedItem().getVisibleWorkEnable());
        }
    }

    public void change(ActionEvent event){
        change();
    }

    public void change() {
        if (tab_Doctor.isSelected() && tableView_Doctor.getSelectionModel().getSelectedItem() != null) {

        } else if (tab_ModelDeveloper.isSelected() && tableView_ModelDeveloper.getSelectionModel().getSelectedItem() != null) {

        } else if (tab_Specialization.isSelected() && tableView_Specialization.getSelectionModel().getSelectedItem() != null) {
            Constant.getMapByName(Constant.getMiscellaneousMapName()).put("specialization",
                    tableView_Specialization.getSelectionModel().getSelectedItem().getId());
            activateStackPane(stackPane_SpecializationChange);
            changeSpecializationController.setChange(true);
            changeSpecializationController.getLabel_PaneName().setText("Change Specialization");
            changeSpecializationController.getTextField_Name()
                    .setText(tableView_Specialization.getSelectionModel().getSelectedItem().getName());
        } else if (tab_EmployeeStatus.isSelected() && tableView_EmployeeStatus.getSelectionModel().getSelectedItem() != null) {
            Constant.getMapByName(Constant.getMiscellaneousMapName()).put("employeeStatus",
                    tableView_EmployeeStatus.getSelectionModel().getSelectedItem().getId());
            activateStackPane(stackPane_EmployeeStatusChange);
            changeEmployeeStatusController.setChange(true);
            changeEmployeeStatusController.getLabel_PaneName().setText("Change Employee Status");
            changeEmployeeStatusController.getTextField_Name()
                    .setText(tableView_EmployeeStatus.getSelectionModel().getSelectedItem().getName());
            changeEmployeeStatusController.getCheckBox_WorkEnable()
                    .setSelected(tableView_EmployeeStatus.getSelectionModel().getSelectedItem().isWorkEnable());

        }
    }

    public void delete() {
        boolean result;
        int id;
        if (tab_Doctor.isSelected() && tableView_Doctor.getSelectionModel().getSelectedItem() != null) {

        } else if (tab_ModelDeveloper.isSelected() && tableView_ModelDeveloper.getSelectionModel().getSelectedItem() != null) {

        } else if (tab_Specialization.isSelected() && tableView_Specialization.getSelectionModel().getSelectedItem() != null) {

        } else if (tab_EmployeeStatus.isSelected() && tableView_EmployeeStatus.getSelectionModel().getSelectedItem() != null) {
            result = Constant.questionOkCancel("Do you really want to delete Employee Status "
                    + tableView_EmployeeStatus.getSelectionModel().getSelectedItem().getName() + " ?");
            if (result) {
                id = tableView_EmployeeStatus.getSelectionModel().getSelectedItem().getId();
                EmployeeStatusController.deleteEmployeeStatus(id);
            }
        }
    }
}
