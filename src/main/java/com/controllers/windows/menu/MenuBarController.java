package com.controllers.windows.menu;

import com.controllers.windows.admin.LoginMenuController;
import com.tools.Constant;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.io.IOException;

/**
 * Created by User on 07.01.2019.
 */
public class MenuBarController extends MenuController {

    private LoginMenuController loginMenuController;
    private WindowsController windowsController;

    private MenuController menuController;

    public void init(MenuController menuController) {
        loginMenuController = new LoginMenuController();
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

    public void about(ActionEvent event) {
        Constant.getAlert(null, "Client: Peryite\nServer: DayRo", Alert.AlertType.INFORMATION);
    }
}
