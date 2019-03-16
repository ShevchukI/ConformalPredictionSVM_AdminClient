package com.controllers.windows.menu;

import com.controllers.windows.LoginMenuController;
import com.tools.Constant;
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
 * Created by Admin on 16.03.2019.
 */
public class WindowsController {

    private Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

    public void start(Stage stage) throws IOException {
        Constant.createInstanceAndMap();
        openWindow("admin/loginMenu", stage, new LoginMenuController(), "Admin client", -1, -1);
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

    public void openWindow(String rootName, Stage stage, MenuController controller, String title, int width, int height) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/" + rootName + ".fxml"));
        Pane pane = (Pane) loader.load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        if(width>0 && height>0){
            stage.setMinWidth(width);
            stage.setMinHeight(height);
            stage.setMaxWidth(width);
            stage.setMaxHeight(height);
        }
        stage.setResizable(false);
        if(title!=null) {
            stage.setTitle(title);
        }
        stage.getIcons().add(new Image("img/icons/icon.png"));
        controller = (MenuController) loader.getController();
        controller.initialize(stage);
//        if (rootName.equals("loginMenu.fxml")) {
//            stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
//            stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
//        }
        stage.show();
    }
}
