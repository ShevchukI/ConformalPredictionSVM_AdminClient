package com.controllers.windows.tab;

import com.controllers.requests.SpecializationController;
import com.controllers.windows.menu.MenuController;
import com.entity.SpecializationEntity;
import com.tools.Constant;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;

public class SpecializationTabController extends MenuController{

    @Autowired
    HttpResponse response;

    private MenuController menuController;
    private StackPane stackPane_Change;
    private StackPane stackPane_Info;
    private ArrayList<StackPane> stackPanes;
    private ArrayList<SpecializationEntity> specializations;
    private ObservableList<SpecializationEntity> specializationEntities;
    private Label label_PaneNameChange;
    private TextField textField_NameChange;
    private Label label_NameInfo;

    @FXML
    private TableView<SpecializationEntity> tableView_Specialization;
    @FXML
    private TableColumn<SpecializationEntity, Number> tableColumn_Number;
    @FXML
    private TableColumn tableColumn_Name;

    public void init(MenuController menuController){
        this.menuController = menuController;
        tableColumn_Number.setSortable(false);
        tableColumn_Number.setCellValueFactory(column ->
                new ReadOnlyObjectWrapper<Number>((tableView_Specialization.getItems().
                        indexOf(column.getValue()) + 1)));
        tableColumn_Name.setCellValueFactory(new PropertyValueFactory<SpecializationEntity, String>("name"));
        try {
            response = SpecializationController.getAllSpecialization();
            setStatusCode(response.getStatusLine().getStatusCode());
            if (checkStatusCode(getStatusCode())) {
                specializations = SpecializationEntity.getListFromResponse(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        specializationEntities = FXCollections.observableArrayList(specializations);
        tableView_Specialization.setItems(specializationEntities);
    }

    public void viewSpecialization(MouseEvent mouseEvent) {
        viewSpecialization();
    }

    public void viewSpecialization() {
        if (tableView_Specialization.getSelectionModel().getSelectedItem() != null) {
            activateStackPane(stackPane_Info, stackPanes);
            label_NameInfo
                    .setText(tableView_Specialization.getSelectionModel().getSelectedItem().getName());
        }
    }

    public void deleteSpecialization() {
        if (tableView_Specialization.getSelectionModel().getSelectedItem() != null) {
            deleteSpecializationFromTable(tableView_Specialization);
        }
    }

    public void deleteSpecializationFromTable(TableView<SpecializationEntity> tableView) {
        boolean result = Constant.questionOkCancel("Do you really want to delete Specialization "
                + tableView.getSelectionModel().getSelectedItem().getName() + " ?");
        if (result) {
            int id = tableView.getSelectionModel().getSelectedItem().getId();
            response = SpecializationController.deleteSpecialization(id);
            setStatusCode(response.getStatusLine().getStatusCode());
            if (checkStatusCode(getStatusCode())) {
                for (SpecializationEntity specializationEntity : tableView.getItems()) {
                    if (specializationEntity.getId() == id) {
                        tableView.getItems().remove(specializationEntity);
                        Constant.getAlert(null, "Specialization " + specializationEntity.getName() + " deleted!", Alert.AlertType.INFORMATION);
                        deactivateAllStackPane(stackPanes);
                        break;
                    }
                }
            }
        }
    }

    public void changeSpecialization(){
        if(tableView_Specialization.getSelectionModel().getSelectedItem()!=null){
            changeSpecializationFromTable(tableView_Specialization);
        }
    }

    public void changeSpecializationFromTable(TableView<SpecializationEntity> tableView) {
        Constant.getMapByName(Constant.getMiscellaneousMapName()).put("specialization",
                tableView.getSelectionModel().getSelectedItem().getId());
        activateStackPane(stackPane_Change, stackPanes);
        label_PaneNameChange.setText("Change Specialization");
        textField_NameChange
                .setText(tableView.getSelectionModel().getSelectedItem().getName());
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

    public Label getLabel_NameInfo() {
        return label_NameInfo;
    }

    public void setLabel_NameInfo(Label label_NameInfo) {
        this.label_NameInfo = label_NameInfo;
    }

    public TableView<SpecializationEntity> getTableView_Specialization() {
        return tableView_Specialization;
    }

    public ArrayList<StackPane> getStackPanes() {
        return stackPanes;
    }

    public void setStackPanes(ArrayList<StackPane> stackPanes) {
        this.stackPanes = stackPanes;
    }
}
