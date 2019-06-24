package com.controllers.windows.tab;

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

    private static TableView<EmployeeStatusEntity> employeeStatusTable;
    private static StackPane stackPane_Change;
    private static StackPane stackPane_Info;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableColumn_Number.setSortable(false);
        tableColumn_Number.setCellValueFactory(column ->
                new ReadOnlyObjectWrapper<Number>((tableView_EmployeeStatus.getItems().
                        indexOf(column.getValue()) + 1)));
        tableColumn_Name.setCellValueFactory(new PropertyValueFactory<EmployeeStatusEntity, String>("name"));
        tableColumn_WorkEnable.setCellValueFactory(new PropertyValueFactory<EmployeeStatusEntity, String>("visibleWorkEnable"));
        HttpResponse response = EmployeeStatusEntity.getAllEmployeeStatus();
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
        }
    }

    public static void deleteEmployeeStatus(EmployeeStatusEntity employeeStatus) {
        int statusCode = employeeStatus.deleteEmployeeStatus();
        if (statusCode != 0) {
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


    public static void setStackPaneInfo(StackPane stackPane) {
        stackPane_Info = stackPane;
    }

    public static void setStackPaneChange(StackPane stackPane) {
        stackPane_Change = stackPane;
    }

    public static ArrayList<EmployeeStatusEntity> getStatusEntities() {
        return statusEntities;
    }

    public static TableView<EmployeeStatusEntity> getEmployeeStatusTable() {
        return employeeStatusTable;
    }

}
