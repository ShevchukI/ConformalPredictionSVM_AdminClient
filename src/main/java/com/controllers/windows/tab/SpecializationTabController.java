package com.controllers.windows.tab;

import com.controllers.requests.SpecializationController;
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

public class SpecializationTabController extends MenuController{

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
        HttpResponse response = SpecializationController.getAllSpecialization();
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
        boolean result = Constant.questionOkCancel("Do you really want to delete Specialization "
                + specialization.getName() + " ?");
        if (result) {
            HttpResponse response = SpecializationController.deleteSpecialization(specialization.getId());
            int statusCode = response.getStatusLine().getStatusCode();
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

    public StackPane getStackPaneChange() {
        return stackPane_Change;
    }

    public void setStackPaneChange(StackPane stackPane) {
        this.stackPane_Change = stackPane;
    }

    public StackPane getStackPaneInfo() {
        return stackPane_Info;
    }

    public void setStackPaneInfo(StackPane stackPane) {
        this.stackPane_Info = stackPane;
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
