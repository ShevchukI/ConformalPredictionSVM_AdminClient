package com.controllers.windows.tab;

import com.controllers.windows.menu.MainMenuController;
import com.controllers.windows.menu.MenuController;
import com.controllers.windows.stack.entityInfo.SpecializationInfoController;
import com.entity.SpecializationEntity;
import com.tools.Constant;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import org.apache.http.HttpResponse;

import java.util.ArrayList;

public class SpecializationTabController extends MenuController {

    private SpecializationInfoController specializationInfoController;
    private MenuController menuController;
    private StackPane stackPane_Change;
    private StackPane stackPane_Info;
    private static ArrayList<SpecializationEntity> specializations;
    private ObservableList<SpecializationEntity> specializationEntities;
    private Label label_PaneNameChange;
    private TextField textField_NameChange;
    private Label label_NameInfo;
    private static TableView<SpecializationEntity> specializationTable;

    @FXML
    private TableView<SpecializationEntity> tableView_Specialization;
    @FXML
    private TableColumn<SpecializationEntity, Number> tableColumn_Number;
    @FXML
    private TableColumn tableColumn_Name;

    public void init(MenuController menuController) {
        this.menuController = menuController;
        tableColumn_Number.setSortable(false);
        tableColumn_Number.setCellValueFactory(column ->
                new ReadOnlyObjectWrapper<Number>((tableView_Specialization.getItems().
                        indexOf(column.getValue()) + 1)));
        tableColumn_Name.setCellValueFactory(new PropertyValueFactory<SpecializationEntity, String>("name"));
        HttpResponse response = SpecializationEntity.getAllSpecialization();
        setStatusCode(response.getStatusLine().getStatusCode());
        if (checkStatusCode(getStatusCode())) {
            specializations = SpecializationEntity.getListFromResponse(response);
        }
        specializationEntities = FXCollections.observableArrayList(specializations);
        tableView_Specialization.setItems(specializationEntities);
        specializationTable = tableView_Specialization;
    }

    public void viewSpecialization() {
        if (specializationTable.getSelectionModel().getSelectedItem() != null) {
            activateStackPane(stackPane_Info, MainMenuController.getStackPanes());
            specializationInfoController.viewSpecializationInfo(specializationTable.getSelectionModel().getSelectedItem());
        }
    }

    public void deleteSpecialization(SpecializationEntity specialization) {
        int statusCode = specialization.deleteSpecialization();
        if (statusCode != 0) {
            if (checkStatusCode(statusCode)) {
                for (SpecializationEntity specializationEntity : specializationTable.getItems()) {
                    if (specializationEntity.getId() == specialization.getId()) {
                        specializationTable.getItems().remove(specializationEntity);
                        Constant.getAlert(null, "Specialization " + specializationEntity.getName() + " deleted!", Alert.AlertType.INFORMATION);
                        MainMenuController.deactivateAllStackPane();
                        break;
                    }
                }
            }
        }
    }

    public void setStackPaneChange(StackPane stackPane) {
        this.stackPane_Change = stackPane;
    }

    public void setStackPaneInfo(StackPane stackPane) {
        this.stackPane_Info = stackPane;
    }

    public static TableView<SpecializationEntity> getSpecializationTable() {
        return specializationTable;
    }

    public static ArrayList<SpecializationEntity> getSpecializations() {
        return specializations;
    }

    public void setSpecializationInfoController(SpecializationInfoController specializationInfoController) {
        this.specializationInfoController = specializationInfoController;
    }
}
