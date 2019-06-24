package com.controllers.windows.menu;

import com.tools.Constant;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by User on 10.01.2019.
 */
public abstract class MenuController {

    private Stage stage;
    private int statusCode;

    public void initialize(Stage stage) throws IOException {
        setStage(stage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public static boolean checkStatusCode(int statusCode) {
        switch (statusCode) {
            case 200:
                return true;
            case 201:
                return true;
            case 401:
                Constant.getAlert("Unauthorized: login or password incorrect!",
                        "Error code: " + statusCode, Alert.AlertType.ERROR);
                return false;
            case 404:
                Constant.getAlert("Error!", String.valueOf(statusCode), Alert.AlertType.ERROR);
                return false;
            case 504:
                Constant.getAlert("Connection to the server is missing!",
                        "Error code: " + statusCode, Alert.AlertType.ERROR);
                return false;
            default:
                return false;
        }
    }

    public void activateStackPane(StackPane pane, ArrayList<StackPane> stackPanes) {
        for (StackPane stackPane : stackPanes) {
            if (stackPane.equals(pane)) {
                stackPane.setDisable(false);
                stackPane.setVisible(true);
            } else {
                stackPane.setDisable(true);
                stackPane.setVisible(false);
            }
        }
    }



    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    protected void setErrorTooltip(TextField textField, Tooltip tooltip_Error){
        textField.setTooltip(tooltip_Error);
        textField.setStyle(Constant.getBorderColorRed());
    }

    protected void setDefaultTooltip(TextField textField, Tooltip tooltip_Default){
        textField.setTooltip(tooltip_Default);
        textField.setStyle(Constant.getBorderColorInherit());
    }

}
