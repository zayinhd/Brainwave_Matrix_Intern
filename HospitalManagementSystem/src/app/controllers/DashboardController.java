package app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class DashboardController {

    @FXML
    private StackPane contentArea;

    @FXML
    private void handlePatients(ActionEvent event) {
        loadUI("/app/views/Patient.fxml");
    }

    @FXML
    private void handleAppointments(ActionEvent event) {
        loadUI("/app/views/Appointment.fxml");
    }

    @FXML
    private void handleDoctors(ActionEvent event) {
        loadUI("/app/views/Doctor.fxml");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/Login.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUI(String fxmlPath) {
        try {
            Pane pane = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/Dashboard.fxml"));
            Scene dashboardScene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(dashboardScene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
