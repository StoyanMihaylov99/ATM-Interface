package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class WelcomeController {

    @FXML
    private AnchorPane mainContainer;

    @FXML
    public void register(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/register.fxml"));
        Parent root = loader.load();
        mainContainer.getScene().setRoot(root);

    }
    @FXML
    public void logIn(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/log-in.fxml"));
        Parent root = loader.load();
        mainContainer.getScene().setRoot(root);
    }
}
