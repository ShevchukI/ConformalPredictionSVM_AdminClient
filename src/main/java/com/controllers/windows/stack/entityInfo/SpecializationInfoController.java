package com.controllers.windows.stack.entityInfo;

import com.controllers.windows.menu.MenuController;
import com.entity.SpecializationEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SpecializationInfoController{

    private MenuController menuController;

    @FXML
    private Label label_Name;

    public void init(MenuController menuController) {
        this.menuController = menuController;
    }

    public void viewSpecializationInfo(SpecializationEntity specializationEntity){
        label_Name.setText(specializationEntity.getName());
    }

}
