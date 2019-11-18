package com.bsuir.schedule.view;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class MainMenu {
    public Button addButton;
    public Button refreshButton;


    public void addButtonClicked(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType().toString());
    }

    public void refreshButtonClicked(ActionEvent actionEvent) {
    }
}
