package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.Transaction;
import org.example.config.SelectedAccount;
import org.example.services.TransactionService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    @FXML
    private AnchorPane transactionContainer;
    @FXML
    private TableView<Transaction> transactionTableViewIn;
    @FXML
    private TableView<Transaction> transactionTableViewOut;
    @FXML
    private TableColumn<Transaction, String> createdIn;
    @FXML
    private TableColumn<Transaction, String> createdOut;
    @FXML
    private TableColumn<Transaction, String> amountIn;
    @FXML
    private TableColumn<Transaction, String> amountOut;
    @FXML
    private TableColumn<Transaction, String> messageIn;
    @FXML
    private TableColumn<Transaction, String> messageOut;
    @FXML
    private TableColumn<Transaction, String> from;
    @FXML
    private TableColumn<String, String> to;
    @FXML
    private ObservableList<Transaction> observableTransactionIn;
    @FXML
    private ObservableList<Transaction> observableTransactionOut;

    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/bank-account.fxml"));
        Parent root = loader.load();
        this.transactionContainer.getScene().setRoot(root);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Transaction> transactionIn = TransactionService.findTransactionsIn();
        List<Transaction> transactionOut = TransactionService.findTransactionsOut();
        observableTransactionIn = FXCollections.observableList(transactionIn);
        observableTransactionOut = FXCollections.observableList(transactionOut);
        transactionTableViewIn.setItems(observableTransactionIn);
        transactionTableViewOut.setItems(observableTransactionOut);
        createdIn.setCellValueFactory(new PropertyValueFactory<>("Created"));
        createdOut.setCellValueFactory(new PropertyValueFactory<>("Created"));
        amountIn.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        amountOut.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        messageIn.setCellValueFactory(new PropertyValueFactory<>("Message"));
        messageOut.setCellValueFactory(new PropertyValueFactory<>("Message"));
        from.setCellValueFactory(new PropertyValueFactory<>("outAccount"));
        to.setCellValueFactory(new PropertyValueFactory<>("inAccount"));

    }
}
