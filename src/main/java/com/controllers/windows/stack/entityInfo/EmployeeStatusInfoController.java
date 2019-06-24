package com.controllers.windows.stack.entityInfo;

import com.entity.EmployeeStatusEntity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeStatusInfoController implements Initializable{

    private static Label name;
    private static Label workEnable;

    @FXML
    private Label label_Name;
    @FXML
    private Label label_WorkEnable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name = label_Name;
        workEnable = label_WorkEnable;
    }

    public static void viewEmployeeStatusInfo(EmployeeStatusEntity employeeStatusEntity) {
        name.setText(employeeStatusEntity.getName());
        workEnable.setText(employeeStatusEntity.getVisibleWorkEnable());
    }

}
