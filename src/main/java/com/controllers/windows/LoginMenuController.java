package com.controllers.windows;

import com.controllers.windows.menu.MenuController;
import com.tools.Constant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Created by Admin on 16.03.2019.
 */
public class LoginMenuController extends MenuController {

    @FXML
    private TextField textField_Login;
    @FXML
    private PasswordField passwordField_Password;
    @FXML
    private Button button_SignIn;
    @FXML
    private Tooltip tooltip_Login;
    @FXML
    private Tooltip tooltip_Password;
    private Tooltip tooltip_ErrorLogin = new Tooltip();
    private Tooltip tooltip_ErrorPassword = new Tooltip();

    public void initialize(Stage stage) {
        stage.setOnHidden(event -> {
            Constant.getInstance().getLifecycleService().shutdown();
        });
        setStage(stage);
        button_SignIn.setGraphic(new ImageView("/img/icons/signIn.png"));
    }

    public void signIn(ActionEvent event) {
        if (checkAuth()) {
            Constant.getAlert(null, "", Alert.AlertType.INFORMATION);
        }
    }

    private boolean checkAuth() {
        textField_Login.setTooltip(tooltip_Login);
        textField_Login.setStyle("-fx-border-color: inherit");
        passwordField_Password.setTooltip(tooltip_Password);
        passwordField_Password.setStyle("-fx-border-color: inherit");

        if (textField_Login.getText().equals("")) {
            tooltip_ErrorLogin.setText("Login is empty!");
            textField_Login.setTooltip(tooltip_ErrorLogin);
            textField_Login.setStyle("-fx-border-color: red");
            Constant.getAlert(null, "Login is empty!", Alert.AlertType.ERROR);
            return false;
        } else if (passwordField_Password.getText().equals("")) {
            tooltip_ErrorPassword.setText("Password is empty!");
            passwordField_Password.setTooltip(tooltip_ErrorPassword);
            passwordField_Password.setStyle("-fx-border-color: red");
            Constant.getAlert(null, "Password is empty!", Alert.AlertType.ERROR);
            return false;
        } else {
            return true;
        }
    }
}
