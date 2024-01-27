package org.example.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.Account;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.User;
import org.example.config.LoggedUser;
import org.example.services.AccountService;
import org.example.services.UserService;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private AnchorPane homeContainer;
    @FXML
    private TableView<Account> accountsTableView;
    @FXML
    private TableColumn<Account,String> ibanColumn;
    @FXML
    private TableColumn<Account,String> balanceColumn;


    private User currentUser;

    private void setUser(){
        currentUser = new User();
        currentUser  = UserService.findUserByEmail(LoggedUser.getEmail());
    }

    private User getUser(){
        return currentUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get the list of BankAccounts from the User
        setUser();
        List<Account> userBankAccounts = currentUser.getBankAccounts();
        // Convert the List to an ObservableList for the TableView
        ObservableList<Account> observableAccounts = FXCollections.observableArrayList(userBankAccounts);

        // Set the items in the TableView
        accountsTableView.setItems(observableAccounts);

        // Set up the cell value factories for each column
        ibanColumn.setCellValueFactory(new PropertyValueFactory<>("Iban"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

    }

    public void addNewAccount(ActionEvent actionEvent) throws IOException {
        AccountService.makeNewAccount(BigDecimal.valueOf(0),getUser());
        accountsTableView.refresh();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/newBankAccountAnnouncement.fxml"));
        Parent root = loader.load();
        this.homeContainer.getScene().setRoot(root);
    }

    public void goBackButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/home-view.fxml"));
        Parent root = loader.load();
        this.homeContainer.getScene().setRoot(root);
    }

    public void moreInfoButton(ActionEvent actionEvent) {
    }
}
