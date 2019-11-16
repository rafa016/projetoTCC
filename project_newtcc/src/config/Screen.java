package config;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Screen extends Application {

    private static String path;
    private static String title;
    private static Stage stage;

    public void initial(){
        setPath("TelaLogin");
        setTitle("Eternidad");
    }

    public void restart(String path){
        setPath(path);
        try {
            getStage().close();
            start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restartTelas(String path){
        setPath(path);
        try {
            startTelas(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(getPath()));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle(getTitle());
        stage.setScene(scene);
        stage.show();
        setStage(stage);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
    }

    public void startTelas(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(getPath()));
        Scene scene = new Scene(root);
        stage.setTitle(getTitle());
        stage.setScene(scene);
        stage.show();
        setStage(stage);
    }

    public void startGridView(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/GridPaneView.fxml"));
        primaryStage.setTitle("Tabela TCC");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    public void startHistoricoLOG(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/TelaHistoricoLog.fxml"));
        primaryStage.setTitle("Histórico (LOG)");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    public void close(){
        getStage().close();
    }
    private Stage getStage() {
        return stage;
    }
    private void setStage(Stage stage) {
        Screen.stage = stage;
    }
    private String getPath() {
        return path;
    }
    private void setPath(String path) {
        Screen.path = "../view/" + path + ".fxml";
    }
    private String getTitle() {
        return title;
    }
    private void setTitle(String title) {
        Screen.title = title;
    }
}
