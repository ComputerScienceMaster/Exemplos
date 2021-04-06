package utils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Buyer;

public class MainScreen extends Application{
    
    private static Stage stage;
    private static Buyer buyerLogged;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root =  FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("DeskCAR");
        stage.show();
        stage.getIcons().add(new Image("/images/icon.png"));
        setStage(stage);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        MainScreen.stage = stage;
    }

    public static Buyer getBuyerLogged() {
        return buyerLogged;
    }

    public static void setBuyerLogged(Buyer buyerLogged) {
        MainScreen.buyerLogged = buyerLogged;
    }
}
