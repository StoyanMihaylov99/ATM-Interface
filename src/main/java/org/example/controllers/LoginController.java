package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.User;
import org.example.config.PasswordHashing;
import org.example.services.UserService;

import java.io.IOException;

public class LoginController {
    @FXML
    private AnchorPane logInContainer;
    @FXML
    private TextField logInEmail;
    @FXML
    private PasswordField logInPassword;

    @FXML
    private void handleLogInButton(javafx.event.ActionEvent actionEvent) throws IOException {
        User user = UserService.findUserByEmail(logInEmail.getText());
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Email");
            alert.setHeaderText(null);
            alert.setContentText("This email doesn't exist.");
            alert.showAndWait();
        } else if (PasswordHashing.verifyPassword(logInPassword.getText(), user.getPassword())) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/home-view.fxml"));
            Parent root = loader.load();
            this.logInContainer.getScene().setRoot(root);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong password");
            alert.setHeaderText(null);
            alert.setContentText("Wrong password.");

            alert.showAndWait();
        }
    }

}
