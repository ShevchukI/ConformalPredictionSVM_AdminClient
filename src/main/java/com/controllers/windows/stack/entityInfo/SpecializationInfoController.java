package com.controllers.windows.stack.entityInfo;

import com.controllers.windows.menu.MenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SpecializationInfoController {

    private MenuController menuController;

    @FXML
    private Label label_Name;

    public void init(MenuController menuController) {
        this.menuController = menuController;
    }

    public MenuController getMenuController() {
        return menuController;
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public Label getLabel_Name() {
        return label_Name;
    }

    public void setLabel_Name(Label label_Name) {
        this.label_Name = label_Name;
    }
}
