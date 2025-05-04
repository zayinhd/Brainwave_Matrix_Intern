package app.controllers;

import app.util.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BillingController {

    @FXML private TextField patientIdField;
    @FXML private TextField totalAmountField;
    @FXML private TextField paidAmountField;
    @FXML private TextField outstandingBalanceField;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        statusComboBox.getItems().addAll("Paid", "Unpaid");
    }

    @FXML
    private void generateInvoice() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText());
            double totalAmount = Double.parseDouble(totalAmountField.getText());
            double paidAmount = Double.parseDouble(paidAmountField.getText());
            double outstandingBalance = totalAmount - paidAmount;
            String status = statusComboBox.getValue();

            String sql = "INSERT INTO billing (patient_id, total_amount, paid_amount, outstanding_balance, status) VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, patientId);
                stmt.setDouble(2, totalAmount);
                stmt.setDouble(3, paidAmount);
                stmt.setDouble(4, outstandingBalance);
                stmt.setString(5, status);

                int rows = stmt.executeUpdate();
                statusLabel.setText(rows > 0 ? "Invoice generated!" : "Failed to generate invoice.");
                if (rows > 0) clearForm();
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error generating invoice.");
        }
    }

    @FXML
    private void updateInvoice() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText());
            double paidAmount = Double.parseDouble(paidAmountField.getText());
            double totalAmount = Double.parseDouble(totalAmountField.getText());
            double outstandingBalance = totalAmount - paidAmount;
            String status = statusComboBox.getValue();

            String sql = "UPDATE billing SET paid_amount = ?, outstanding_balance = ?, status = ? WHERE patient_id = ?";

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setDouble(1, paidAmount);
                stmt.setDouble(2, outstandingBalance);
                stmt.setString(3, status);
                stmt.setInt(4, patientId);

                int rows = stmt.executeUpdate();
                statusLabel.setText(rows > 0 ? "Invoice updated!" : "Failed to update invoice.");
                if (rows > 0) clearForm();
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error updating invoice.");
        }
    }

    @FXML
    private void loadInvoice() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText());

            String sql = "SELECT * FROM billing WHERE patient_id = ?";

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, patientId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    totalAmountField.setText(String.valueOf(rs.getDouble("total_amount")));
                    paidAmountField.setText(String.valueOf(rs.getDouble("paid_amount")));
                    outstandingBalanceField.setText(String.valueOf(rs.getDouble("outstanding_balance")));
                    statusComboBox.setValue(rs.getString("status"));
                    statusLabel.setText("Invoice loaded.");
                } else {
                    statusLabel.setText("No invoice found.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error loading invoice.");
        }
    }

    private void clearForm() {
        patientIdField.clear();
        totalAmountField.clear();
        paidAmountField.clear();
        outstandingBalanceField.clear();
        statusComboBox.getSelectionModel().clearSelection();
    }
}

