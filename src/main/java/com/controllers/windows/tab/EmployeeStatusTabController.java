package com.controllers.windows.tab;

import com.controllers.requests.EmployeeStatusController;
import com.controllers.windows.menu.MainMenuController;
import com.controllers.windows.menu.MenuController;
import com.controllers.windows.stack.entityInfo.EmployeeStatusInfoController;
import com.entity.EmployeeStatusEntity;
import com.tools.Constant;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import org.apache.http.HttpResponse;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeStatusTabController extends MenuController implements Initializable {

//    @Autowired
//    HttpResponse response;

    private static TableView<EmployeeStatusEntity> employeeStatusTable;

//    private MenuController menuController;
    private static StackPane stackPane_Change;
    private static StackPane stackPane_Info;
//    private ArrayList<StackPane> stackPanes;
    private static ArrayList<EmployeeStatusEntity> statusEntities;

    private static ObservableList<EmployeeStatusEntity> employeeStatusEntities;
    private Label label_PaneNameChange;
    private TextField textField_NameChange;
    private CheckBox checkBox_WorkEnableChange;
    private Label label_NameInfo;
    private Label label_WorkEnableInfo;

    @FXML
    private TableView<EmployeeStatusEntity> tableView_EmployeeStatus;
    @FXML
    private TableColumn<EmployeeStatusEntity, Number> tableColumn_Number;
    @FXML
    private TableColumn tableColumn_Name;
    @FXML
    private TableColumn tableColumn_WorkEnable;

//    public void init(MenuController menuController, ArrayList<EmployeeStatusEntity> statusEntities) {
//        this.menuController = menuController;
//        this.statusEntities = statusEntities;
//        tableColumn_Number.setSortable(false);
//        tableColumn_Number.setCellValueFactory(column ->
//                new ReadOnlyObjectWrapper<Number>((tableView_EmployeeStatus.getItems().
//                        indexOf(column.getValue()) + 1)));
//        tableColumn_Name.setCellValueFactory(new PropertyValueFactory<EmployeeStatusEntity, String>("name"));
//        tableColumn_WorkEnable.setCellValueFactory(new PropertyValueFactory<EmployeeStatusEntity, String>("visibleWorkEnable"));
//        employeeStatusEntities = FXCollections.observableList(statusEntities);
//        tableView_EmployeeStatus.setItems(employeeStatusEntities);
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableColumn_Number.setSortable(false);
        tableColumn_Number.setCellValueFactory(column ->
                new ReadOnlyObjectWrapper<Number>((tableView_EmployeeStatus.getItems().
                        indexOf(column.getValue()) + 1)));
        tableColumn_Name.setCellValueFactory(new PropertyValueFactory<EmployeeStatusEntity, String>("name"));
        tableColumn_WorkEnable.setCellValueFactory(new PropertyValueFactory<EmployeeStatusEntity, String>("visibleWorkEnable"));
        HttpResponse response = EmployeeStatusController.getAllEmployeeStatus();
        setStatusCode(response.getStatusLine().getStatusCode());
        if (checkStatusCode(getStatusCode())) {
            statusEntities = EmployeeStatusEntity.getListFromResponse(response);
        }
        employeeStatusEntities = FXCollections.observableList(statusEntities);
        tableView_EmployeeStatus.setItems(employeeStatusEntities);
        employeeStatusTable = tableView_EmployeeStatus;
    }


    public void viewEmployeeStatus() {
        if (tableView_EmployeeStatus.getSelectionModel().getSelectedItem() != null) {
            activateStackPane(stackPane_Info, MainMenuController.getStackPanes());
            EmployeeStatusInfoController.viewEmployeeStatusInfo(employeeStatusTable.getSelectionModel().getSelectedItem());
//            label_NameInfo
//                    .setText(tableView_EmployeeStatus.getSelectionModel().getSelectedItem().getName());
//            label_WorkEnableInfo
//                    .setText(tableView_EmployeeStatus.getSelectionModel().getSelectedItem().getVisibleWorkEnable());
        }
    }

    public static void deleteEmployeeStatus(EmployeeStatusEntity employeeStatus) {
        boolean result = Constant.questionOkCancel("Do you really want to delete Employee Status "
                + employeeStatus.getName() + " ?");
        if (result) {
            HttpResponse response = EmployeeStatusController.deleteEmployeeStatus(employeeStatus.getId());
            int statusCode = response.getStatusLine().getStatusCode();
            if (checkStatusCode(statusCode)) {
                for (EmployeeStatusEntity employeeStatusEntity : employeeStatusTable.getItems()) {
                    if (employeeStatusEntity.getId() == employeeStatus.getId()) {
                        employeeStatusTable.getItems().remove(employeeStatusEntity);
                        Constant.getAlert(null, "Employee status " + employeeStatusEntity.getName() + " deleted!", Alert.AlertType.INFORMATION);
                        MainMenuController.deactivateAllStackPane();
                        break;
                    }
                }
            }
        }
    }

