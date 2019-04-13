package com.controllers.windows.menu;

import com.controllers.windows.admin.LoginMenuController;
import com.tools.Constant;
import com.tools.HazleCastMap;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

/**
 * Created by User on 16.03.2019.
 */
public class WindowsController {

    private Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

    public void start(Stage stage) throws IOException {
        HazleCastMap.createInstanceAndMap();
        openWindow(Constant.getLoginMenuRoot(), stage, new LoginMenuController(), "User client", false, 341, 236);
//        FXMLLoader loginMenuLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/admin/loginMenu.fxml"));
//        Pane loginMenuPane = (Pane) loginMenuLoader.load();
//        Scene loginMenuScene = new Scene(loginMenuPane);
//        stage.setScene(loginMenuScene);
//        stage.setResizable(false);
//        stage.setTitle("Login menu");
////        stage.getIcons().add(new Image("img/icons/icons.png"));
//        LoginMenuController loginMenuController = (LoginMenuController) loginMenuLoader.getController();
//        loginMenuController.initialize(stage);
//        stage.show();
    }

    public void openWindow(String rootName, Stage stage, MenuController controller, String title, boolean resizable, int width, int height) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(rootName));
        Pane pane = (Pane) loader.load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(resizable);
        if (resizable) {
            stage.setMinWidth(width);
            stage.setMinHeight(height);
            stage.setMaxWidth(sSize.getWidth());
            stage.setMaxHeight(sSize.getHeight());
        } else {
            stage.setMinWidth(width);
            stage.setMinHeight(height);
            stage.setMaxWidth(width);
            stage.setMaxHeight(height);
        }
        if (title != null) {
            stage.setTitle(title);
        }
        stage.getIcons().add(new Image(Constant.getApplicationIcon()));
        controller = (MenuController) loader.getController();
        controller.initialize(stage);
//        if (rootName.equals("loginMenu.fxml")) {
//            stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
//            stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
//        }
        stage.show();
    }

//    public void openWindowResizable(String rootName, Stage stage, MenuController controller, String title, int minWidth, int minHeight) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(rootName));
//        Pane pane = (Pane) loader.load();
//        Scene scene = new Scene(pane);
//        stage.setScene(scene);
//        stage.setMinWidth(minWidth);
//        stage.setMinHeight(minHeight);
//        stage.setMaxWidth(sSize.getWidth());
//        stage.setMaxHeight(sSize.getHeight());
//        stage.setResizable(true);
//        stage.setTitle(title);
//        stage.getIcons().add(new Image(Constant.getApplicationIcon()));
//        controller = (MenuController) loader.getController();
//        controller.initialize(stage);
//        stage.show();
//    }
}
