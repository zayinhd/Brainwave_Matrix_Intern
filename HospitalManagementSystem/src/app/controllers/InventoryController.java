package app.controllers;

import app.util.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InventoryController {

    @FXML private TextField itemNameField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private TextField supplierField;
    @FXML private DatePicker restockDatePicker;
    @FXML private Label statusLabel;

    @FXML
    private void addItem() {
        try {
            String itemName = itemNameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());
            String supplier = supplierField.getText();
            String restockDate = restockDatePicker.getValue().toString();

            String sql = "INSERT INTO inventory (item_name, quantity, price, supplier, restock_date) VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, itemName);
                stmt.setInt(2, quantity);
                stmt.setDouble(3, price);
                stmt.setString(4, supplier);
                stmt.setString(5, restockDate);

                int rows = stmt.executeUpdate();
                statusLabel.setText(rows > 0 ? "Item added to inventory!" : "Failed to add item.");
                if (rows > 0) clearForm();
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error adding item.");
        }
    }

    @FXML
    private void updateInventory() {
        try {
            String itemName = itemNameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());

            String sql = "UPDATE inventory SET quantity = quantity + ? WHERE item_name = ?";

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, quantity);
                stmt.setString(2, itemName);

                int rows = stmt.executeUpdate();
                statusLabel.setText(rows > 0 ? "Inventory updated!" : "Failed to update inventory.");
                if (rows > 0) clearForm();
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error updating inventory.");
        }
    }

    @FXML
    private void loadItem() {
        try {
            String itemName = itemNameField.getText();

            String sql = "SELECT * FROM inventory WHERE item_name = ?";

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, itemName);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    quantityField.setText(String.valueOf(rs.getInt("quantity")));
                    priceField.setText(String.valueOf(rs.getDouble("price")));
                    supplierField.setText(rs.getString("supplier"));
                    restockDatePicker.setValue(rs.getDate("restock_date").toLocalDate());
                    statusLabel.setText("Item loaded.");
                } else {
                    statusLabel.setText("No item found.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error loading item.");
        }
    }

    private void clearForm() {
        itemNameField.clear();
        quantityField.clear();
        priceField.clear();
        supplierField.clear();
        restockDatePicker.setValue(null);
    }
}
