package app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();


        if ("admin".equals(username) && "admin123".equals(password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/Dashboard.fxml"));
                Scene dashboardScene = new Scene(loader.load());

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(dashboardScene);
                stage.setTitle("Dashboard - Hospital Management System");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                errorLabel.setText("Failed to load dashboard.");
            }
        } else {
            errorLabel.setText("Invalid credentials.");
        }
    }
}
