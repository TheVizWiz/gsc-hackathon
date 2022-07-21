package com.example.hackathon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

public class HelloApplication extends Application {

    public static Stage stage;
    @Override
    public void start (Stage stage) throws IOException {

//        HashMap<String, String> peopleString = GsonBuilder
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        stage.setTitle("Pull the lever!");
        stage.setScene(scene);
        stage.show();
        HelloApplication.stage = stage;
    }

    public static void main (String[] args) {
        launch();
    }
}