package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Random;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PatientPortal {

    private final HashSet<String> existingIDs = new HashSet<>();

    public void display(Stage stage) {
        Stage authStage = new Stage();
        authStage.setTitle("Patient Portal - Login or Sign Up");

        // Login and Sign Up Buttons on Main Page
        Button btnLogInPage = new Button("Log In");
        btnLogInPage.setOnAction(e -> showLogInPage(authStage));

        Button btnSignUpPage = new Button("Sign Up");
        btnSignUpPage.setOnAction(e -> showSignUpPage(authStage));

        VBox choiceBox = new VBox(20, btnLogInPage, btnSignUpPage);
        choiceBox.setAlignment(Pos.CENTER);
        choiceBox.setPadding(new Insets(10));

        Scene scene = new Scene(choiceBox, 300, 200);
        authStage.setScene(scene);
        authStage.show();
    }

    private void showLogInPage(Stage stage) {
        Label lblPatientID = new Label("Patient ID:");
        TextField txtPatientID = new TextField();
        Button btnLogIn = new Button("Log In");
        btnLogIn.setOnAction(e -> handleLogIn(txtPatientID, stage));

        VBox logInBox = new VBox(10, lblPatientID, txtPatientID, btnLogIn);
        logInBox.setAlignment(Pos.CENTER);
        Scene logInScene = new Scene(logInBox, 300, 200);
        stage.setScene(logInScene);
    }

    private void showSignUpPage(Stage stage) {
        TextField txtFirstName = new TextField();
        txtFirstName.setPromptText("First Name");
        TextField txtLastName = new TextField();
        txtLastName.setPromptText("Last Name");
        TextField txtPhoneNumber = new TextField();
        txtPhoneNumber.setPromptText("Phone Number");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        TextField txtEmergencyContact = new TextField();
        txtEmergencyContact.setPromptText("Emergency Contact");
        Button btnSignUp = new Button("Sign Up");

        btnSignUp.setOnAction(e -> handleSignUp(txtFirstName, txtLastName, txtPhoneNumber, txtEmail, txtEmergencyContact, stage));

        VBox signUpBox = new VBox(10, txtFirstName, txtLastName, txtPhoneNumber, txtEmail, txtEmergencyContact, btnSignUp);
        signUpBox.setAlignment(Pos.CENTER);
        Scene signUpScene = new Scene(signUpBox, 300, 300);
        stage.setScene(signUpScene);
    }

    private void handleSignUp(TextField firstName, TextField lastName, TextField phoneNumber, TextField email, TextField emergencyContact, Stage stage) {
        if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || phoneNumber.getText().isEmpty() || email.getText().isEmpty() || emergencyContact.getText().isEmpty()) {
            showAlert("Sign Up Error", "All fields are required. Please fill in all fields.");
            return;
        }

        String patientID = generateUniquePatientID();
        try {
            savePatientInfo(patientID, firstName.getText(), lastName.getText(), phoneNumber.getText(), email.getText(), emergencyContact.getText());
            showAlert("Sign Up Successful", "Your sign up was successful. Your Patient ID is: " + patientID);
            showMessagingSystem(stage, patientID);  // Redirect to messaging system after sign up
        } catch (IOException e) {
            showAlert("Sign Up Error", "There was an error saving your information. Please try again.");
        }
    }

    private void handleLogIn(TextField patientIDField, Stage stage) {
        String patientID = patientIDField.getText().trim();
        if (patientID.isEmpty()) {
            showAlert("Login Error", "Patient ID is required. Please enter your Patient ID.");
            return;
        }
        if (Files.exists(Paths.get(patientID + ".txt"))) {
            showAlert("Login Successful", "You have successfully logged in.");
            showMessagingSystem(stage, patientID);  // Redirect to messaging system after login
        } else {
            showAlert("Login Error", "Invalid Patient ID. Please try again or sign up.");
        }
    }

    private void showMessagingSystem(Stage stage, String patientID) {
        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Enter your message here...");
        Button sendMessageButton = new Button("Send Message");
        sendMessageButton.setOnAction(e -> {
            sendPatientMessage(patientID, messageArea.getText());
            messageArea.clear(); // Clear the text area after sending the message
        });

        VBox messageBox = new VBox(10, new Label("Message your doctor:"), messageArea, sendMessageButton);
        messageBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(messageBox, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Patient Messaging System - ID: " + patientID);
        stage.show();
    }

    private String generateUniquePatientID() {
        Random random = new Random();
        while (true) {
            String id = String.format("%05d", random.nextInt(100000));
            if (existingIDs.add(id)) {
                return id;
            }
        }
    }

    private void savePatientInfo(String patientID, String firstName, String lastName, String phoneNumber, String email, String emergencyContact) throws IOException {
        java.nio.file.Path path = Paths.get(patientID + ".txt");
        String content = String.format("Patient ID: %s\nFirst Name: %s\nLast Name: %s\nPhone Number: %s\nEmail: %s\nEmergency Contact: %s",
                                        patientID, firstName, lastName, phoneNumber, email, emergencyContact);
        Files.write(path, content.getBytes());
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void sendPatientMessage(String patientID, String message) {
        if (message.isEmpty()) {
            showAlert("Messaging Error", "Message cannot be empty.");
            return;
        }
        System.out.println("Message from Patient ID " + patientID + ": " + message);
        // Here you would implement the actual message sending logic
    }
}