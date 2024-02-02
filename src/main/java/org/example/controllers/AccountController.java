package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.example.config.SelectedAccount;
import org.example.services.AccountService;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountController implements Initializable {
    @FXML
    public AnchorPane accountContainer;
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
}
