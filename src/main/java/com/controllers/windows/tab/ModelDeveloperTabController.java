package com.controllers.windows.tab;

import com.controllers.requests.ModelDeveloperController;
import com.controllers.windows.menu.MainMenuController;
import com.controllers.windows.menu.MenuController;
import com.entity.EmployeeStatusEntity;
import com.entity.ModelDeveloperEntity;
import com.models.ModelDeveloperPage;
import com.tools.Constant;
import com.tools.HazelCastMap;
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

import java.util.ArrayList;

public class ModelDeveloperTabController extends MenuController {

    private MenuController menuController;
    private StackPane stackPane_Change;
    private StackPane stackPane_Info;
    private ArrayList<StackPane> stackPanes;
    private ObservableList<ModelDeveloperEntity> modelDeveloperEntities;
    private ModelDeveloperPage modelDeveloperPage;
    private int pageIndex;
    private Label label_NameInfo;
    private Label label_SurnameInfo;
    private Label label_TelephoneInfo;
    private Label label_EmailInfo;
    private Label label_StatusInfo;
    private Label label_PaneNameChange;
    private TextField textField_NameChange;
    private TextField textField_SurnameChange;
    private TextField textField_TelephoneChange;
    private TextField textField_EmailChange;
    private ComboBox<EmployeeStatusEntity> comboBox_Status;


    private ArrayList<EmployeeStatusEntity> employeeStatusEntities;

    @FXML
    private TableView<ModelDeveloperEntity> tableView_ModelDeveloper;
    @FXML
    private TableColumn<ModelDeveloperEntity, Number> tableColumn_Number;
    @FXML
    private TableColumn tableColumn_Name;
    @FXML
    private TableColumn tableColumn_Surname;
    @FXML
    private Pagination pagination_ModelDeveloper;

