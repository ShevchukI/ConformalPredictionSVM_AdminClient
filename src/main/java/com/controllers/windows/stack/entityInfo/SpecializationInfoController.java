package com.controllers.windows.stack.entityInfo;

import com.entity.SpecializationEntity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SpecializationInfoController implements Initializable{

    private static Label name;

    @FXML
    private Label label_Name;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name = label_Name;
    }

    public static void viewSpecializationInfo(SpecializationEntity specializationEntity){
        name.setText(specializationEntity.getName());
    }

    public Label getLabel_Name() {
        return label_Name;
    }

}