//    public void deleteEmployee() {
//        if (tableView_EmployeeStatus.getSelectionModel().getSelectedItem() != null) {
//            deleteEmployeeStatusFromTable(tableView_EmployeeStatus);
//        }
//    }
//
//    public void deleteEmployeeStatusFromTable(TableView<EmployeeStatusEntity> tableView) {
//        boolean result = Constant.questionOkCancel("Do you really want to delete Employee Status "
//                + tableView.getSelectionModel().getSelectedItem().getName() + " ?");
//        if (result) {
//            int id = tableView.getSelectionModel().getSelectedItem().getId();
//            response = EmployeeStatusController.deleteEmployeeStatus(id);
//            setStatusCode(response.getStatusLine().getStatusCode());
//            if (checkStatusCode(getStatusCode())) {
//                for (EmployeeStatusEntity employeeStatusEntity : tableView.getItems()) {
//                    if (employeeStatusEntity.getId() == id) {
//                        tableView.getItems().remove(employeeStatusEntity);
//                        Constant.getAlert(null, "Employee status " + employeeStatusEntity.getName() + " deleted!", Alert.AlertType.INFORMATION);
//                        MainMenuController.deactivateAllStackPane();
//                        break;
//                    }
//                }
//            }
//        }
//    }

//    public void changeEmployeeStatus() {
//        if (tableView_EmployeeStatus.getSelectionModel().getSelectedItem() != null) {
//            changeEmployeeStatusFromTable(tableView_EmployeeStatus);
//        }
//    }
//
//    public void changeEmployeeStatusFromTable(TableView<EmployeeStatusEntity> tableView) {
//        HazleCastMap.getMapByName(HazleCastMap.getMiscellaneousMapName()).put("employeeStatus",
//                tableView.getSelectionModel().getSelectedItem().getId());
//        activateStackPane(stackPane_Change, stackPanes);
//        label_PaneNameChange.setText("Change Employee Status");
//        textField_NameChange
//                .setText(tableView.getSelectionModel().getSelectedItem().getName());
//        checkBox_WorkEnableChange
//                .setSelected(tableView.getSelectionModel().getSelectedItem().isWorkEnable());
//    }

    public Label getLabel_NameInfo() {
        return label_NameInfo;
    }

    public void setLabel_NameInfo(Label label_Name) {
        this.label_NameInfo = label_Name;
    }

    public Label getLabel_WorkEnableInfo() {
        return label_WorkEnableInfo;
    }

    public void setLabel_WorkEnableInfo(Label label_WorkEnable) {
        this.label_WorkEnableInfo = label_WorkEnable;
    }

    public static StackPane getStackPaneInfo() {
        return stackPane_Info;
    }

    public static void setStackPaneInfo(StackPane stackPane) {stackPane_Info = stackPane;
    }

//    public ArrayList<StackPane> getStackPanes() {
//        return stackPanes;
//    }
//
//    public void setStackPanes(ArrayList<StackPane> stackPanes) {
//        this.stackPanes = stackPanes;
//    }

    public TableView<EmployeeStatusEntity> getTableView_EmployeeStatus() {
        return tableView_EmployeeStatus;
    }

    public static StackPane getStackPaneChange() {
        return stackPane_Change;
    }

    public static void setStackPaneChange(StackPane stackPane) {stackPane_Change = stackPane;
    }

    public Label getLabel_PaneNameChange() {
        return label_PaneNameChange;
    }

    public void setLabel_PaneNameChange(Label label_PaneName) {
        this.label_PaneNameChange = label_PaneName;
    }

    public TextField getTextField_NameChange() {
        return textField_NameChange;
    }

    public void setTextField_NameChange(TextField textField_NameChange) {
        this.textField_NameChange = textField_NameChange;
    }

    public CheckBox getCheckBox_WorkEnableChange() {
        return checkBox_WorkEnableChange;
    }

    public void setCheckBox_WorkEnableChange(CheckBox checkBox_WorkEnableChange) {
        this.checkBox_WorkEnableChange = checkBox_WorkEnableChange;
    }

    public static ArrayList<EmployeeStatusEntity> getStatusEntities() {
        return statusEntities;
    }

    public static void setStatusEntities(ArrayList<EmployeeStatusEntity> statuses) {
        statusEntities = statuses;
    }

    public static TableView<EmployeeStatusEntity> getEmployeeStatusTable() {
        return employeeStatusTable;
    }

    public static ObservableList<EmployeeStatusEntity> getEmployeeStatusEntities() {
        return employeeStatusEntities;
    }

    public static void setEmployeeStatusEntities(ObservableList<EmployeeStatusEntity> employeeStatusEntities) {
        EmployeeStatusTabController.employeeStatusEntities = employeeStatusEntities;
    }
}
