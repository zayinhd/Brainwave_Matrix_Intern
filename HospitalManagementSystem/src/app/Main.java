package app;

import app.util.DBUtil;
import java.io.IOException;
import java.sql.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
//    public static void main(String[] args) {
//        Connection conn = DBUtil.getConnection();
//        if (conn != null) {
//            System.out.println("Connection is active.");
//        } else {
//            System.out.println("Failed to connect.");
//        }
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/Login.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setTitle("Hospital Management System");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
