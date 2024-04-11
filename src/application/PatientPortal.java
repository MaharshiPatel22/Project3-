package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.HashSet;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

public class PatientPortal {

    public void display(Stage stage) {
        // Sign Up Form
        Label lblFirstName = new Label("First Name:");
        TextField txtFirstName = new TextField();

        Label lblLastName = new Label("Last Name:");
        TextField txtLastName = new TextField();

        Label lblPhoneNumber = new Label("Phone Number:");
        TextField txtPhoneNumber = new TextField();

        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();

        Label lblEmergencyContact = new Label("Emergency Contact:");
        TextField txtEmergencyContact = new TextField();

        Button btnSignUp = new Button("Sign Up");
        btnSignUp.setOnAction(e -> handleSignUp(txtFirstName, txtLastName, txtPhoneNumber, txtEmail, txtEmergencyContact));

        // Log In Form
        Label lblPatientID = new Label("Patient ID:");
        TextField txtPatientID = new TextField();

        Button btnLogIn = new Button("Log In");
        btnLogIn.setOnAction(e -> handleLogIn(txtPatientID));

        // Layout for Sign Up and Log In
        VBox signUpBox = new VBox(10, lblFirstName, txtFirstName, lblLastName, txtLastName,
                lblPhoneNumber, txtPhoneNumber, lblEmail, txtEmail, lblEmergencyContact, txtEmergencyContact, btnSignUp);

        VBox logInBox = new VBox(10, lblPatientID, txtPatientID, btnLogIn);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        gridPane.add(signUpBox, 0, 0);
        gridPane.add(logInBox, 1, 0);

        Scene scene = new Scene(gridPane, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void handleSignUp(TextField firstName, TextField lastName, TextField phoneNumber, TextField email, TextField emergencyContact) {
        // First, validate the input to ensure that all fields are filled in
        if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || 
            phoneNumber.getText().isEmpty() || email.getText().isEmpty() || 
            emergencyContact.getText().isEmpty()) {
            // Display an alert to inform the user that all fields are required
            showAlert("Sign Up Error", "All fields are required. Please fill in all fields.");
            return; // Stop the method here if validation fails
        }

        // Generate a unique patient ID
        String patientID = PatientIDGenerator();

        // Save the patient information to a file or database
        try {
            savePatientInfo(patientID, firstName.getText(), lastName.getText(), phoneNumber.getText(), email.getText(), emergencyContact.getText());
            // Display the patient ID to the user and instruct them to save it
            showAlert("Sign Up Successful", "Your sign up was successful. Your Patient ID is: " + patientID);
        } catch (IOException e) {
            // If there is an error saving the patient info, display an error message
            showAlert("Sign Up Error", "There was an error saving your information. Please try again.");
        }
    }



    private String PatientIDGenerator() {
    	Random random = new Random();
        HashSet<Integer> existingIDs = new HashSet<>();
    	while (true) {
            int id = 10000 + random.nextInt(90000);  // Generates a number from 10000 to 99999
            if (existingIDs.add(id)) {
                return String.valueOf(id);
            }
    	}
	}

	private void savePatientInfo(String patientID, String firstName, String lastName, String phoneNumber, String email, String emergencyContact) throws IOException {
        // Here you would implement the logic to save the information to a file or database
        // For simplicity, this example saves to a text file
        java.nio.file.Path path = Paths.get(patientID + ".txt");
        String content = String.format("Patient ID: %s%nFirst Name: %s%nLast Name: %s%nPhone Number: %s%nEmail: %s%nEmergency Contact: %s",
                                        patientID, firstName, lastName, phoneNumber, email, emergencyContact);
        Files.write(path, content.getBytes());
    }

    private void showAlert(String title, String content) {
        // Implement alert showing logic
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void handleLogIn(TextField patientIDField) {
        String patientID = patientIDField.getText().trim();

        // Check if the input field is empty
        if (patientID.isEmpty()) {
            showAlert("Login Error", "Patient ID is required. Please enter your Patient ID.");
            return;
        }

        // Check if a file corresponding to the patient ID exists
        java.nio.file.Path path = Paths.get(patientID + ".txt");
        if (Files.exists(path)) {
            // If the file exists, login is successful
            showAlert("Login Successful", "You have successfully logged in.");
            // Here, you can transition to another scene or interface that shows patient details
            showPatientDetails(patientID);
        } else {
            // If the file does not exist, show an error message
            showAlert("Login Error", "Invalid Patient ID. Please try again or sign up.");
        }
    }

    private void showAlertAgain(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showPatientDetails(String patientID) {
        try {
            // Load patient details from file
            java.nio.file.Path path = Paths.get(patientID + ".txt");
            String details = Files.readString(path);

            // Create labels and text area for displaying details
            Label labelDetails = new Label("Patient Details:");
            TextArea textAreaDetails = new TextArea(details);
            textAreaDetails.setEditable(false);

            // Setting up the layout
            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(10));
            gridPane.setVgap(10);
            gridPane.setHgap(10);

            gridPane.add(labelDetails, 0, 0);
            gridPane.add(textAreaDetails, 0, 1);

            // Set the scene and stage
            Scene scene = new Scene(gridPane, 400, 300);
            Stage detailsStage = new Stage();
            detailsStage.setTitle("Patient Details - ID: " + patientID);
            detailsStage.setScene(scene);
            detailsStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load patient details.");
        }
    }
}
