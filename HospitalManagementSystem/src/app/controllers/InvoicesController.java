package app.controllers;

import app.util.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InvoicesController {

    @FXML private TextField appointmentIdField;
    @FXML private TextField amountField;
    @FXML private ComboBox<String> paymentStatusComboBox;
    @FXML private DatePicker invoiceDatePicker;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        paymentStatusComboBox.getItems().addAll("Paid", "Pending");
    }

    @FXML
    private void generateInvoice() {
        try {
            int appointmentId = Integer.parseInt(appointmentIdField.getText());
            double amount = Double.parseDouble(amountField.getText());
            String paymentStatus = paymentStatusComboBox.getValue();
            String invoiceDate = invoiceDatePicker.getValue().toString();

            String sql = "INSERT INTO invoices (appointment_id, amount, payment_status, invoice_date) VALUES (?, ?, ?, ?)";

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, appointmentId);
                stmt.setDouble(2, amount);
                stmt.setString(3, paymentStatus);
                stmt.setString(4, invoiceDate);

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
    private void loadInvoice() {
        try {
            int appointmentId = Integer.parseInt(appointmentIdField.getText());

            String sql = "SELECT * FROM invoices WHERE appointment_id = ? ORDER BY invoice_date DESC LIMIT 1";

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, appointmentId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    amountField.setText(String.valueOf(rs.getDouble("amount")));
                    paymentStatusComboBox.setValue(rs.getString("payment_status"));
                    invoiceDatePicker.setValue(rs.getDate("invoice_date").toLocalDate());
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
        amountField.clear();
        paymentStatusComboBox.getSelectionModel().clearSelection();
        invoiceDatePicker.setValue(null);
    }
}

