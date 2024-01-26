package org.example.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.example.Account;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private AnchorPane homeContainer;
    @FXML
    private TableView<Account> accountsTableView;
    @FXML
    private TableColumn<Account,String> iban;
    @FXML
    private TableColumn<Account,String> balance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get the list of BankAccounts from the User
        List<Account> userBankAccounts =

        // Convert the List to an ObservableList for the TableView
        ObservableList<Account> observableAccounts = FXCollections.observableArrayList(userBankAccounts);

        // Set the items in the TableView
        accountsTableView.setItems(observableAccounts);

        // Set up the cell value factories for each column
        accountNumberColumn.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        // Add more cell value factories as needed
    }
}
