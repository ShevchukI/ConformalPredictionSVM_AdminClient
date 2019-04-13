package com.controllers.windows.menu;

import com.controllers.windows.admin.LoginMenuController;
import com.tools.Constant;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by User on 07.01.2019.
 */
public class MenuBarController extends MenuController {

    @Autowired
    LoginMenuController loginMenuController;

    private WindowsController windowsController;

    private MenuController menuController;

    public void init(MenuController menuController) {
        windowsController = new WindowsController();
        this.menuController = menuController;
    }

    public void closeApplication(ActionEvent event) {
        menuController.getStage().close();
    }

    public void signOut(ActionEvent event) throws IOException {
        windowsController.openWindow(Constant.getLoginMenuRoot(), menuController.getStage(), loginMenuController,
                null, false, 341, 236);
    }

//    public void changeName(ActionEvent event) throws IOException {
//        windowsController.openNewModalWindow("specialist/changeName", menuController.getStage(),
//                changeInfoMenuController, "Change name and surname", true, 400, 200);
//    }

//    public void changePassword(ActionEvent event) throws IOException {
//        windowsController.openNewModalWindow("specialist/changePassword", menuController.getStage(),
//                changeInfoMenuController, "Change password", false, 400, 200);
//
//    }

    public void about(ActionEvent event) {
       Constant.getAlert(null, "Client: Peryite\nServer: DayRo", Alert.AlertType.INFORMATION);
    }
}
