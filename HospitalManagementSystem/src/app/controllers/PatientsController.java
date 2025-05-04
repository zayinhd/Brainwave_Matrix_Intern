package app.controllers;


import app.util.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class PatientsController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private DatePicker dobPicker;
    @FXML private TextField contactField;
    @FXML private TextArea addressArea;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        genderComboBox.getItems().addAll("Male", "Female", "Other");
    }

    @FXML
    private void registerPatient() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String gender = genderComboBox.getValue();
        LocalDate dob = dobPicker.getValue();
        String contact = contactField.getText();
        String address = addressArea.getText();

        String sql = "INSERT INTO patients (first_name, last_name, gender, dob, contact, address) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, gender);
            stmt.setDate(4, java.sql.Date.valueOf(dob));
            stmt.setString(5, contact);
            stmt.setString(6, address);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                statusLabel.setText("Patient registered successfully!");
                clearForm();
            } else {
                statusLabel.setText("Registration failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error occurred.");
        }
    }

    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        genderComboBox.getSelectionModel().clearSelection();
        dobPicker.setValue(null);
        contactField.clear();
        addressArea.clear();
    }
}
