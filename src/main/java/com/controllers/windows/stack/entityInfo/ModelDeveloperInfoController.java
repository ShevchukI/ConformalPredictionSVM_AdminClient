package com.controllers.windows.stack.entityInfo;

import com.controllers.windows.menu.MenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ModelDeveloperInfoController {

    private MenuController menuController;

    @FXML
    private Label label_Name;
    @FXML
    private Label label_Surname;
    @FXML
    private Label label_Telephone;
    @FXML
    private Label label_Email;
    @FXML
    private Label label_Status;

    public void init(MenuController menuController) {
        this.menuController = menuController;
    }

    public Label getLabel_Name() {
        return label_Name;
    }

    public Label getLabel_Surname() {
        return label_Surname;
    }

    public Label getLabel_Telephone() {
        return label_Telephone;
    }

    public Label getLabel_Email() {
        return label_Email;
    }

    public Label getLabel_Status() {
        return label_Status;
    }

}
