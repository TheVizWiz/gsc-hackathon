package com.example.hackathon;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class HelloController {
    public HBox images;
    public HBox peopleBox;
    public Button clickbutton;
    @FXML
    private Label welcomeText;

    ArrayList<Person> people = new ArrayList<>();
    HashMap<Person, Node> map = new HashMap<>();
    HashMap<Person, Node> largeMap = new HashMap<>();
    HashMap<Person, PersonController> controllerHashMap = new HashMap<>();


    ArrayList<String> pickedList = new ArrayList<>();


    public void initialize () {
        try {
            File file = new File("/Users/viiyer/Documents/Internship/hackathon/src/main/resources/com/example/hackathon/people.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                Person person = new Person();
                person.name = reader.readLine();
                File f = new File (reader.readLine());
                if (!f.exists()) {
                    person.image = new ImageView( new Image(new FileInputStream("/Users/viiyer" +
                            "/Documents" +
                            "/Internship" +
                            "/hackathon" +
                            "/src/main/resources/com/example/hackathon/default.png")));
                } else {
                    person.image =
                            new ImageView(new Image(new FileInputStream(f.getAbsolutePath())));

                }
                ImageView view = person.image;
                view.setPreserveRatio(false);
                view.setFitWidth(200);
                view.setFitHeight(200);
                person.isFlagged = false;
                person.isInPrevious = false;
                people.add(person);
                PersonController controller = new PersonController();
                controller.person = person;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Person.fxml"));
                loader.setController(controller);
                try {
                    Node node = loader.load();
                    map.put(person, node);
                    controllerHashMap.put(person,controller);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                LargePersonController largePersonController = new LargePersonController();
                largePersonController.person = person;
                loader = new FXMLLoader(getClass().getResource("LargePerson.fxml"));
                loader.setController(largePersonController);
                try {
                    Node node = loader.load();
                    largeMap.put(person, node);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.values().forEach(node -> {
            peopleBox.getChildren().add(node);
        });



    }


    @FXML
    protected void onHelloButtonClick () {
        Random rand = new Random();
        int randomInt;
        ArrayList<Person> peopleInThisRound = new ArrayList<>(); // Array with people for this round

        // Picking people who were flagged last round
        int counter = 0;
        for (int i = 0; i < people.size(); i++) {
            if (counter == 3) {
                break;
            } else if (people.get(i).flaggedLastRound) {
                peopleInThisRound.add(people.get(i));
                people.get(i).flaggedLastRound = false;
                people.get(i).isInPrevious = true;
                counter = counter + 1;
            }
        }

        // Picking remaining random people (if any needed) who are not flagged and are not in
        // previous
        if (counter < 3) {
            for (int i = 0; i < 3 - counter; i++) {
                randomInt = rand.nextInt(people.size());
                Person curr = people.get(randomInt);
                if (curr.isFlagged || curr.isInPrevious) {
                    while (curr.isFlagged || curr.isInPrevious) {
                        randomInt = rand.nextInt(people.size());
                        curr = people.get(randomInt);
                    }
                }
                curr.isInPrevious = true;
                peopleInThisRound.add(curr);
            }
        }

        // Setting other peoples inPrevious and flagged to false where needed
        for (int i = 0; i < people.size(); i++) {
            Person checking = people.get(i);
            if (peopleInThisRound.contains(checking)) {
                continue;
            } else {
                checking.isInPrevious = false;
                if (checking.isFlagged) {
                    checking.isFlagged = false;
                    checking.flaggedLastRound = true;
                } else {
                    checking.flaggedLastRound = false;
                }
            }
        }

        showPeople(peopleInThisRound);
    }

    public void showPeople(ArrayList<Person> people) {
        images.getChildren().clear();
        System.out.print("People chosen this round: ");
        String s = "- [";
        for (Person p : people) {
            System.out.print(p.name + ", ");

            images.getChildren().add(largeMap.get(p));
            s += p.name + ", ";
        }

        s = s.substring(0, s.length() - 2);
        s += "]";
        pickedList.add(s);
        System.out.println("");

        controllerHashMap.values().forEach(PersonController::setFields);
    }

    public void exportToYAML (ActionEvent actionEvent) {
        try {
            FileChooser chooser = new FileChooser();
//            chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(".yaml"));
            File file = chooser.showSaveDialog(HelloApplication.stage);
            String s = pickedList.stream().collect(Collectors.joining("\n"));
            s = "---\n" + s  + "\n...";
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(s);
            writer.flush();
            writer.close();
        } catch (Exception e) {

        }

    }
}