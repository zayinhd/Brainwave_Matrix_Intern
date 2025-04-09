package atm;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ATM extends Application {

    private double accountBalance = 5000.00;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        stage.setTitle("ATM");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label balanceLabel = new Label("Account Balance: ");
        Label titleLabel = new Label("ATM UI Interface ");

        //Account balance field
        TextField balanceField = new TextField();
        balanceField.setEditable(false);
        balanceField.setText(String.valueOf(accountBalance));

        //Functional Buttons 
        Button checkBalanceBtn = new Button("Check Balance");
        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");

        //Add amount field
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");

        //Add components to layout
        grid.add(titleLabel, 3,0);
        grid.add(balanceLabel, 2, 2);
        grid.add(balanceField, 3, 2);
        grid.add(checkBalanceBtn, 4, 5);
        grid.add(depositBtn, 2, 5);
        grid.add(withdrawBtn, 3, 5);
        grid.add(amountField, 3, 4, 1, 1);

        //Event actions
        checkBalanceBtn.setOnAction(e -> {
            balanceField.setText(String.valueOf(accountBalance));
        });

        depositBtn.setOnAction(e -> {
            try {
                double depositAmount = Double.parseDouble(amountField.getText());
                if (depositAmount > 0) {
                    accountBalance += depositAmount;
                    amountField.clear();

                    balanceField.setText(String.valueOf(accountBalance));
                    showAlert("Success", "Deposited: $" + depositAmount);
                } else {
                    showAlert("Error", "Deposit amount must be positive.");

                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "Please enter a valid amount");
            }
        });

        withdrawBtn.setOnAction(e -> {
            try {
                double withdrawAmount = Double.parseDouble(amountField.getText());
                if (withdrawAmount > 0 && withdrawAmount <= accountBalance) {
                    accountBalance -= withdrawAmount;
                    amountField.clear();

                    balanceField.setText(String.valueOf(accountBalance));
                    showAlert("Success", "Withdrawn: $" + withdrawAmount);
                } else {
                    showAlert("Error", "Insufficient funds or invalid amount.");

                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "Please enter a valid amount");
            }
        });

        Scene scene = new Scene(grid, 400, 400);
        stage.setScene(scene);
        stage.show();

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
