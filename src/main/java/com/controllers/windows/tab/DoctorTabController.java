package com.controllers.windows.tab;

import com.controllers.requests.DoctorController;
import com.controllers.windows.menu.MenuController;
import com.entity.DoctorEntity;
import com.entity.EmployeeStatusEntity;
import com.entity.SpecializationEntity;
import com.models.DoctorPage;
import com.tools.Constant;
import com.tools.HazleCastMap;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class DoctorTabController extends MenuController{
    @Autowired
    HttpResponse response;

    private MenuController menuController;
    private StackPane stackPane_Change;
    private StackPane stackPane_Info;
    private ArrayList<StackPane> stackPanes;
    private ObservableList<DoctorEntity> doctorEntities;
    private DoctorPage doctorPage;
    private int pageIndex;
    private Label label_NameInfo;
    private Label label_SurnameInfo;
    private Label label_SpecializationInfo;
    private Label label_TelephoneInfo;
    private Label label_EmailInfo;
    private Label label_StatusInfo;
    private Label label_PaneNameChange;
    private TextField textField_NameChange;
    private TextField textField_SurnameChange;
    private TextField textField_TelephoneChange;
    private TextField textField_EmailChange;
    private ComboBox<SpecializationEntity> comboBox_Specialization;
    private ComboBox<EmployeeStatusEntity> comboBox_Status;

    private ArrayList<EmployeeStatusEntity> employeeStatusEntities;
    private ArrayList<SpecializationEntity> specializationEntities;

    @FXML
    private TableView<DoctorEntity> tableView_Doctor;
    @FXML
    private TableColumn<DoctorEntity, Number> tableColumn_Number;
    @FXML
    private TableColumn tableColumn_Name;
    @FXML
    private TableColumn tableColumn_Surname;
    @FXML
    private TableColumn tableColumn_Specialization;
    @FXML
    private Pagination pagination_Doctor;

    public void init(MenuController menuController) {
        this.menuController = menuController;
        pageIndex = Integer.parseInt(HazleCastMap.getMapByName(HazleCastMap.getMiscellaneousMapName()).get(Constant.getPageIndexDoctor()).toString());
//        doctorEntities = FXCollections.observableArrayList();

        tableColumn_Number.setSortable(false);
        tableColumn_Number.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Number>((tableView_Doctor.getItems().
                indexOf(column.getValue()) + 1) + (pageIndex - 1) * Constant.getObjectOnPage()));
        tableColumn_Name.setSortable(false);
        tableColumn_Name.setCellValueFactory(new PropertyValueFactory<DoctorEntity, String>("name"));
        tableColumn_Surname.setSortable(false);
        tableColumn_Surname.setCellValueFactory(new PropertyValueFactory<DoctorEntity, String>("surname"));
        tableColumn_Specialization.setSortable(false);
        tableColumn_Specialization.setCellValueFactory(new PropertyValueFactory<DoctorEntity, String>("visibleSpecialization"));
        pagination_Doctor.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex) {
        this.pageIndex = pageIndex + 1;
        response = DoctorController.getDoctorPage(this.pageIndex);
        setStatusCode(response.getStatusLine().getStatusCode());
        if (checkStatusCode(getStatusCode())) {
            doctorPage = DoctorPage.fromJson(response);
            doctorEntities = FXCollections.observableList(doctorPage.getDoctorEntities());
        }
        if (doctorPage.getNumberOfPages() == 0) {
            pagination_Doctor.setPageCount(1);
        } else {
            pagination_Doctor.setPageCount(doctorPage.getNumberOfPages());
        }
        pagination_Doctor.setCurrentPageIndex(this.pageIndex - 1);
        tableView_Doctor.setItems(doctorEntities);
        return tableView_Doctor;
    }

    public void viewDoctor(MouseEvent mouseEvent) {
        viewDoctor();
    }

    public void viewDoctor() {
        if (tableView_Doctor.getSelectionModel().getSelectedItem() != null) {
            activateStackPane(stackPane_Info, stackPanes);
            label_NameInfo
                    .setText(tableView_Doctor.getSelectionModel().getSelectedItem().getName());
            label_SurnameInfo
                    .setText(tableView_Doctor.getSelectionModel().getSelectedItem().getSurname());
            label_SpecializationInfo.setText(tableView_Doctor.getSelectionModel().getSelectedItem().getSpecializationEntity().getName());
            label_TelephoneInfo.setText(tableView_Doctor.getSelectionModel().getSelectedItem().getTelephone());
            label_EmailInfo.setText(tableView_Doctor.getSelectionModel().getSelectedItem().getEmail());
            label_StatusInfo.setText(tableView_Doctor.getSelectionModel().getSelectedItem().getEmployeeStatus().getName());
        }
    }

    public void changeDoctor(){
        if(tableView_Doctor.getSelectionModel().getSelectedItem()!=null){
            changeDoctor(tableView_Doctor);
        }
    }

    public void changeDoctor(TableView<DoctorEntity> tableView){
        HazleCastMap.getMapByName(HazleCastMap.getMiscellaneousMapName()).put("doctor",
                tableView.getSelectionModel().getSelectedItem().getId());
        activateStackPane(stackPane_Change, stackPanes);
        label_PaneNameChange.setText("Change Doctor");
        textField_NameChange
                .setText(tableView.getSelectionModel().getSelectedItem().getName());
        textField_SurnameChange.setText(tableView.getSelectionModel().getSelectedItem().getSurname());
        comboBox_Status.setItems(EmployeeStatusTabController.getEmployeeStatusTable().getItems());
        comboBox_Specialization.setItems(SpecializationTabController.getSpecializationTable().getItems());
        for(SpecializationEntity specializationEntity : comboBox_Specialization.getItems()){
            if(specializationEntity.getId() == tableView.getSelectionModel().getSelectedItem().getSpecializationEntity().getId()){
                comboBox_Specialization.getSelectionModel().select(specializationEntity);
            }
        }
        textField_TelephoneChange.setText(tableView.getSelectionModel().getSelectedItem().getTelephone());
        textField_EmailChange.setText(tableView.getSelectionModel().getSelectedItem().getEmail());
        for(EmployeeStatusEntity employeeStatusEntity:comboBox_Status.getItems()){
            if(employeeStatusEntity.getId() == tableView.getSelectionModel().getSelectedItem().getEmployeeStatus().getId()){
                comboBox_Status.getSelectionModel().select(employeeStatusEntity);
            }
        }
    }

    public void refreshPage(){
        pagination_Doctor.setPageFactory(this::createPage);
    }


    public void setStackPaneChange(StackPane stackPaneChange) {
        this.stackPane_Change = stackPaneChange;
    }

    public void setLabel_PaneNameChange(Label label_PaneNameChange) {
        this.label_PaneNameChange = label_PaneNameChange;
    }

    public void setTextField_NameChange(TextField textField_NameChange) {
        this.textField_NameChange = textField_NameChange;
    }

    public void setTextField_SurnameChange(TextField textField_SurnameChange) {
        this.textField_SurnameChange = textField_SurnameChange;
    }

    public void setComboBox_Specialization(ComboBox<SpecializationEntity> comboBox_Specialization) {
        this.comboBox_Specialization = comboBox_Specialization;
    }

    public void setTextField_TelephoneChange(TextField textField_TelephoneChange) {
        this.textField_TelephoneChange = textField_TelephoneChange;
    }

    public void setTextField_EmailChange(TextField textField_EmailChange) {
        this.textField_EmailChange = textField_EmailChange;
    }

    public void setComboBox_Status(ComboBox<EmployeeStatusEntity> comboBox_Status) {
        this.comboBox_Status = comboBox_Status;
    }

    public TableView<DoctorEntity> getTableView_Doctor() {
        return tableView_Doctor;
    }

    public void setStackPanes(ArrayList<StackPane> stackPanes) {
        this.stackPanes = stackPanes;
    }

    public void setEmployeeStatusEntities(ArrayList<EmployeeStatusEntity> employeeStatus) {
        this.employeeStatusEntities = employeeStatus;
    }

    public void setSpecializationEntities(ArrayList<SpecializationEntity> specializationEntities) {
        this.specializationEntities = specializationEntities;
    }

    public void setLabel_NameInfo(Label label_NameInfo) {
        this.label_NameInfo = label_NameInfo;
    }

    public void setLabel_SurnameInfo(Label label_SurnameInfo) {
        this.label_SurnameInfo = label_SurnameInfo;
    }

    public void setLabel_TelephoneInfo(Label label_TelephoneInfo) {
        this.label_TelephoneInfo = label_TelephoneInfo;
    }

    public void setLabel_EmailInfo(Label label_EmailInfo) {
        this.label_EmailInfo = label_EmailInfo;
    }

    public void setLabel_StatusInfo(Label label_StatusInfo) {
        this.label_StatusInfo = label_StatusInfo;
    }

    public void setStackPaneInfo(StackPane stackPaneInfo) {
        this.stackPane_Info = stackPaneInfo;
    }

    public Label getLabel_SpecializationInfo() {
        return label_SpecializationInfo;
    }

    public void setLabel_SpecializationInfo(Label label_SpecializationInfo) {
        this.label_SpecializationInfo = label_SpecializationInfo;
    }
}