    public void init(MenuController menuController) {
        this.menuController = menuController;
        pageIndex = Integer.parseInt(HazelCastMap.getMapByName(HazelCastMap.getMiscellaneousMapName()).get("pageIndexModelDeveloper").toString());

        tableColumn_Number.setSortable(false);
        tableColumn_Number.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Number>((tableView_ModelDeveloper.getItems().
                indexOf(column.getValue()) + 1) + (pageIndex - 1) * Constant.getObjectOnPage()));
        tableColumn_Name.setSortable(false);
        tableColumn_Name.setCellValueFactory(new PropertyValueFactory<ModelDeveloperEntity, String>("name"));
        tableColumn_Surname.setSortable(false);
        tableColumn_Surname.setCellValueFactory(new PropertyValueFactory<ModelDeveloperEntity, String>("surname"));
        pagination_ModelDeveloper.setPageFactory(this::createPage);
    }

    public void viewModelDeveloper(MouseEvent mouseEvent) {
        viewModelDeveloper();
    }

    public void viewModelDeveloper() {
        if (tableView_ModelDeveloper.getSelectionModel().getSelectedItem() != null) {
            activateStackPane(stackPane_Info, stackPanes);
            label_NameInfo
                    .setText(tableView_ModelDeveloper.getSelectionModel().getSelectedItem().getName());
            label_SurnameInfo
                    .setText(tableView_ModelDeveloper.getSelectionModel().getSelectedItem().getSurname());
            label_TelephoneInfo.setText(tableView_ModelDeveloper.getSelectionModel().getSelectedItem().getTelephone());
            label_EmailInfo.setText(tableView_ModelDeveloper.getSelectionModel().getSelectedItem().getEmail());
            label_StatusInfo.setText(tableView_ModelDeveloper.getSelectionModel().getSelectedItem().getEmployeeStatus().getName());
        }
    }

    public void changeModelDeveloper(){
        if(tableView_ModelDeveloper.getSelectionModel().getSelectedItem()!=null){
            changeModelDeveloper(tableView_ModelDeveloper);
        }
    }

    public void changeModelDeveloper(TableView<ModelDeveloperEntity> tableView){
        HazelCastMap.getMapByName(HazelCastMap.getMiscellaneousMapName()).put("modelDeveloper",
                tableView.getSelectionModel().getSelectedItem().getId());
        activateStackPane(stackPane_Change, stackPanes);
        label_PaneNameChange.setText("Change Model Developer");
        textField_NameChange
                .setText(tableView.getSelectionModel().getSelectedItem().getName());
        textField_SurnameChange.setText(tableView.getSelectionModel().getSelectedItem().getSurname());
        textField_TelephoneChange.setText(tableView.getSelectionModel().getSelectedItem().getTelephone());
        textField_EmailChange.setText(tableView.getSelectionModel().getSelectedItem().getEmail());
        comboBox_Status.setItems(EmployeeStatusTabController.getEmployeeStatusTable().getItems());
        for(EmployeeStatusEntity employeeStatusEntity:comboBox_Status.getItems()){
            if(employeeStatusEntity.getId() == tableView.getSelectionModel().getSelectedItem().getEmployeeStatus().getId()){
                comboBox_Status.getSelectionModel().select(employeeStatusEntity);
            }
        }
    }

    public void deleteModelDeveloper(ModelDeveloperEntity modelDeveloper) {
        boolean result = Constant.questionOkCancel("Do you really want to delete Model Developer "
                + modelDeveloper.getSurname() + modelDeveloper.getName() + " ?");
        if (result) {
            HttpResponse response = ModelDeveloperController.deleteModelDeveloper(modelDeveloper.getId());
            int statusCode = response.getStatusLine().getStatusCode();
            if (checkStatusCode(statusCode)) {
//                return doctor.getId();
                for (ModelDeveloperEntity modelDeveloperEntity : tableView_ModelDeveloper.getItems()) {
                    if (modelDeveloperEntity.getId() == modelDeveloper.getId()) {
                        tableView_ModelDeveloper.getItems().remove(modelDeveloperEntity);
                        Constant.getAlert(null, "Model Developer " + modelDeveloperEntity.getName() + " deleted!", Alert.AlertType.INFORMATION);
                        MainMenuController.deactivateAllStackPane();
                        break;
                    }
                }
            }
        }
    }

    public StackPane getStackPaneChange() {
        return stackPane_Change;
    }

    public void setStackPaneChange(StackPane stackPane_Change) {
        this.stackPane_Change = stackPane_Change;
    }

    public StackPane getStackPaneInfo() {
        return stackPane_Info;
    }

    public void setStackPaneInfo(StackPane stackPane_Info) {
        this.stackPane_Info = stackPane_Info;
    }

    public ArrayList<StackPane> getStackPanes() {
        return stackPanes;
    }

    public void setStackPanes(ArrayList<StackPane> stackPanes) {
        this.stackPanes = stackPanes;
    }

    public TableView<ModelDeveloperEntity> getTableView_ModelDeveloper() {
        return tableView_ModelDeveloper;
    }

    public Label getLabel_NameInfo() {
        return label_NameInfo;
    }

    public void setLabel_NameInfo(Label label_NameInfo) {
        this.label_NameInfo = label_NameInfo;
    }

    public Label getLabel_SurnameInfo() {
        return label_SurnameInfo;
    }

    public void setLabel_SurnameInfo(Label label_SurnameInfo) {
        this.label_SurnameInfo = label_SurnameInfo;
    }

    public Label getLabel_TelephoneInfo() {
        return label_TelephoneInfo;
    }

    public void setLabel_TelephoneInfo(Label label_TelephoneInfo) {
        this.label_TelephoneInfo = label_TelephoneInfo;
    }

    public Label getLabel_EmailInfo() {
        return label_EmailInfo;
    }

    public void setLabel_EmailInfo(Label label_EmailInfo) {
        this.label_EmailInfo = label_EmailInfo;
    }

    public Label getLabel_StatusInfo() {
        return label_StatusInfo;
    }

    public void setLabel_StatusInfo(Label label_StatusInfo) {
        this.label_StatusInfo = label_StatusInfo;
    }

    public ArrayList<EmployeeStatusEntity> getEmployeeStatusEntities() {
        return employeeStatusEntities;
    }

    public void setEmployeeStatusEntities(ArrayList<EmployeeStatusEntity> employeeStatusEntities) {
        this.employeeStatusEntities = employeeStatusEntities;
    }

    public Label getLabel_PaneNameChange() {
        return label_PaneNameChange;
    }

    public void setLabel_PaneNameChange(Label label_PaneNameChange) {
        this.label_PaneNameChange = label_PaneNameChange;
    }

    public TextField getTextField_NameChange() {
        return textField_NameChange;
    }

    public void setTextField_NameChange(TextField textField_NameChange) {
        this.textField_NameChange = textField_NameChange;
    }

    public TextField getTextField_SurnameChange() {
        return textField_SurnameChange;
    }

    public void setTextField_SurnameChange(TextField textField_SurnameChange) {
        this.textField_SurnameChange = textField_SurnameChange;
    }

    public TextField getTextField_TelephoneChange() {
        return textField_TelephoneChange;
    }

    public void setTextField_TelephoneChange(TextField textField_TelephoneChange) {
        this.textField_TelephoneChange = textField_TelephoneChange;
    }

    public TextField getTextField_EmailChange() {
        return textField_EmailChange;
    }

    public void setTextField_EmailChange(TextField textField_EmailChange) {
        this.textField_EmailChange = textField_EmailChange;
    }

    public ComboBox<EmployeeStatusEntity> getComboBox_Status() {
        return comboBox_Status;
    }

    public void setComboBox_Status(ComboBox<EmployeeStatusEntity> comboBox_Status) {
        this.comboBox_Status = comboBox_Status;
    }

    private Node createPage(int pageIndex) {
        this.pageIndex = pageIndex + 1;
        HttpResponse response = ModelDeveloperController.getModelDeveloperPage(this.pageIndex);
        setStatusCode(response.getStatusLine().getStatusCode());
        if (checkStatusCode(getStatusCode())) {
            modelDeveloperPage = ModelDeveloperPage.fromJson(response);
            modelDeveloperEntities = FXCollections.observableList(modelDeveloperPage.getSpecialistEntities());
        }
        if (modelDeveloperPage.getNumberOfPages() == 0) {
            pagination_ModelDeveloper.setPageCount(1);
        } else {
            pagination_ModelDeveloper.setPageCount(modelDeveloperPage.getNumberOfPages());
        }
        pagination_ModelDeveloper.setCurrentPageIndex(this.pageIndex - 1);
        tableView_ModelDeveloper.setItems(modelDeveloperEntities);
        return tableView_ModelDeveloper;
    }

    public void refreshPage(){
        pagination_ModelDeveloper.setPageFactory(this::createPage);
    }
}
