package app.controllers;

import app.util.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentsController {

    @FXML private TextField patientIdField;
    @FXML private TextField departmentField;
    @FXML private TextField doctorField;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private TextArea notesArea;
    @FXML private Label statusLabel;

    @FXML
    private void scheduleAppointment() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText());
            String department = departmentField.getText();
            String doctor = doctorField.getText();
            LocalDate date = datePicker.getValue();
            LocalTime time = LocalTime.parse(timeField.getText());
            String notes = notesArea.getText();

            String sql = "INSERT INTO appointments (patient_id, department, doctor, appointment_date, appointment_time, notes) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, patientId);
                stmt.setString(2, department);
                stmt.setString(3, doctor);
                stmt.setDate(4, java.sql.Date.valueOf(date));
                stmt.setTime(5, java.sql.Time.valueOf(time));
                stmt.setString(6, notes);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    statusLabel.setText("Appointment scheduled!");
                    clearForm();
                } else {
                    statusLabel.setText("Failed to schedule.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error occurred.");
        }
    }

    private void clearForm() {
        patientIdField.clear();
        departmentField.clear();
        doctorField.clear();
        datePicker.setValue(null);
        timeField.clear();
        notesArea.clear();
    }
}
