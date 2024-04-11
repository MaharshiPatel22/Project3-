package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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

    private void handleSignUp(TextField firstName, TextField lastName, TextField phoneNumber,
                              TextField email, TextField emergencyContact) {
        // Logic to handle sign up, generate unique patient ID, and save patient information
    }

    private void handleLogIn(TextField patientID) {
        // Logic to handle patient log in using the patient ID
    }
}