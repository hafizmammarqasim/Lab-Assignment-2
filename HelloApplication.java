package com.example.guiproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class HelloApplication extends Application {

    ArrayList<Person> students = new ArrayList<>();
    Image selectedImage;

    @Override
    public void start(Stage stage) {
        // Scene 1: Start Registration Screen
        VBox startScreen = new VBox(20);
        startScreen.setAlignment(Pos.CENTER);
        Label welcomeLabel = new Label("Welcome to the Student Enrollment System");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: green;");
        Button startButton = new Button("Start Registration");
        startButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        startScreen.getChildren().addAll(welcomeLabel, startButton);
        Scene startScene = new Scene(startScreen, 800, 500);

        // Scene 2: Registration Form
        Label bannerLabel = new Label("Student Enrollment Form");
        bannerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: blue;");

        // Form fields
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label fatherNameLabel = new Label("Father Name:");
        TextField fatherNameField = new TextField();

        Label cnicLabel = new Label("CNIC:");
        TextField cnicField = new TextField();

        Label dobLabel = new Label("Date of Birth:");
        DatePicker datePicker = new DatePicker();

        Label genderLabel = new Label("Gender:");
        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton maleButton = new RadioButton("Male");
        RadioButton femaleButton = new RadioButton("Female");
        RadioButton otherButton = new RadioButton("Other");
        maleButton.setToggleGroup(genderGroup);
        femaleButton.setToggleGroup(genderGroup);
        otherButton.setToggleGroup(genderGroup);
        HBox genderBox = new HBox(10, maleButton, femaleButton, otherButton);

        Label cityLabel = new Label("City:");
        ComboBox<String> cityComboBox = new ComboBox<>();
        cityComboBox.getItems().addAll("Karachi", "Lahore", "Islamabad", "Rawalpindi", "Quetta", "Peshawar");

        // Image Viewer and File Picker
        ImageView imageView = new ImageView();
        imageView.setFitHeight(250); // Increased image size
        imageView.setFitWidth(250);
        imageView.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

        Button imageButton = new Button("Select Image");
        imageButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", ".jpg", ".png", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                selectedImage = new Image(selectedFile.toURI().toString());
                imageView.setImage(selectedImage);
                imageButton.setVisible(false); // Hide the button once the image is selected
            }
        });

        VBox imageSection = new VBox(10, imageView, imageButton);
        imageSection.setAlignment(Pos.CENTER);

        // Save Button
        Button saveButton = new Button("Save and Finish");

        // Layout: Left Side (Form) and Right Side (Image Section)
        GridPane formLayout = new GridPane();
        formLayout.setPadding(new Insets(10, 10, 10, 15));
        formLayout.setVgap(10);
        formLayout.setHgap(10);

        // Add form elements to the grid on the left
        formLayout.add(bannerLabel, 0, 0, 2, 1);

        formLayout.add(nameLabel, 0, 1);
        formLayout.add(nameField, 1, 1);
        formLayout.add(imageButton, 2, 1); // Place the image button next to the name field

        formLayout.add(fatherNameLabel, 0, 2);
        formLayout.add(fatherNameField, 1, 2);

        formLayout.add(cnicLabel, 0, 3);
        formLayout.add(cnicField, 1, 3);

        formLayout.add(dobLabel, 0, 4);
        formLayout.add(datePicker, 1, 4);

        formLayout.add(genderLabel, 0, 5);
        formLayout.add(genderBox, 1, 5);

        formLayout.add(cityLabel, 0, 6);
        formLayout.add(cityComboBox, 1, 6);

        // Add image section to the far right
        formLayout.add(imageSection, 3, 1, 1, 6);

        // Add save button
        formLayout.add(saveButton, 1, 7);

        // Save button logic
        saveButton.setOnAction(event -> {
            String name = nameField.getText();
            String fatherName = fatherNameField.getText();
            String cnic = cnicField.getText();
            LocalDate dob = datePicker.getValue();
            String gender = maleButton.isSelected() ? "Male" : femaleButton.isSelected() ? "Female" : "Other";
            String city = cityComboBox.getValue();

            // Create a new Person object with the form details
            Person newStudent = new Person(name, fatherName, cnic, dob, gender, city, selectedImage);
            students.add(newStudent); // Add the new student to the ArrayList

            // Display the student's details in the console
            System.out.println("Student saved:");
            System.out.println("Name: " + newStudent.getName());
            System.out.println("Father Name: " + newStudent.getFatherName());
            System.out.println("CNIC: " + newStudent.getCnic());
            System.out.println("Date of Birth: " + newStudent.getDob());
            System.out.println("Gender: " + newStudent.getGender());
            System.out.println("City: " + newStudent.getCity());
            System.out.println("Image: " + (newStudent.getImage() != null ? "Image Selected" : "No Image"));
        });

        // Create registration form scene
        Scene formScene = new Scene(formLayout, 900, 500);

        // Button to switch from start screen to registration form
        startButton.setOnAction(event -> stage.setScene(formScene));

        // Set initial scene and show
        stage.setScene(startScene);
        stage.setTitle("Student Enrollment System");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Person class to hold the student's details
    public static class Person {
        private String name;
        private String fatherName;
        private String cnic;
        private LocalDate dob;
        private String gender;
        private String city;
        private Image image;

        public Person(String name, String fatherName, String cnic, LocalDate dob, String gender, String city, Image image) {
            this.name = name;
            this.fatherName = fatherName;
            this.cnic = cnic;
            this.dob = dob;
            this.gender = gender;
            this.city = city;
            this.image = image;
        }

        // Getters for each property
        public String getName() {
            return name;
        }

        public String getFatherName() {
            return fatherName;
        }

        public String getCnic() {
            return cnic;
        }

        public LocalDate getDob() {
            return dob;
        }

        public String getGender() {
            return gender;
        }

        public String getCity() {
            return city;
        }

        public Image getImage() {
            return image;
        }
    }
}