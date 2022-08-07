package gitFit;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TrackPage {
	
	private Database db;
	private User user;
	
	public TrackPage(Stage trackStage, User user, Database db) {
		this.db = db;
		this.user = user;
		
		trackStage.setTitle("GitFit");
		BorderPane border = new BorderPane();
		Text title = new Text("Select Type");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		MenuButton dropDown = new MenuButton("WorkoutType");
		MenuItem run = new MenuItem("Run");
		MenuItem cali = new MenuItem("Calisthenic");
		dropDown.getItems().addAll(run, cali);
		VBox top = new VBox(10);
		top.setAlignment(Pos.CENTER);
		top.getChildren().addAll(title, dropDown);
		border.setTop(top);
		
		
		
		Button track = new Button("Track");
		Button back = new Button("Back");
		
		EventHandler<ActionEvent> runEvent = new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				trackRun(border, track);
			}
			
		};
		
		EventHandler<ActionEvent> caliEvent = new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				trackCali(border, track);
				
			}
			
		};
		
		
		
		run.setOnAction(runEvent);
		cali.setOnAction(caliEvent);
		
		VBox bot = new VBox(10);
		bot.setAlignment(Pos.CENTER);
		bot.getChildren().addAll(track, back);
		border.setBottom(bot);
		
		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				MainPage p = new MainPage();
				p.mainPage(trackStage, db, user);
			}
			
		});
		
		Scene scene = new Scene(border, 500, 475);
		trackStage.setScene(scene);
		trackStage.show();
	}

	private void trackCali(BorderPane border, Button track) {
        border.setCenter(null);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		
		Text runTitle = new Text("Enter Calisthenics Info");
		runTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(runTitle, 0, 0,2,1);
		
		Label date = new Label("Date");
		grid.add(date, 0, 1);
		TextField dateField = new TextField();
		grid.add(dateField, 1, 1);
		
		Label pushups = new Label("Number of Pushups");
		grid.add(pushups, 0, 2);
		TextField pushField = new TextField();
		grid.add(pushField, 1, 2);
		
		Label pullups = new Label("Number of Pullups");
		grid.add(pullups, 0, 3);
		TextField pullField = new TextField();
		grid.add(pullField, 1, 3);
		
		Label squats = new Label("Number of Squats");
		grid.add(squats, 0, 4);
		TextField squatField = new TextField();
		grid.add(squatField, 1, 4);
		
		Label dips = new Label("Number of Dips");
		grid.add(dips, 0, 5);
		TextField dipField = new TextField();
		grid.add(dipField, 1, 5);
		
		Label situps = new Label("Number of Situps");
		grid.add(situps, 0, 6);
		TextField situpField = new TextField();
		grid.add(situpField, 1, 6);
		
		Text error = new Text();
		grid.add(error, 1, 7);
		
		border.setCenter(grid);
		
		track.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(dateField.getText().isEmpty()) {
					error.setFill(Color.FIREBRICK);
					error.setText("Please Enter Date");
				}
				else {
					try {
						CaliHistory cal = new CaliHistory();
						cal.setDate(dateField.getText());
						if(pushField.getText().isEmpty()) {
							cal.setPush(0);
						}
						else {
							cal.setPush(Integer.parseInt(pushField.getText()));
						}
						if(pullField.getText().isEmpty()) {
							cal.setPull(0);
						}
						else {
							cal.setPull(Integer.parseInt(pullField.getText()));
						}
						if(situpField.getText().isEmpty()) {
							cal.setSit(0);
						}
						else {
							cal.setSit(Integer.parseInt(situpField.getText()));
						}
						if(squatField.getText().isEmpty()) {
							cal.setSquat(0);
						}
						else {
							cal.setSquat(Integer.parseInt(squatField.getText()));
						}
						if(dipField.getText().isEmpty()) {
							cal.setDip(0);
						}
						else {
							cal.setDip(Integer.parseInt(dipField.getText()));
						}
						db.addCaliHistory(cal, user);
						createMessage("Workout Tracked", border);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			
		});		
	}

	private void trackRun(BorderPane border, Button track) {
        border.setCenter(null);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		
		Text runTitle = new Text("Enter Run Info");
		runTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(runTitle, 0, 0,2,1);
		
		Label dateName = new Label("Date");
		grid.add(dateName, 0, 1);
		TextField dateField = new TextField();
		grid.add(dateField, 1, 1);
		
		Label distance = new Label("Distance");
		grid.add(distance, 0, 2);
		TextField disField = new TextField();
		grid.add(disField, 1, 2);
		
		Label desiredTime = new Label("Time");
		grid.add(desiredTime, 0, 3);
		TextField desTField = new TextField();
		grid.add(desTField, 1, 3);
		
		Text error = new Text();
		grid.add(error, 1, 4);
		
		border.setCenter(grid);
		
		track.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(dateField.getText().isEmpty()) {
					error.setFill(Color.FIREBRICK);
					error.setText("Please Enter Date");
				}
				else if(disField.getText().isEmpty()) {
					error.setFill(Color.FIREBRICK);
					error.setText("Please Enter a Distance");
				}
				else if(desTField.getText().isEmpty()) {
					error.setFill(Color.FIREBRICK);
					error.setText("Please Enter a Time");
				}
				else {
					RunHis newHis = new RunHis();
					try {
						
						newHis.setDis(Integer.parseInt(disField.getText()));
						newHis.setTime(Integer.parseInt(desTField.getText()));
						newHis.setDate(dateField.getText());
						db.createRunHis(user, newHis);
						createMessage("Program Created", border);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
			}
			
		});	}
	
	protected void createMessage(String str, BorderPane border) {
		border.setCenter(null);
		Text message = new Text();
		message.setFill(Color.FIREBRICK);
		message.setText(str);
		HBox mBox = new HBox(10);
		mBox.setAlignment(Pos.CENTER);
		mBox.getChildren().add(message);
		border.setCenter(mBox);
		
	}

}
