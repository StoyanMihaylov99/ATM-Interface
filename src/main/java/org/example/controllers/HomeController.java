package org.example.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.Account;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.User;
import org.example.config.LoggedUser;
import org.example.config.SelectedAccount;
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
    private TableColumn<Account, String> ibanColumn;
    @FXML
    private TableColumn<Account, String> balanceColumn;
    private User currentUser;
    private List<Account> userBankAccounts;
    @FXML
    private ObservableList<Account> observableAccounts;

    private List<Account> setUserInfo() {
        currentUser = UserService.findUserByEmail(LoggedUser.getEmail());
        userBankAccounts = currentUser.getBankAccounts();
        return userBankAccounts;
    }

    private User getUser() {
        return currentUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Account> currentBankAccounts = setUserInfo();
        observableAccounts = FXCollections.observableArrayList(currentBankAccounts);
        accountsTableView.setItems(observableAccounts);
        LoggedUser.getInstance(currentUser.getEmail());

        // Set up the cell value factories for each column
        ibanColumn.setCellValueFactory(new PropertyValueFactory<>("Iban"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        accountsTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 2){
                    Account selectedRecord = accountsTableView.getSelectionModel().getSelectedItem();
                    if(selectedRecord != null){
                        SelectedAccount selectedAccount = new SelectedAccount(selectedRecord.getIban());
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/fxml/bank-account.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                            homeContainer.getScene().setRoot(root);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }
    public void addNewAccount(ActionEvent actionEvent) throws IOException {
        Account newAccount = AccountService.makeNewAccount(BigDecimal.valueOf(0.00), getUser());
        observableAccounts.add(newAccount);
        accountsTableView.refresh();
    }

}
