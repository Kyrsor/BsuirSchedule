package com.bsuir.schedule.view;

import com.bsuir.schedule.data.extractors.FileExtractor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu {
    public Button addButton;
    public Button refreshButton;
    public ListView choiceList;
    private FileExtractor fileExtractor = new FileExtractor();

    private void updateListOfGroup() {
        choiceList.getItems().clear();
        fileExtractor.getListOfSaves().foreach((String str) -> choiceList.getItems().add(str));
    }

    @FXML
    void initialize() {
        updateListOfGroup();
    }

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
        updateListOfGroup();
    }

    public void refreshButtonClicked(ActionEvent actionEvent) {
    }

    public void deleteButtonClicked(ActionEvent actionEvent) {
        if (choiceList.getSelectionModel().getSelectedItem() != null)
            fileExtractor.removeFromFile((String)choiceList.getSelectionModel().getSelectedItem());
        updateListOfGroup();
    }
}
