package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DoctorsPortal {

    public void display(Stage stage) {
    	
        // Log In Form
        Label lblDoctorID = new Label("Doctor ID:");
        
        //textField
        TextField txtDoctorID = new TextField();
        
        // log in button
        Button btnLogIn = new Button("Log In");
        btnLogIn.setOnAction(e -> handleLogIn(txtDoctorID, stage));

        // Layout for Log In
        VBox logInBox = new VBox(10, lblDoctorID, txtDoctorID, btnLogIn);

        //gridpane
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        gridPane.add(logInBox, 0, 0);
        
        //Scene layout
        Scene scene = new Scene(gridPane, 400, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void handleLogIn(TextField doctorID, Stage stage) {
        // Logic to handle doctor log in using the doctor ID
        // For demonstration purposes, let's assume the login is successful and proceed to the doctor's dashboard
        showDoctorsDashboard(stage);
    }

    private void showDoctorsDashboard(Stage stage) {
        TabPane tabPane = new TabPane();

        Tab visitHistoryTab = new Tab("Visit History");
        visitHistoryTab.setContent(new Label("Visit history content goes here."));
        visitHistoryTab.setClosable(false);

        Tab messagePortalTab = new Tab("Message Portal");
        VBox messageBox = new VBox(10);
        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Type your message here...");
        Button sendMessageButton = new Button("Send Message");
        sendMessageButton.setOnAction(e -> {
            System.out.println("Message Sent: " + messageArea.getText()); // Placeholder action
            messageArea.clear();
        });
        messageBox.getChildren().addAll(messageArea, sendMessageButton);
        messagePortalTab.setContent(messageBox);
        messagePortalTab.setClosable(false);

        Tab medicalRecordTab = new Tab("Medical Record");
        medicalRecordTab.setContent(new Label("Medical record content goes here."));
        medicalRecordTab.setClosable(false);

        tabPane.getTabs().addAll(visitHistoryTab, messagePortalTab, medicalRecordTab);

        Scene scene = new Scene(tabPane, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}