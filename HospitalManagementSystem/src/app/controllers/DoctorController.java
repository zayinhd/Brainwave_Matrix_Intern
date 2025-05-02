package app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;

public class DoctorController {

    @FXML
    private TableView<Doctor> doctorsTable;

    @FXML
    private TableColumn<Doctor, Integer> idColumn;

    @FXML
    private TableColumn<Doctor, String> nameColumn;

    @FXML
    private TableColumn<Doctor, String> specializationColumn;

    private ObservableList<Doctor> doctorsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        specializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));

        // Sample data
        doctorsList.add(new Doctor(1, "Dr. John Larki", "Cardiology"));
        doctorsList.add(new Doctor(2, "Dr. Will Smith", "Neurology"));

        doctorsTable.setItems(doctorsList);
    }

    @FXML
    private void handleAddDoctor() {
        int newId = doctorsList.size() + 1;
        doctorsList.add(new Doctor(newId, "New Doctor " + newId, "General"));
    }

    @FXML
    private void handleDeleteDoctor() {
        Doctor selected = doctorsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            doctorsList.remove(selected);
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

    // Inner class to represent Doctor
    public static class Doctor {
        private final Integer id;
        private final String name;
        private final String specialization;

        public Doctor(Integer id, String name, String specialization) {
            this.id = id;
            this.name = name;
            this.specialization = specialization;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getSpecialization() {
            return specialization;
        }
    }
}
