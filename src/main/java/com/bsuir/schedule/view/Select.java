package com.bsuir.schedule.view;

import com.bsuir.schedule.data.extractors.FileExtractor;
import com.bsuir.schedule.data.extractors.WebExtractor;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import scala.collection.immutable.List;

public class Select {

    public Button selectButton;
    public ListView listView;
    public TextField input;
    private static WebExtractor webExtractor = new WebExtractor();
    private static FileExtractor fileExtractor = new FileExtractor();

    public void selectButtonClicked(ActionEvent actionEvent) {
        if (listView.getSelectionModel().getSelectedItem() != null)
            fileExtractor.saveToFile((String)listView.getSelectionModel().getSelectedItem());

        ((Stage)selectButton.getScene().getWindow()).close();
    }

    public void keyTyped(KeyEvent keyEvent) {
        String str = input.getText();
        List<String> teachers = webExtractor.getListTeachersContaining(str).toList();
        List<String> groups = webExtractor.getListGroupsStartAt(str);
        listView.getItems().clear();
        teachers.foreach((String string) -> listView.getItems().add(string));
        groups.foreach((String string) -> listView.getItems().add(string));
    }
}
