package org.example;
import javafx.application.*;
import javafx.fxml.FXMLLoader;

import javafx.stage.*;
import javafx.scene.*;
import org.example.config.Connector;

public class Main extends Application {
    public static void main(String[] args) {
        Connector.creating();
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/welcome.fxml"));
        Parent content = loader.load();
        stage.setTitle("ATM Interface");
        stage.setScene(new Scene(content,807,483));
        stage.show();
    }

}