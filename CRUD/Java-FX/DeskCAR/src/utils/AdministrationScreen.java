package utils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AdministrationScreen extends Application{
    
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root =  FXMLLoader.load(getClass().getResource("/view/administrationScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("DeskCAR - Administração");
        stage.show();
        stage.getIcons().add(new Image("/images/icon.png"));
        setStage(stage);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        AdministrationScreen.stage = stage;
    }
}
