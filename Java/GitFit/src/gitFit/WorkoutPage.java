package gitFit;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WorkoutPage {
	
	private Database db;
	private User user;
	
	public WorkoutPage(Stage workoutStage, Database db, User user) {
		this.db = db;
		this.user = user;
		
		workoutStage.setTitle("GitFit");
		
		BorderPane border = new BorderPane();
		
		Text title = new Text("Select A Workout to View");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		Label srchTitle = new Label("Enter Workout Name");
		MenuButton menu = new MenuButton("Type of Workout");
		MenuItem run = new MenuItem("Run");
		MenuItem cali = new MenuItem("Calisthenics");
		
		menu.getItems().addAll(run, cali);
		
		//TextField search = new 
		
		
		
		VBox top = new VBox(10);
		top.setAlignment(Pos.TOP_CENTER);
		top.getChildren().addAll(title, menu);	
		
		border.setTop(top);
		
		run.setOnAction(getRunWorkouts(border));
		cali.setOnAction(getCaliWorkouts(border));
		
		Button back = new Button("Back");
		VBox backBtn = new VBox(10);
		backBtn.setAlignment(Pos.CENTER);
		backBtn.getChildren().add(back);
		border.setBottom(backBtn);
		
		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				MainPage p = new MainPage();
				p.mainPage(workoutStage, db, user);
			}
			
		});
		
		
		
		
		Scene scene = new Scene(border, 500, 475);
		workoutStage.setScene(scene);
		
	}

	private EventHandler<ActionEvent> getCaliWorkouts(BorderPane border) {
		EventHandler<ActionEvent> event = new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				border.setCenter(null);
				ScrollPane scroll = new ScrollPane();
				String text;
				
				try {
					text = "Number of Calisthenics Programs: " + db.getProgramCount(user, 2)
							+ "\n---------------------------------------------\n";
					ResultSet set = db.getCaliWorkouts(user);
					while(set.next()) {
						text = text + "\nProgram Name: " + set.getString("ProgramName")
							   + "\nPushups: " + set.getInt("Pushup")
							   + "\nSitups: " + set.getInt("Situp")
							   + "\nPullups: " + set.getInt("Pullup")
							   + "\nSquats: " + set.getInt("Squat")
							   + "\nDips: " + set.getInt("Dips")
							   + "\n---------------------------------------------\n";
						
					}
					Text programs = new Text(text);
					programs.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
					scroll.setContent(programs);
					border.setCenter(scroll);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		
		
		return event;
	}

	private EventHandler<ActionEvent> getRunWorkouts(BorderPane border) {
		EventHandler<ActionEvent> event = new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				border.setCenter(null);
				ScrollPane scroll = new ScrollPane();
				String text;
				try {
					text = "Number of Runnning Programs: " + db.getProgramCount(user, 1)
							+ "\n---------------------------------------------\n";
					ResultSet set = db.getRunWorkouts(user);
					while(set.next()) {
						text = text + "\nProgram Name: " + set.getString("ProgramName")
								+ "\nDistance: " + set.getInt("DesiredDistance")
								+ "\nDesired Time: " + set.getInt("DesiredTime")
								+ "\n---------------------------------------------\n";
					}
					Text programs = new Text(text);
					programs.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
					scroll.setContent(programs);
					border.setCenter(scroll);


				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		};
		
		
		return event;
	}

}
