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
    @Override
    public void start (Stage stage) throws IOException {
        File file = new File("src/main/resources/com/example/hackathon/people.json");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String s = reader.lines().collect(Collectors.joining(" "));
//        HashMap<String, String> peopleString = GsonBuilder
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main (String[] args) {
        launch();
    }
}