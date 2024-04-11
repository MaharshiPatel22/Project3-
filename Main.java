package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	private static final boolean True = false;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Page");

        
        
        Button login = new Button("Login");
        
        Label messageLabel = new Label();
        messageLabel.setText("How would you like to Login as?");
        
        RadioButton doctor = new RadioButton("Patient");
	    RadioButton patient = new RadioButton("Doctor");
        
        ToggleGroup loginAs = new ToggleGroup();
        doctor.setToggleGroup(loginAs);
        patient.setToggleGroup(loginAs);
        
        
       login.setOnAction(event -> {
        	if(doctor.isSelected() == True) {
        		
        		switchToDoctorScene(primaryStage);
        	}else if(patient.isSelected() == True) {
        		
        		switchToPatientPortal(primaryStage);
        	}else {

                showMessage();
            }
        });

        // Creating a grid pane layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
       // gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPrefSize(400, 400);
        
        // gridPane 
        gridPane.add(messageLabel,4,4); 
        gridPane.add(doctor,4,5);
        gridPane.add(patient, 4, 6);
        gridPane.add(login, 4, 7);

       
        // Setting up the scene
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void switchToPatientPortal(Stage stage) {
        // Assuming PatientPortalView is your class for the patient portal
        PatientPortal patientPortalView = new PatientPortal();
        patientPortalView.display(stage);
    }

    //switch to doctor scene
    private void switchToDoctorScene(Stage primaryStage) {
    	DoctorsPortal doctorportal= new DoctorsPortal();
    	doctorportal.display(primaryStage);
       
    	
    }
    // message show
    private void showMessage() {
        System.out.print("Please choose your login identity");
    }
    // main method
    public static void main(String[] args) {
        launch(args);
    }
}
