package com.controllers.windows.admin;

import com.controllers.requests.AdminController;
import com.controllers.windows.menu.MainMenuController;
import com.controllers.windows.menu.MenuController;
import com.controllers.windows.menu.WindowsController;
import com.tools.Constant;
import com.tools.HazleCastMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by User on 16.03.2019.
 */
public class LoginMenuController extends MenuController {

    @Autowired
    MainMenuController mainMenuController;
    @Autowired
    HttpResponse response;

    private WindowsController windowsController;
    private AdminController adminController;

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
    private Tooltip tooltip_ErrorLogin;
    private Tooltip tooltip_ErrorPassword;

    public void initialize(Stage stage) {
        stage.setOnHidden(event -> {
            HazleCastMap.getInstance().getLifecycleService().shutdown();
        });
        setStage(stage);
        windowsController = new WindowsController();
        adminController = new AdminController();
        tooltip_ErrorLogin = new Tooltip();
        tooltip_ErrorPassword = new Tooltip();
        button_SignIn.setGraphic(new ImageView(Constant.getSignInButtonIcon()));
    }

    public void signIn(ActionEvent event) {
        if (checkAuth()) {
            String[] authorization = new String[2];
            authorization[0] = textField_Login.getText();
            authorization[1] = passwordField_Password.getText();
            try {
                response = adminController.getAdminAuth(authorization);
                setStatusCode(response.getStatusLine().getStatusCode());
                if(checkStatusCode(getStatusCode())) {
                    HazleCastMap.fillUserMap(authorization);
                    windowsController.openWindow(Constant.getMainMenuRoot(), getStage(), mainMenuController,
                            null, true, 950, 650);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkAuth() {
        textField_Login.setTooltip(tooltip_Login);
        textField_Login.setStyle(Constant.getBorderColorInherit());
        passwordField_Password.setTooltip(tooltip_Password);
        passwordField_Password.setStyle(Constant.getBorderColorInherit());
        if (textField_Login.getText().equals("")) {
            tooltip_ErrorLogin.setText("Login is empty!");
            textField_Login.setTooltip(tooltip_ErrorLogin);
            textField_Login.setStyle(Constant.getBorderColorRed());
            Constant.getAlert(null, "Login is empty!", Alert.AlertType.ERROR);
            return false;
        } else if (passwordField_Password.getText().equals("")) {
            tooltip_ErrorPassword.setText("Password is empty!");
            passwordField_Password.setTooltip(tooltip_ErrorPassword);
            passwordField_Password.setStyle(Constant.getBorderColorRed());
            Constant.getAlert(null, "Password is empty!", Alert.AlertType.ERROR);
            return false;
        } else {
            return true;
        }
    }
}
