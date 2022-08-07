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

public class SetGoalPage {
	
	int goalType = 0;
	private Database db;
	private User user;

	public void goalCreation(Stage createGoalStage, User user, Database db) {
		this.db = db;
		this.user = user;
		createGoalStage.setTitle("GitFit");
		
		BorderPane border = new BorderPane();
		
		Text title = new Text("Set Your Goal");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
		MenuButton exercise = new MenuButton("Exercise Type");
		MenuItem run = new MenuItem("Run");
		MenuItem pushup = new MenuItem("Pushup");
		MenuItem pullup = new MenuItem("Pullup");
		MenuItem squat = new MenuItem("Squat");
		MenuItem dip = new MenuItem("Dips");
		MenuItem situps = new MenuItem("Situps");
		exercise.getItems().addAll(run, pushup, pullup,
								   squat, dip, situps);
		
		Button addGoal = new Button("Set Goal");

		
		run.setOnAction(runForm(border, addGoal));
		pushup.setOnAction(pushupForm(border,addGoal));
		pullup.setOnAction(pullupForm(border,addGoal));
		squat.setOnAction(squatForm(border, addGoal));
		dip.setOnAction(dipsForm(border, addGoal));
		situps.setOnAction(situpForm(border, addGoal));
		
		
		VBox top = new VBox(10);
		top.setAlignment(Pos.CENTER);
		top.getChildren().addAll(title, exercise);
		
		border.setTop(top);
		
		Button back = new Button("Back");
		
		VBox bottom = new VBox(10);
		bottom.setAlignment(Pos.CENTER);
		bottom.getChildren().addAll(addGoal, back);
		border.setBottom(bottom);
		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				MainPage main = new MainPage();
				main.mainPage(createGoalStage,db, user);
			}
			
		});
		
		Text error = new Text();
		
		//addGoal.setOnAction(createGoal());
		addGoal.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(goalType == 0) {
					error.setFill(Color.FIREBRICK);
					error.setText("Please Select a Goal Type");
					HBox box = new HBox(10);
					box.setAlignment(Pos.CENTER);
					box.getChildren().add(error);
					border.setCenter(box);
				}
			}
			
		});
		
		
		Scene scene = new Scene(border, 400, 350);
		createGoalStage.setScene(scene);
		createGoalStage.show();
		
	}

	private EventHandler<ActionEvent> situpForm(BorderPane border, Button add) {
		EventHandler<ActionEvent> event = new EventHandler<>() {
			@Override
			public void handle(ActionEvent arg0) {
				goalType = 2;

				border.setCenter(null);
				GridPane grid = new GridPane();
				grid.setAlignment(Pos.CENTER);
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(25,25,25,25));
				
				Text centerTitle = new Text("Enter Goal Info");
				centerTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
				Label name = new Label("Goal Name");
				TextField goalName = new TextField();
				Label sitLab = new Label("Situp Goal");
				TextField sitField = new TextField();
				Text error = new Text();
				
				grid.add(centerTitle, 0, 0,2,1);
				grid.add(name, 0, 1);
				grid.add(goalName, 1, 1);
				grid.add(sitLab, 0, 2);
				grid.add(sitField, 1, 2);
				grid.add(error, 1, 3);
				
				border.setCenter(grid);
				
				add.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if(goalName.getText().isEmpty()) {
							error.setFill(Color.FIREBRICK);
							error.setText("Please Enter Goal Name");
						}
						else if(sitField.getText().isEmpty()) {
							error.setFill(Color.FIREBRICK);
							error.setText("Please Enter Pushup Number");
						}
						else {
							try {
								CaliGoal g = createCaliGoal(goalName.getText(), 2, sitField.getText());
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				});
			}
		};
		return event;
	}

	private EventHandler<ActionEvent> dipsForm(BorderPane border, Button add) {
		EventHandler<ActionEvent> event = new EventHandler<>() {
			@Override
			public void handle(ActionEvent arg0) {
				goalType = 2;

				border.setCenter(null);
				GridPane grid = new GridPane();
				grid.setAlignment(Pos.CENTER);
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(25,25,25,25));
				
				Text centerTitle = new Text("Enter Goal Info");
				centerTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
				Label name = new Label("Goal Name");
				TextField goalName = new TextField();
				Label dipLab = new Label("Dips Goal");
				TextField dipField = new TextField();
				Text error = new Text();
				
				grid.add(centerTitle, 0, 0,2,1);
				grid.add(name, 0, 1);
				grid.add(goalName, 1, 1);
				grid.add(dipLab, 0, 2);
				grid.add(dipField, 1, 2);
				grid.add(error, 1, 3);
				
				border.setCenter(grid);
				
				add.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if(goalName.getText().isEmpty()) {
							error.setFill(Color.FIREBRICK);
							error.setText("Please Enter Goal Name");
						}
						else if(dipField.getText().isEmpty()) {
							error.setFill(Color.FIREBRICK);
							error.setText("Please Enter Pushup Number");
						}
						else {
							try {
								CaliGoal g = createCaliGoal(goalName.getText(), 5, dipField.getText());
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				});
			}
		};
		return event;
	}

	private EventHandler<ActionEvent> squatForm(BorderPane border, Button add) {
		EventHandler<ActionEvent> event = new EventHandler<>() {
			@Override
			public void handle(ActionEvent arg0) {
				goalType = 2;

				border.setCenter(null);
				GridPane grid = new GridPane();
				grid.setAlignment(Pos.CENTER);
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(25,25,25,25));
				
				Text centerTitle = new Text("Enter Goal Info");
				centerTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
				Label name = new Label("Goal Name");
				TextField goalName = new TextField();
				Label squatLab = new Label("Squat Goal");
				TextField squatField = new TextField();
				Text error = new Text();
				
				grid.add(centerTitle, 0, 0,2,1);
				grid.add(name, 0, 1);
				grid.add(goalName, 1, 1);
				grid.add(squatLab, 0, 2);
				grid.add(squatField, 1, 2);
				grid.add(error,1,3);
				
				border.setCenter(grid);
				
				add.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if(goalName.getText().isEmpty()) {
							error.setFill(Color.FIREBRICK);
							error.setText("Please Enter Goal Name");
						}
						else if(squatField.getText().isEmpty()) {
							error.setFill(Color.FIREBRICK);
							error.setText("Please Enter Pushup Number");
						}
						else {
							try {
								CaliGoal g = createCaliGoal(goalName.getText(), 4, squatField.getText());
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				});
			}
		};
		return event;
	}

	private EventHandler<ActionEvent> pullupForm(BorderPane border, Button add) {
		EventHandler<ActionEvent> event = new EventHandler<>() {
			@Override
			public void handle(ActionEvent arg0) {
				goalType = 2;

				border.setCenter(null);
				GridPane grid = new GridPane();
				grid.setAlignment(Pos.CENTER);
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(25,25,25,25));
				
				Text centerTitle = new Text("Enter Goal Info");
				centerTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
				Label name = new Label("Goal Name");
				TextField goalName = new TextField();
				Label pullLab = new Label("Pullup Goal");
				TextField pullField = new TextField();
				Text error = new Text();
				
				grid.add(centerTitle, 0, 0,2,1);
				grid.add(name, 0, 1);
				grid.add(goalName, 1, 1);
				grid.add(pullLab, 0, 2);
				grid.add(pullField, 1, 2);
				grid.add(error, 1, 3);
				
				border.setCenter(grid);
				
				add.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if(goalName.getText().isEmpty()) {
							error.setFill(Color.FIREBRICK);
							error.setText("Please Enter Goal Name");
						}
						else if(pullField.getText().isEmpty()) {
							error.setFill(Color.FIREBRICK);
							error.setText("Please Enter Pullup Number");
						}
						else {
							try {
								CaliGoal g = createCaliGoal(goalName.getText(), 3, pullField.getText());
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
					
				});
			}
		};
		return event;
	}

	private EventHandler<ActionEvent> pushupForm(BorderPane border, Button add) {
		EventHandler<ActionEvent> event = new EventHandler<>() {
			@Override
			public void handle(ActionEvent arg0) {
				goalType = 2;
				border.setCenter(null);
				GridPane grid = new GridPane();
				grid.setAlignment(Pos.CENTER);
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(25,25,25,25));
				
				Text centerTitle = new Text("Enter Goal Info");
				centerTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
				Label name = new Label("Goal Name");
				TextField goalName = new TextField();
				Label pushLab = new Label("Pushup Goal");
				TextField pushField = new TextField();
				Text error = new Text();
				
				grid.add(centerTitle, 0, 0,2,1);
				grid.add(name, 0, 1);
				grid.add(goalName, 1, 1);
				grid.add(pushLab, 0, 2);
				grid.add(pushField, 1, 2);
				grid.add(error, 1, 3);
				
				border.setCenter(grid);
				
				add.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if(goalName.getText().isEmpty()) {
							error.setFill(Color.FIREBRICK);
							error.setText("Please Enter Goal Name");
						}
						else if(pushField.getText().isEmpty()) {
							error.setFill(Color.FIREBRICK);
							error.setText("Please Enter Pushup Number");
						}
						else {
							try {
								CaliGoal g = createCaliGoal(goalName.getText(), 1, pushField.getText());
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
					
				});
				
			}
		};
		return event;
	}

	private EventHandler<ActionEvent> runForm(BorderPane border, Button add) {
		EventHandler<ActionEvent> event = new EventHandler<>() {
			@Override
			public void handle(ActionEvent arg0) {
				goalType = 1;

				border.setCenter(null);
				GridPane grid = new GridPane();
				grid.setAlignment(Pos.CENTER);
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(25,25,25,25));
				
				Text centerTitle = new Text("Enter Run Goal Info");
				centerTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
				Label name = new Label("Goal Name");
				TextField goalName = new TextField();
				Label disLab = new Label("Distance Goal");
				TextField disField = new TextField();
				Label timeLab = new Label("Time Goal");
				TextField timeField = new TextField();
				Text error = new Text();
				
				grid.add(centerTitle, 0, 0,2,1);
				grid.add(name, 0, 1);
				grid.add(goalName, 1, 1);
				grid.add(disLab, 0, 2);
				grid.add(disField, 1, 2);
				grid.add(timeLab, 0, 3);
				grid.add(timeField, 1, 3);
				grid.add(error, 1, 4);
				
				border.setCenter(grid);
				add.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						
						try {
							if(goalName.getText().isEmpty()) {
								error.setFill(Color.FIREBRICK);
								error.setText("Please Enter Goal Name");
							}
							else if(disField.getText().isEmpty()) {
								error.setFill(Color.FIREBRICK);
								error.setText("Please Enter Distance");
							}
							else if(timeField.getText().isEmpty()) {
								error.setFill(Color.FIREBRICK);
								error.setText("Please Enter Time");
							}
							else {
								RunGoal g = createRunGoal(goalName.getText(),
										  disField.getText(),
										  timeField.getText());
							}
							
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
					
				});
			}
		};
		return event;
	}
	
	public RunGoal createRunGoal(String name, String dis, String time) throws SQLException{
		RunGoal goal = new RunGoal();
		goal.setName(name);
		goal.setDistance(Integer.parseInt(dis));
		goal.setTime(Integer.parseInt(time));
		goal.setUsername(user.getUsername());
		db.createRunGoal(user, goal);
		
		
		return goal;
		
	}
	
	public CaliGoal createCaliGoal(String name, int type, String goal) throws SQLException{
		CaliGoal g = new CaliGoal();
		switch(type) {
		case 1:
			g.setName(name);
			g.setType(type);
			g.setPushGoal(Integer.parseInt(goal));
			g.setUsername(user.getUsername());
			db.createCaliGoal(g);
			break;
		case 2:
			g.setName(name);
			g.setType(type);
			g.setSitGoal(Integer.parseInt(goal));
			g.setUsername(user.getUsername());
			db.createCaliGoal(g);
			break;
		case 3:
			g.setName(name);
			g.setType(type);
			g.setPullGoal(Integer.parseInt(goal));
			g.setUsername(user.getUsername());
			db.createCaliGoal(g);
			break;
		case 4:
			g.setName(name);
			g.setType(type);
			g.setSquatGoal(Integer.parseInt(goal));
			g.setUsername(user.getUsername());
			db.createCaliGoal(g);
			break;
		case 5:
			g.setName(name);
			g.setType(type);
			g.setDipGoal(Integer.parseInt(goal));
			g.setUsername(user.getUsername());
			db.createCaliGoal(g);
			break;
		}
		
		return g;
	}



}
