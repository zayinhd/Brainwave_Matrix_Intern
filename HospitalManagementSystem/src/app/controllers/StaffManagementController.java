package app.controllers;


import app.util.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StaffManagementController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("Doctor", "Nurse", "Admin", "Receptionist", "Technician");
        statusComboBox.getItems().addAll("Active", "Inactive");
    }

    @FXML
    private void addStaff() {
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String role = roleComboBox.getValue();
            String status = statusComboBox.getValue();

            String sql = "INSERT INTO staff (first_name, last_name, email, phone, role, status) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, email);
                stmt.setString(4, phone);
                stmt.setString(5, role);
                stmt.setString(6, status);

                int rows = stmt.executeUpdate();
                statusLabel.setText(rows > 0 ? "Staff member added!" : "Failed to add staff.");
                if (rows > 0) clearForm();
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error adding staff.");
        }
    }

    @FXML
    private void updateStaff() {
        try {
            String email = emailField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String phone = phoneField.getText();
            String role = roleComboBox.getValue();
            String status = statusComboBox.getValue();

            String sql = "UPDATE staff SET first_name = ?, last_name = ?, phone = ?, role = ?, status = ? WHERE email = ?";

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, phone);
                stmt.setString(4, role);
                stmt.setString(5, status);
                stmt.setString(6, email);

                int rows = stmt.executeUpdate();
                statusLabel.setText(rows > 0 ? "Staff details updated!" : "Failed to update staff.");
                if (rows > 0) clearForm();
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error updating staff.");
        }
    }

    @FXML
    private void loadStaff() {
        try {
            String email = emailField.getText();

            String sql = "SELECT * FROM staff WHERE email = ?";

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    firstNameField.setText(rs.getString("first_name"));
                    lastNameField.setText(rs.getString("last_name"));
                    phoneField.setText(rs.getString("phone"));
                    roleComboBox.setValue(rs.getString("role"));
                    statusComboBox.setValue(rs.getString("status"));
                    statusLabel.setText("Staff loaded.");
                } else {
                    statusLabel.setText("No staff found.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error loading staff.");
        }
    }

    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        phoneField.clear();
        roleComboBox.getSelectionModel().clearSelection();
        statusComboBox.getSelectionModel().clearSelection();
    }
}
