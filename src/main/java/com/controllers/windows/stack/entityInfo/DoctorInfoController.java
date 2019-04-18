package com.controllers.windows.stack.entityInfo;

import com.controllers.windows.menu.MenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DoctorInfoController {
    private MenuController menuController;

    @FXML
    private Label label_Name;
    @FXML
    private Label label_Surname;
    @FXML
    private Label label_Specialization;
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

    public void setLabel_Name(Label label_Name) {
        this.label_Name = label_Name;
    }

    public Label getLabel_Surname() {
        return label_Surname;
    }

    public void setLabel_Surname(Label label_Surname) {
        this.label_Surname = label_Surname;
    }

    public Label getLabel_Specialization() {
        return label_Specialization;
    }

    public void setLabel_Specialization(Label label_Specialization) {
        this.label_Specialization = label_Specialization;
    }

    public Label getLabel_Telephone() {
        return label_Telephone;
    }

    public void setLabel_Telephone(Label label_Telephone) {
        this.label_Telephone = label_Telephone;
    }

    public Label getLabel_Email() {
        return label_Email;
    }

    public void setLabel_Email(Label label_Email) {
        this.label_Email = label_Email;
    }

    public Label getLabel_Status() {
        return label_Status;
    }

    public void setLabel_Status(Label label_Status) {
        this.label_Status = label_Status;
    }
}