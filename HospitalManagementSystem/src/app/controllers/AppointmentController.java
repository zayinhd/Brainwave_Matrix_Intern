package app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;

public class AppointmentController {

    @FXML
    private ComboBox<String> patientComboBox;

    @FXML
    private ComboBox<String> doctorComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, String> patientCol;

    @FXML
    private TableColumn<Appointment, String> doctorCol;

    @FXML
    private TableColumn<Appointment, LocalDate> dateCol;

    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Populate ComboBoxes with mock data
        patientComboBox.setItems(FXCollections.observableArrayList("John Doe", "Jane Smith", "Alice Johnson"));
        doctorComboBox.setItems(FXCollections.observableArrayList("Dr. Brown", "Dr. Lee", "Dr. Patel"));

        // Set up table columns
        patientCol.setCellValueFactory(new PropertyValueFactory<>("patient"));
        doctorCol.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Bind data to the table
        appointmentTable.setItems(appointments);
    }

    @FXML
    private void handleAddAppointment(ActionEvent event) {
        String patient = patientComboBox.getValue();
        String doctor = doctorComboBox.getValue();
        LocalDate date = datePicker.getValue();

        if (patient == null || doctor == null || date == null) {
            showAlert("Error", "Please fill all fields before adding.");
            return;
        }

        Appointment newAppointment = new Appointment(patient, doctor, date);
        appointments.add(newAppointment);

        // Clear fields
        patientComboBox.setValue(null);
        doctorComboBox.setValue(null);
        datePicker.setValue(null);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/Dashboard.fxml"));
            Scene dashboardScene = new Scene(loader.load());
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(dashboardScene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Inner class to represent appointments
    public static class Appointment {
        private final String patient;
        private final String doctor;
        private final LocalDate date;

        public Appointment(String patient, String doctor, LocalDate date) {
            this.patient = patient;
            this.doctor = doctor;
            this.date = date;
        }

        public String getPatient() {
            return patient;
        }

        public String getDoctor() {
            return doctor;
        }

        public LocalDate getDate() {
            return date;
        }
    }
}
