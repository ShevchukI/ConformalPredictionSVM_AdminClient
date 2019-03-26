package com.controllers.windows.entityInfo;

import com.controllers.windows.menu.MenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EmployeeStatusInfoController {

    private MenuController menuController;
    @FXML
    private Label label_Name;
    @FXML
    private Label label_WorkEnable;

    public void init(MenuController menuController) {
        this.menuController = menuController;
    }

    public Label getLabel_Name() {
        return label_Name;
    }

    public void setLabel_Name(Label label_Name) {
        this.label_Name = label_Name;
    }

    public Label getLabel_WorkEnable() {
        return label_WorkEnable;
    }

    public void setLabel_WorkEnable(Label label_WorkEnable) {
        this.label_WorkEnable = label_WorkEnable;
    }
}
