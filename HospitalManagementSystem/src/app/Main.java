package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {

    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/app/views/Login.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hospital Management System");
            stage.setScene(scene);
            stage.show();
            
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}
