package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.Account;
import org.example.Transaction;
import org.example.config.SelectedAccount;
import org.example.services.AccountService;
import org.example.services.TransactionService;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountController implements Initializable {
    @FXML
    public AnchorPane accountContainer;
    @FXML
    public TextField ibanInput;
    @FXML
    public TextField amountInput;
    @FXML
    public TextField messageInput;

    @FXML
    private Label ibanInfo;
    @FXML
    private Label balanceInfo;

    public void setIbanInfo(){
        this.ibanInfo.setText(SelectedAccount.getIban());
    }

    public void setBalanceInfo(){
        this.balanceInfo.setText(SelectedAccount.getBalance().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBalanceInfo();
        setIbanInfo();
    }

    public void deleteAccount(ActionEvent actionEvent) throws IOException {
        if(SelectedAccount.getBalance().compareTo(BigDecimal.ZERO) == 0){
            if(AccountService.deleteAccountById(SelectedAccount.getId(),SelectedAccount.getIban())){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/deleted-account.fxml"));
                Parent root = loader.load();
                this.accountContainer.getScene().setRoot(root);
            }
        }
    }


    public void handleSubmissionTransaction(ActionEvent actionEvent) {
        Account outAccount = AccountService.findByIban(SelectedAccount.getIban());
        Account inAccount = AccountService.findByIban(ibanInput.getText());
        if(inAccount.isBlocked()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blocked Account");
            alert.setHeaderText(null);
            alert.setContentText("The account you are trying to send funds has been blocked.");
            alert.showAndWait();
        }
        double amount = Double.parseDouble(amountInput.getText());
        Transaction transaction = TransactionService.MakeATransaction(outAccount,inAccount,BigDecimal.valueOf(amount));
        if(transaction.isApproved()){
            //TODO: successfully submition;

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Money error");
            alert.setHeaderText(null);
            alert.setContentText("You don't have enough funds.");
            alert.showAndWait();
        }
    }
}
