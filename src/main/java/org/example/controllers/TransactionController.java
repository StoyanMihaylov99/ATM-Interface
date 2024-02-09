package org.example.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.Account;
import org.example.config.AmountValidator;
import org.example.config.Connector;
import org.example.config.SelectedAccount;
import org.example.services.AccountService;
import org.example.services.TransactionService;
import java.io.IOException;
import java.math.BigDecimal;

public class TransactionController{

    @FXML
    private AnchorPane transactionContainer;
    @FXML
    private TextField depositAmount;
    @FXML
    private TextField withdrawAmount;
    @FXML
    public TextField ibanInput;
    @FXML
    public TextField amountInput;
    @FXML
    public TextField messageInput;


    public void handleWithdrawButton(ActionEvent actionEvent) throws IOException {
        if (AmountValidator.validateAmount(withdrawAmount.getText())) {
            double amount = Double.parseDouble(withdrawAmount.getText());


            if (AccountService.withdrawByIban(BigDecimal.valueOf(amount), SelectedAccount.getIban())) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/successfully-withdraw.fxml"));
                Parent root = loader.load();
                this.transactionContainer.getScene().setRoot(root);
                Connector.closeConnection();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Amount");
                alert.setHeaderText(null);
                alert.setContentText("Not enough funds for this amount.");
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Amount");
            alert.setHeaderText(null);
            alert.setContentText("The amount should contains only digits.");
            alert.showAndWait();
        }

    }


    public void handleDepositButton(ActionEvent actionEvent) throws IOException {
        if (AmountValidator.validateAmount(depositAmount.getText())) {
            double amount = Double.parseDouble(depositAmount.getText());
            AccountService.makeADepositByIban(BigDecimal.valueOf(amount), SelectedAccount.getIban());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/successfully-deposit.fxml"));
            Parent root = loader.load();
            this.transactionContainer.getScene().setRoot(root);
            Connector.closeConnection();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Amount");
            alert.setHeaderText(null);
            alert.setContentText("The amount should contains only digits.");
            alert.showAndWait();
        }
    }


    public void handleSubmissionTransaction(ActionEvent actionEvent) throws IOException {
        Account outAccount = AccountService.findByIban(SelectedAccount.getIban());
        Account inAccount = AccountService.findByIban(ibanInput.getText());
        if (inAccount == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Iban error");
            alert.setHeaderText(null);
            alert.setContentText("There is no bank account with this Iban.");
            alert.showAndWait();
        } else if (inAccount.getHolder().isBlocked()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blocked User");
            alert.setHeaderText(null);
            alert.setContentText("The user you are trying to send funds has been blocked.");
            alert.showAndWait();
        } else if (AmountValidator.validateAmount(amountInput.getText())) {
            double amount = Double.parseDouble(amountInput.getText());
            if (TransactionService.verifyTransaction(outAccount, inAccount, BigDecimal.valueOf(amount))) {
                AccountService.withdrawByIban(BigDecimal.valueOf(amount), outAccount.getIban());
                AccountService.makeADepositByIban(BigDecimal.valueOf(amount), inAccount.getIban());
                TransactionService.persistATransaction(outAccount,inAccount,BigDecimal.valueOf(amount),messageInput.getText());
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/successfully-transaction.fxml"));
                Parent root = loader.load();
                this.transactionContainer.getScene().setRoot(root);
                Connector.closeConnection();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Money error");
                alert.setHeaderText(null);
                alert.setContentText("You don't have enough funds.");
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Money error");
            alert.setHeaderText(null);
            alert.setContentText("The amount should contains only digits.");
            alert.showAndWait();
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/bank-account.fxml"));
        Parent root = loader.load();
        this.transactionContainer.getScene().setRoot(root);
    }


}
