package gitFit;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPage {
	Database db;
	User user;
	
	 public void mainPage(Stage mainStage, Database db, User user) {
		 	this.db = db;
		 	this.user = user;
		 	
		 	
	    	mainStage.setTitle("GitFit");
	    	
	    	GridPane welcomeGrid = new GridPane();
	    	welcomeGrid.setAlignment(Pos.TOP_CENTER);
	    	//welcomeGrid.setHgap(10);
	    	welcomeGrid.setVgap(10);
	    	welcomeGrid.setPadding(new Insets(25,25,25,25));
	    	
	    	Text mainTitle = new Text("Welcome " + user.getFirstName());
	    	mainTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	    	welcomeGrid.add(mainTitle, 0, 0,2,1);
	    	
	    	//GridPane buttonGrid
	    	
	    	
	    	Button seeWorkouts = new Button("See My Workouts");
	    	HBox hbSeeW = new HBox(10);
	    	hbSeeW.setAlignment(Pos.TOP_CENTER);
	    	//hbSeeW.setPrefSize(200, 200);
	    	hbSeeW.getChildren().add(seeWorkouts);
	    	welcomeGrid.add(hbSeeW, 0, 1);
	    	seeWorkouts.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					WorkoutPage wPage = new WorkoutPage(mainStage, db, user);
				}
	    	});
	    	
	    	Button createWorkout = new Button("Create New Workout");
	    	HBox hbCW = new HBox(10);
	    	hbCW.setAlignment(Pos.TOP_CENTER);
	    	hbCW.getChildren().add(createWorkout);
	    	welcomeGrid.add(createWorkout, 0, 2);
	    	createWorkout.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					CreateProgramPage cpp = new CreateProgramPage(mainStage, db, user);
				}
	    		
	    	});
	    	
	    	
	    	
	    	Button trackWorkout = new Button("Track Workout");
	    	HBox tWork = new HBox(10);
	    	tWork.setAlignment(Pos.TOP_CENTER);
	    	tWork.getChildren().add(trackWorkout);
	    	welcomeGrid.add(tWork, 0, 3);
	    	trackWorkout.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					TrackPage tpage = new TrackPage(mainStage, user, db);
				}
	    		
	    	});
	    	

	    	Button setGoals = new Button("Set Goals");
	    	HBox hbSetG = new HBox(10);
	    	hbSetG.setAlignment(Pos.TOP_CENTER);
	    	hbSetG.getChildren().add(setGoals);
	    	welcomeGrid.add(hbSetG, 0, 4);
	    	setGoals.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					SetGoalPage sGPage = new SetGoalPage();
					sGPage.goalCreation(mainStage, user, db);
				}
	    	});
	    	
	    	Button seeGoals = new Button("See Goals");
	    	HBox hbSeeG = new HBox(10);
	    	hbSeeG.setAlignment(Pos.TOP_CENTER);
	    	hbSeeG.getChildren().add(seeGoals);
	    	welcomeGrid.add(hbSeeG, 0, 5);
	    	seeGoals.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
		    		ViewGoalPage vGPage = new ViewGoalPage(mainStage, db, user);
					
				}
	    	});
	    	
	    	Button logout = new Button("Sign Out");
	    	HBox hbBack = new HBox(10);
	    	hbBack.setAlignment(Pos.TOP_CENTER);
	    	hbBack.getChildren().add(logout);
	    	welcomeGrid.add(hbBack, 0, 6);  
	    	logout.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					try {
						db.disconnect();

					}catch(SQLException e) {
						e.printStackTrace();
					};
					
					Login login = new Login();
					login.start(mainStage);
				}
	    		
	    	});
	    	
	    	
	    	Scene scene = new Scene(welcomeGrid, 300, 275);
	    	mainStage.setScene(scene);
	    }

}
