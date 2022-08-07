package gitFit;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

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
import javafx.stage.Popup;
import javafx.stage.Stage;

public class CreateProgramPage {
	
	private int currentType = 0;
	private Database db;
	private User user;
	
	public CreateProgramPage(Stage createProgramStage, Database db, User user) {
		this.db = db;
		this.user = user;
		
		createProgramStage.setTitle("GitFit");
		
		BorderPane border = new BorderPane();
		
		Text title = new Text("Create Your Workout");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		VBox hbTitle = new VBox(10);
		hbTitle.setAlignment(Pos.CENTER);
		hbTitle.getChildren().add(title);
		border.setTop(hbTitle);
		
		MenuButton programType = new MenuButton("Program Type");
		MenuItem run = new MenuItem("Run");
		MenuItem calisthenics = new MenuItem("Calisthenics");
		programType.getItems().addAll(run, calisthenics);
		Text selectType = new Text("Select Program Type");
		selectType.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		
		VBox vbDropDown = new VBox(10);
		vbDropDown.setAlignment(Pos.CENTER);
		vbDropDown.getChildren().addAll(selectType, programType);
		border.setLeft(vbDropDown);
		
		Button createProgram = new Button("Create Program");
		Button back = new Button("Back");
		VBox buttons = new VBox(10);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(createProgram, back);
		border.setBottom(buttons);
		
		back.setOnAction(backToMain(createProgramStage));
		
		
		EventHandler<ActionEvent> runForm = new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				currentType = 1;
				createRunForm(border, createProgram);
			}
			
		};
		
		EventHandler<ActionEvent> calisForm = new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				currentType = 2;
				createCalisForm(border, createProgram);
			}
			
		};
		
		run.setOnAction(runForm);
		calisthenics.setOnAction(calisForm);
		
		createProgram.setOnAction(createProgramAction(border));
		
		
		Scene scene = new Scene(border, 550, 475);
		createProgramStage.setScene(scene);
		createProgramStage.show();
	}

	private EventHandler<ActionEvent> createProgramAction(BorderPane border) {
		EventHandler<ActionEvent> action = new EventHandler<>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(currentType == 0) {
					printSelectionError(border);
				}
			}
		};
		
		return action;
	}
	

	protected void printSelectionError(BorderPane border) {
		border.setCenter(null);
		Text error = new Text("Please Select Program Type");
		error.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		error.setFill(Color.FIREBRICK);
		VBox box = new VBox(10);
		box.setAlignment(Pos.CENTER);
		box.getChildren().add(error);
		border.setCenter(box);
	}

	private EventHandler<ActionEvent> backToMain(Stage stage) {
		EventHandler<ActionEvent> event = new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				MainPage main = new MainPage();
				main.mainPage(stage,db,user);
			}
			
		};
		return event;
	}

	protected void createCalisForm(BorderPane border, Button create) {
		border.setCenter(null);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		
		Text runTitle = new Text("Enter Calisthenics Info");
		runTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(runTitle, 0, 0,2,1);
		
		Label programName = new Label("Program Name");
		grid.add(programName, 0, 1);
		TextField pNameField = new TextField();
		grid.add(pNameField, 1, 1);
		
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
		
		create.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(pNameField.getText().isEmpty()) {
					error.setFill(Color.FIREBRICK);
					error.setText("Please Enter Program Name");
				}
				else {
					Program newPro = new Program(pNameField.getText(), currentType, user.getUsername());
					try {
						db.createProgram(user, newPro);
						newPro.setProgramId(db.getProgramId(newPro));
						Calisthenics cal = new Calisthenics();
						if(pushField.getText().isEmpty()) {
							cal.setPushups(0);
						}
						else {
							cal.setPushups(Integer.parseInt(pushField.getText()));
						}
						if(pullField.getText().isEmpty()) {
							cal.setPullups(0);
						}
						else {
							cal.setPullups(Integer.parseInt(pullField.getText()));
						}
						if(situpField.getText().isEmpty()) {
							cal.setSitups(0);
						}
						else {
							cal.setSitups(Integer.parseInt(situpField.getText()));
						}
						if(squatField.getText().isEmpty()) {
							cal.setSquats(0);
						}
						else {
							cal.setSquats(Integer.parseInt(squatField.getText()));
						}
						if(dipField.getText().isEmpty()) {
							cal.setDips(0);
						}
						else {
							cal.setDips(Integer.parseInt(dipField.getText()));
						}
						cal.setProgramId(newPro.getProgramId());
						db.createCal(cal, newPro);
						createMessage("Program Created", border);
						currentType = 0;
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			
		});
		
		
		
	}

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

	protected void createRunForm(BorderPane border, Button create) {
		border.setCenter(null);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		
		Text runTitle = new Text("Enter Run Info");
		runTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(runTitle, 0, 0,2,1);
		
		Label programName = new Label("Program Name");
		grid.add(programName, 0, 1);
		TextField pNameField = new TextField();
		grid.add(pNameField, 1, 1);
		
		Label distance = new Label("Distance");
		grid.add(distance, 0, 2);
		TextField disField = new TextField();
		grid.add(disField, 1, 2);
		
		Label desiredTime = new Label("Desired Time");
		grid.add(desiredTime, 0, 3);
		TextField desTField = new TextField();
		grid.add(desTField, 1, 3);
		
		Text error = new Text();
		grid.add(error, 1, 4);
		
		border.setCenter(grid);
		
		create.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(pNameField.getText().isEmpty()) {
					error.setFill(Color.FIREBRICK);
					error.setText("Please Enter Program Name");
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
					Program newPro = new Program(pNameField.getText(), currentType, user.getUsername());
					try {
						db.createProgram(user, newPro);
						newPro.setProgramId(db.getProgramId(newPro));
						Run run = new Run();
						run.setDistance(Integer.parseInt(disField.getText()));
						run.setTime(Integer.parseInt(desTField.getText()));
						run.setProgramId(newPro.getProgramId());
						db.createRun(run, newPro);
						createMessage("Program Created", border);
						currentType = 0;
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
			}
			
		});
		
	}

}
