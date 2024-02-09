package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.Account;
import org.example.User;
import org.example.config.Connector;
import org.example.services.AccountService;
import org.example.services.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AdminController {
    @FXML
    public AnchorPane adminContainer;
    @FXML
    public TextField emailField;


    public void deleteUser(ActionEvent actionEvent) {
        List<Account> accounts = findAccounts(emailField.getText());
        if(accounts == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("User error");
            alert.setHeaderText(null);
            alert.setContentText("There is no user with this email");
            alert.showAndWait();
        } else {

            boolean verify = true;

            for (Account account : accounts) {
                if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                    verify = false;
                    break;
                }
            }

            if (!verify) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Balance Error");
                alert.setHeaderText(null);
                alert.setContentText("The balance of the user's accounts should exactly 0.00 to proceed a delete operation");
                alert.showAndWait();
            } else {
                UserService.deleteUserByEmail(emailField.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText(null);
                alert.setContentText("You successfully delete the user whit email " + emailField.getText() + " !");
                alert.showAndWait();

            }
        }


    }

    public void blockUser(ActionEvent actionEvent) {
        Connector.transactionBegin();
        User user = UserService.findUserByEmail(emailField.getText());
        user.setBlocked(true);
        Connector.commitTransaction();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(null);
        alert.setContentText("You successfully unblock User " + emailField.getText() + " !");
        alert.showAndWait();

    }


    public void unBlockUser(ActionEvent actionEvent) {
        Connector.transactionBegin();
        User user = UserService.findUserByEmail(emailField.getText());
        user.setBlocked(false);
        Connector.commitTransaction();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(null);
        alert.setContentText("You successfully unblock User " + emailField.getText() + " !");
        alert.showAndWait();
    }

    public List<Account> findAccounts(String email) {
        User user = UserService.findUserByEmail(email);
        if(user == null) return null;
        return user.getBankAccounts();

    }
}
