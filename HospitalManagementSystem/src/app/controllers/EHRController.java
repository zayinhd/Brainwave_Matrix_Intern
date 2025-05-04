
package app.controllers;

import app.util.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EHRController {

    @FXML private TextField patientIdField;
    @FXML private TextArea diagnosisArea;
    @FXML private TextArea prescriptionArea;
    @FXML private TextArea allergyArea;
    @FXML private TextArea notesArea;
    @FXML private Label statusLabel;

    @FXML
    private void saveRecord() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText());
            String diagnosis = diagnosisArea.getText();
            String prescriptions = prescriptionArea.getText();
            String allergies = allergyArea.getText();
            String notes = notesArea.getText();

            String sql = "INSERT INTO ehr (patient_id, diagnosis, prescriptions, allergies, notes) VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, patientId);
                stmt.setString(2, diagnosis);
                stmt.setString(3, prescriptions);
                stmt.setString(4, allergies);
                stmt.setString(5, notes);

                int rows = stmt.executeUpdate();
                statusLabel.setText(rows > 0 ? "Record saved!" : "Failed to save.");
                if (rows > 0) clearForm();
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error saving record.");
        }
    }

    @FXML
    private void loadRecord() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText());

            String sql = "SELECT * FROM ehr WHERE patient_id = ? ORDER BY record_date DESC LIMIT 1";

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, patientId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    diagnosisArea.setText(rs.getString("diagnosis"));
                    prescriptionArea.setText(rs.getString("prescriptions"));
                    allergyArea.setText(rs.getString("allergies"));
                    notesArea.setText(rs.getString("notes"));
                    statusLabel.setText("Latest record loaded.");
                } else {
                    statusLabel.setText("No records found.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error loading record.");
        }
    }

    private void clearForm() {
        diagnosisArea.clear();
        prescriptionArea.clear();
        allergyArea.clear();
        notesArea.clear();
    }
}

