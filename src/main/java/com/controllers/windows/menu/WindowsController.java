package com.controllers.windows.menu;

import com.controllers.windows.admin.LoginMenuController;
import com.tools.Constant;
import com.tools.HazelCastMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

/**
 * Created by User on 16.03.2019.
 */
public class WindowsController {

    private Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();

    public void start(Stage stage) throws IOException {
        HazelCastMap.createInstanceAndMap();
        openWindow(Constant.getLoginMenuRoot(), stage, new LoginMenuController(), "Admin client", false, 341, 236);
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
        stage.show();
    }
}
