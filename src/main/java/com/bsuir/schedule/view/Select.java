package com.bsuir.schedule.view;

import com.bsuir.schedule.data.extractors.FileExtractor;
import com.bsuir.schedule.data.extractors.WebExtractor;
import com.bsuir.schedule.data.models.Schedule;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scala.collection.immutable.List;

public class Select {

    public Button selectButton;
    public ListView listView;
    public TextField input;
    private static WebExtractor webExtractor = new WebExtractor();
    private static FileExtractor fileExtractor = new FileExtractor();

    public void selectButtonClicked(ActionEvent actionEvent) {
        String selected = (String)listView.getSelectionModel().getSelectedItem();
        if (selected != null)
        {
            fileExtractor.saveToFile(selected);
            fileExtractor.saveScheduleToFile(selected, webExtractor.getRawSchedule(selected));
        }
        Stage stage = (Stage)selectButton.getScene().getWindow();
        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
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
