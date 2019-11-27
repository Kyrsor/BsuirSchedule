package com.bsuir.schedule.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu {
    public Button addButton;
    public Button refreshButton;


    public void addButtonClicked(ActionEvent actionEvent) {
        final Stage searchWindow = new Stage();
        searchWindow.initModality(Modality.APPLICATION_MODAL);
        Parent root = null;
        try {
            root = FXMLLoader.load(Select.class.getResource("Select.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        searchWindow.setScene(scene);
        searchWindow.show();
    }

    public void refreshButtonClicked(ActionEvent actionEvent) {
    }
}
