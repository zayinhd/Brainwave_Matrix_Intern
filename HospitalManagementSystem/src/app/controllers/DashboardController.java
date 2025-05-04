package app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class DashboardController {

    @FXML
    private BorderPane dashboardPane;

    @FXML
    private void handlePatients() {
        loadUI("Patients.fxml");
    }

    @FXML
    private void handleAppointments() {
        loadUI("Appointments.fxml");
    }

    @FXML
    private void handleEHR() {
        loadUI("EHR.fxml");
    }

    @FXML
    private void handleBilling() {
        loadUI("Billing.fxml");
    }

    @FXML
    private void handleInventory() {
        loadUI("Inventory.fxml");
    }

    @FXML
    private void handleStaff() {
        loadUI("Staff.fxml");
    }

    private void loadUI(String fxml) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/app/views/" + fxml));  // Fixed path
            dashboardPane.getChildren().setAll(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
