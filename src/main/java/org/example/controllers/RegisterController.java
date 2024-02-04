package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import org.example.config.EmailValidator;
import org.example.services.UserService;
import java.io.IOException;
import java.lang.String;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class RegisterController implements Initializable {
    @FXML
    private TextField firstNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private AnchorPane registerContainer;
    @FXML
    private TextField confirmPassword;



    public void handleRegisterButton(javafx.event.ActionEvent event) throws NoSuchAlgorithmException, IOException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String secondPassword = confirmPassword.getText();


        if (EmailValidator.validateEmail(email)) {
            if (EmailValidator.verifyDuplicateEmail(email)) {
                showAlreadyTakenEmailAlert();
            } else {
                if (password.length() < 3) {
                    showInvalidPassword();
                } else {
                    if(secondPassword.equals(password)){
                   UserService.makeNewUser(firstName, lastName, password, email);
                    succeedRegistration(event);}
                    else {
                        showPasswordMissmatch();
                    }
                }
            }
        } else {
            showInvalidEmailAlert();
        }
    }

    private void showPasswordMissmatch() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Password");
        alert.setHeaderText(null);
        alert.setContentText("Your password miss match, please try again.");

        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAlphabeticTextFieldFormatter(firstNameField);
        setAlphabeticTextFieldFormatter(lastNameField);
        setPasswordTextFieldFormatter(passwordField);
    }

    private void setAlphabeticTextFieldFormatter(TextField textField) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 15) {
                return null;
            }
            if (newText.matches("[a-zA-Z]*")) {
                return change;
            }

            return null; // reject the change
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        textField.setTextFormatter(textFormatter);
    }

    private static void setPasswordTextFieldFormatter(TextField textField) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            // Restrict to a minimum length of 3 characters and a maximum length of 20 characters
            if (newText.length() > 20) {
                return null;
            }

            return change;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        textField.setTextFormatter(textFormatter);
    }

    private void showInvalidEmailAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Email");
        alert.setHeaderText(null);
        alert.setContentText("Please, enter a valid email address.");

        alert.showAndWait();
    }

    private void showInvalidPassword() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Password");
        alert.setHeaderText(null);
        alert.setContentText("Please, enter a valid password between 3 and 20 characters.");
        alert.showAndWait();
    }

    private void showAlreadyTakenEmailAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Email");
        alert.setHeaderText(null);
        alert.setContentText("This email is already in use.");
        alert.showAndWait();
    }

    private void succeedRegistration(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/log-in.fxml"));
        Parent root = loader.load();
        this.registerContainer.getScene().setRoot(root);
    }


    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/welcome.fxml"));
        Parent root = loader.load();
        this.registerContainer.getScene().setRoot(root);
    }
}
