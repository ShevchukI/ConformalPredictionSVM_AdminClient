package com.controllers.windows.stack.entity;

import com.controllers.windows.menu.MainMenuController;
import com.controllers.windows.menu.MenuController;
import com.controllers.windows.tab.EmployeeStatusTabController;
import com.entity.EmployeeStatusEntity;
import com.tools.Constant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangeEmployeeStatusController extends MenuController implements Initializable {

    private static EmployeeStatusEntity employeeStatusEntity;
    private static boolean change;
    private static Label paneName;
    private static TextField name;
    private static CheckBox workEnable;

    @FXML
    private Label label_PaneName;
    @FXML
    private TextField textField_Name;
    @FXML
    private CheckBox checkBox_WorkEnable;
    @FXML
    private Button button_Save;
    @FXML
    private Button button_Cancel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneName = label_PaneName;
        name = textField_Name;
        workEnable = checkBox_WorkEnable;

        employeeStatusEntity = new EmployeeStatusEntity();

        button_Save.setGraphic(Constant.okIcon());
        button_Cancel.setGraphic(Constant.cancelIcon());
    }

    public static void create() {
        change = false;
        paneName.setText("Add New Employee Status");
        name.clear();
        workEnable.setSelected(true);
    }

    public static void change(EmployeeStatusEntity employeeStatus) {
        change = true;
        employeeStatusEntity = employeeStatus;
        paneName.setText("Change Employee Status");
        name.setText(employeeStatus.getName());
        workEnable.setSelected(employeeStatus.isWorkEnable());
    }

    public void save(ActionEvent event) throws IOException {
        if (change) {
            changeCurrent();
        } else {
            addNew();
        }
    }

    private void addNew() {
        employeeStatusEntity.addNew(name.getText(), workEnable.isSelected());
        EmployeeStatusTabController.getEmployeeStatusTable().getItems().add(employeeStatusEntity);
        EmployeeStatusTabController.getEmployeeStatusTable().refresh();
        close();
    }

    private void changeCurrent() {
        int statusCode = employeeStatusEntity.change(name.getText(), workEnable.isSelected());
        if (statusCode != 0) {
            if (checkStatusCode(getStatusCode())) {
                TableView<EmployeeStatusEntity> tableView = EmployeeStatusTabController.getEmployeeStatusTable();
                for (EmployeeStatusEntity employeeStatus : tableView.getItems()) {
                    if (employeeStatus.getId() == employeeStatusEntity.getId()) {
                        employeeStatus.setName(employeeStatusEntity.getName());
                        employeeStatus.setWorkEnable(employeeStatusEntity.isWorkEnable());
                    }
                }
                tableView.refresh();
                Constant.getAlert(null, "Employee status changed!", Alert.AlertType.INFORMATION);
                close();
            }
        }
    }

    private void close() {
        name.clear();
        workEnable.setSelected(true);
        MainMenuController.deactivateAllStackPane();
    }

    public void cancel() {
        boolean result = Constant.questionOkCancel("Do you want the cancel to operation?");
        if (result) {
            close();
        }
    }

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

}
