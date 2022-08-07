package gitFit;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewGoalPage {
	
	private User user;
	private Database db;
	
	public ViewGoalPage(Stage viewGPage, Database db, User user) {
		this.user = user;
		this.db = db;
		
		viewGPage.setTitle("GitFit");
		
		BorderPane border = new BorderPane();
		
		Text title = new Text("See Your Goals");
		MenuButton types = new MenuButton("Select Goal Type");
		MenuItem run = new MenuItem("Run");
		MenuItem push = new MenuItem("Pushup");
		MenuItem sit = new MenuItem("Situp");
		MenuItem pull = new MenuItem("Pullup");
		MenuItem squat = new MenuItem("Squat");
		MenuItem dips = new MenuItem("Dips");
		types.getItems().addAll(run, push,sit,pull,squat,dips);
		VBox top = new VBox(10);
		top.setAlignment(Pos.CENTER);
		top.getChildren().addAll(title, types);
		border.setTop(top);
		
		run.setOnAction(showGoal("Runs", 0, border));
		push.setOnAction(showGoal("PushUp", 1, border));
		sit.setOnAction(showGoal("Situp", 2, border));
		pull.setOnAction(showGoal("Pullup", 3, border));
		squat.setOnAction(showGoal("Squat", 4, border));
		dips.setOnAction(showGoal("Dips", 5, border));
		
		Button back = new Button("Back");
		VBox bot = new VBox(10);
		bot.setAlignment(Pos.CENTER);
		bot.getChildren().add(back);
		border.setBottom(bot);
		
		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				MainPage p = new MainPage();
				p.mainPage(viewGPage, db, user);
			}
			
		});
		
		
		
		Scene scene = new Scene(border, 500, 475);
		viewGPage.setScene(scene);
		viewGPage.show();
		
	}
	
	public EventHandler<ActionEvent> showGoal(String workout, int type, BorderPane border){
		
		EventHandler<ActionEvent> event = new EventHandler<>() {
			@Override
			public void handle(ActionEvent arg0) {
				border.setCenter(null);
				ScrollPane scroll = new ScrollPane();
				Text text = new Text();
				text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
				String content;
				boolean check = false;
				try {
					
					if(type == 0) {
						content = "Number of Run Goals: " + db.runGoalCount("RunGoal", user)
								  + "\n---------------------------------------------\n";
						ResultSet runGoals = db.getRunGoals(user);
						
						while(runGoals.next()) {
							content = content + "Goal Name: " + runGoals.getString("GoalName")
									  + "\nDesired Distance: " + runGoals.getInt("DistanceGoal")
									  + "\nDesired Time: " + runGoals.getInt("TimeGoal");
							ResultSet completed = db.completedRunGoal(runGoals.getInt("RunGId"), user);
							if(completed.next()) {
								check = true;
								content = content + "\nGoal Completed: On " + completed.getString("Date");
							}
							else {
								content = content + "\nGoal Comleted: No";
							}
							
							if(!check) {
								ResultSet programs = db.runCheck(user);
								if(programs.next()) {
									content = content + "\nProgram That Will Acheive Goal: "
									          + programs.getString("ProgramName");

								}
							}
							content = content + "\n---------------------------------------------\n";
							check = false;
						}
						text.setText(content);
						scroll.setContent(text);
						border.setCenter(scroll);
					}
					else {
						content = "Number of " + workout+ " Goals: " + db.caliGoalCount(type, user);
						ResultSet caliGoals = db.getCaliGoals(user, type);
						
						while(caliGoals.next()) {
							content = content + "\nGoal Name: " + caliGoals.getString("CalisthenicName")
										+ "\nNumber of " + workout + ": " + caliGoals.getInt(workout +"Goal");                                          
							ResultSet compC = db.completedCali(workout, caliGoals.getInt("CalisthenicsGId"), user);
							if(compC.next()) {
								check = true;
								content = content + "\nGoal Completed: On " + compC.getString("CalisDate");
							}
							else {
								content = content + "\nGoal Completed: No";
							}
							if(!check) {
								ResultSet calPro = db.caliCheck(workout, user);
								if(calPro.next()) {
									content = content + "\nProgram That Will Achieve Goal: "
											+ calPro.getString("ProgramName");
								}
							}
							content = content + "\n---------------------------------------------\n";
							check = false;
						}
						text.setText(content);
						scroll.setContent(text);
						border.setCenter(scroll);
					}
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
				
			}
			
		};
		return event;
	}

}

















