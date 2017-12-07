package core;

import javafx.scene.Scene;
import javafx.stage.Stage;
import main.controller.MainLayoutController;

/**
 * Created by Manuel Wimmer
 * Date: 29.11.17
 * Desc:
 */

public class Application extends javafx.application.Application {

    public Application() {

    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Multi WP Updater Desktop");

//        this.primaryStage.getIcons().add(new Image(""));
        primaryStage.setScene(new Scene(new MainLayoutController(), 1200, 800));
        primaryStage.show();
    }
}