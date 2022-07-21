package com.example.hackathon;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class LargePersonController {

    @FXML
    Label nameLabel;
    @FXML
    VBox mainVBox;
    Person person;

    public void initialize () {
        nameLabel.setText(person.name);
        ImageView imageView = new ImageView(person.image.getImage());
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(false);
        mainVBox.getChildren().add(imageView);
    }

}
