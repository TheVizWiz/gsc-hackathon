package com.example.hackathon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class PersonController {

    Person person;
    @FXML
    Label isFlagged;
    @FXML
    Label isPickedLastRound;
    @FXML
    Label nameLabel;
    @FXML
    Button flagSwitcher;
    @FXML
    VBox mainVBox;

    public void initialize() {
        flagSwitcher.setOnAction(this::switchFlaggedStatus);
        ImageView imageView = new ImageView(person.image.getImage());
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(false);
        mainVBox.getChildren().add(imageView);
        nameLabel.setText(person.name);
        setFields();
    }

    public void switchFlaggedStatus (ActionEvent actionEvent) {
        person.isFlagged = !person.isFlagged;
        isFlagged.setText(person.isFlagged + "");
    }

    public void setFields() {
        isFlagged.setText(person.isFlagged + "");
        isPickedLastRound.setText(person.isInPrevious + "");
    }
}
