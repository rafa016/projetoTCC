package application;

import config.Screen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Screen screen = new Screen();
        screen.initial();
        screen.start(primaryStage);
    }
}