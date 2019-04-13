package com;

import com.controllers.windows.menu.WindowsController;
import javafx.stage.Stage;

/**
 * Created by User on 16.03.2019.
 */
public class Application extends javafx.application.Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new WindowsController().start(stage);
    }

}
