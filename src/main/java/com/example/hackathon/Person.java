package com.example.hackathon;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Person {
    @FXML
    boolean isFlagged = false;
    boolean isInPrevious = false;

    boolean flaggedLastRound  = false;
    String name;
    ImageView image;
}
