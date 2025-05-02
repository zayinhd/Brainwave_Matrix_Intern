package app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import app.models.Patient;

public class PatientController {

    @FXML
    private TableView<Patient> patientsTable;

    @FXML
    private TableColumn<Patient, Integer> idColumn;

    @FXML
    private TableColumn<Patient, String> nameColumn;

    @FXML
    private TableColumn<Patient, Integer> ageColumn;

    @FXML
    private TableColumn<Patient, String> genderColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField genderField;

    @FXML
    private Label statusLabel;

    private ObservableList<Patient> patients = FXCollections.observableArrayList();
    private int patientIdCounter = 1;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
        genderColumn.setCellValueFactory(cellData -> cellData.getValue().genderProperty());

        patientsTable.setItems(patients);
    }

    @FXML
    private void handleAddPatient() {
        String name = nameField.getText();
        String ageText = ageField.getText();
        String gender = genderField.getText();

        if (name.isEmpty() || ageText.isEmpty() || gender.isEmpty()) {
            statusLabel.setText("Please fill in all fields.");
            statusLabel.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        try {
            int age = Integer.parseInt(ageText);
            Patient newPatient = new Patient(patientIdCounter++, name, age, gender);
            patients.add(newPatient);
            nameField.clear();
            ageField.clear();
            genderField.clear();
            statusLabel.setText("Patient added.");
            statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        } catch (NumberFormatException e) {
            statusLabel.setText("Age must be a number.");
            statusLabel.setTextFill(javafx.scene.paint.Color.RED);
        }
    }
}
