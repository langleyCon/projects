package gitFit;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateAccountPage {
	
	Database db;
	
	public CreateAccountPage(Stage createStage, Database db) {
		this.db = db;
		createStage.setTitle("GitFit");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		
		Text title = new Text("Create Your Account (* Required Fields)");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(title, 0, 0,2,1);
		
		Label username = new Label("Username*");
		grid.add(username, 0, 1);
		TextField userText = new TextField();
		userText.setAlignment(Pos.TOP_LEFT);
		grid.add(userText, 1, 1);
		
		Label password = new Label("Password*");
		grid.add(password, 0, 2);
		PasswordField passText = new PasswordField();
		grid.add(passText, 1, 2);
		
		Label firstName = new Label("First Name*");
		grid.add(firstName, 0, 3);
		TextField firstText = new TextField();
		grid.add(firstText, 1, 3);
		
		Label lastName = new Label("Last Name*");
		grid.add(lastName, 0, 4);
		TextField lastText = new TextField();
		grid.add(lastText, 1, 4);
		
		Button createAccount = new Button("Create Account");
		HBox hbCA = new HBox(10);
		hbCA.setAlignment(Pos.CENTER);
		hbCA.getChildren().add(createAccount);
		grid.add(hbCA, 1, 5);
		
		Button cancel = new Button("Cancel");
		HBox hbCancel = new HBox(10);
		hbCancel.setAlignment(Pos.CENTER);
		hbCancel.getChildren().add(cancel);
		grid.add(hbCancel, 1, 6);
		
		cancel.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				Login login = new Login();
				login.start(createStage);
			}
			
		});
		
		createAccount.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				User user = new User(userText.getText(), passText.getText(), firstText.getText(),
									 lastText.getText());
				try {
					db.createUser(user);
					MainPage main = new MainPage();
					main.mainPage(createStage, db, user);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		
				
		Scene scene = new Scene(grid, 500, 475);
		createStage.setScene(scene);
		createStage.show();
		
				
	}

}
