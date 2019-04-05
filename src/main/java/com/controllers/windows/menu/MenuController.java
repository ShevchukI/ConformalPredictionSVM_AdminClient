package com.controllers.windows.menu;

import com.tools.Constant;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Admin on 10.01.2019.
 */
public abstract class MenuController {

    private Stage stage;
    private Stage newWindow;
    private int statusCode;
    private final String borderStatusRed = "-fx-border-color: red";
    private final String borderStatusInherit = "-fx-border-color: inherit";

    public void initialize(Stage stage) throws IOException {
        stage.setOnHidden(event -> {
            Constant.getInstance().getLifecycleService().shutdown();
        });
        setStage(stage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void setNewWindow(Stage newWindow) {
        this.newWindow = newWindow;
    }

    public Stage getNewWindow() {
        return newWindow;
    }

    public boolean checkStatusCode(int statusCode) {
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
            case 504:
                Constant.getAlert("Connection to the server is missing!",
                        "Error code: " + statusCode, Alert.AlertType.ERROR);
                return false;
            default:
                return false;
        }
    }

    public void initialize(Stage stage, Stage newWindow, boolean change) throws IOException {
    }

    public void initialize(Stage stage, Stage newWindow) throws IOException {
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

    public void deactivateAllStackPane(ArrayList<StackPane> stackPanes) {
        for (StackPane stackPane : stackPanes) {
            stackPane.setDisable(true);
            stackPane.setVisible(false);
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    protected String[] getContent(HttpResponse response) {
        String[] strings = new String[10];
        String[] content = new String[3];
        try {
            strings = Constant.responseToString(response).split("\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] login = strings[3].split("_");
        content[0] = login[2];
        content[1] = strings[3];
        content[2] = strings[7];
        return content;
    }

    protected void setErrorTooltip(TextField textField, Tooltip tooltip_Error){
        textField.setTooltip(tooltip_Error);
        textField.setStyle(borderStatusRed);
    }

    protected void setDefaultTooltip(TextField textField, Tooltip tooltip_Default){
        textField.setTooltip(tooltip_Default);
        textField.setStyle(borderStatusInherit);
    }

    public String getBorderStatusRed() {
        return borderStatusRed;
    }

    public String getBorderStatusInherit() {
        return borderStatusInherit;
    }
}
