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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class Login extends Application {
    public static void main(String[] args) {
    	
    	
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	Database db = new Database();
    	
    	try {
    		db.connect();
    		System.out.println("Connected");
    	}catch(SQLException e) {
    		System.out.println("Failed to connect");
    		e.printStackTrace();
    	}

    	primaryStage.setTitle("GitFit");
    	
    	GridPane grid = new GridPane();
    	grid.setAlignment(Pos.CENTER);
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(25, 25,25,25));
    	
    	Text scenetitle = new Text("Welcome to GitFit");
    	scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    	grid.add(scenetitle, 0, 0, 2, 1);
    	
    	Label userName = new Label("UserName");
    	grid.add(userName, 0, 1);
    	
    	TextField userTextField = new TextField();
    	grid.add(userTextField, 1, 1);
    	
    	Label password = new Label("Password");
    	grid.add(password, 0, 2);
    	
    	PasswordField pwBox = new PasswordField();
    	grid.add(pwBox, 1, 2);
    	//grid.setGridLinesVisible(true);
     	
    	
    	Button btn = new Button("Sign In");
    	HBox hbBtn = new HBox(10);
    	hbBtn.setAlignment(Pos.BOTTOM_CENTER);
    	hbBtn.getChildren().add(btn);
    	grid.add(hbBtn, 1, 4);
    	
    	
    	Button createAccount = new Button("Create Account");
    	HBox hbCA = new HBox(10);
    	hbCA.setAlignment(Pos.BOTTOM_CENTER);
    	hbCA.getChildren().add(createAccount);
    	grid.add(hbCA, 1, 5);
    	
    	final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        btn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				if(userTextField.getText().isEmpty()) {
					actiontarget.setFill(Color.FIREBRICK);
					actiontarget.setText("Please Enter Username");
				}
				else if(pwBox.getText().isEmpty()) {
					actiontarget.setFill(Color.FIREBRICK);
					actiontarget.setText("Please Enter Password");
				}
				//System.out.println("That's a nice Button right there!");
				//actiontarget.setFill(Color.FIREBRICK);
				//actiontarget.setText(pwBox.getText());
				else{
					ResultSet result;
					try {
						result = db.login(userTextField.getText(), pwBox.getText());
						if(result.next() == false) {
							actiontarget.setFill(Color.FIREBRICK);
							actiontarget.setText("Incorrect Username or Password");
						}
						else {
							System.out.println(result.toString());
							User user = getUser(result);
							MainPage mPage = new MainPage();
							mPage.mainPage(primaryStage, db, user );
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					

				}
				
			}
        	
        });
        
        createAccount.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				CreateAccountPage ca = new CreateAccountPage(primaryStage, db);
			}
        	
        });
    	
    	Scene scene = new Scene(grid, 300, 275);
    	primaryStage.setScene(scene);
    	
    	primaryStage.show();
    }

	protected User getUser(ResultSet result) throws SQLException {
		String username = result.getString("Username");
		String password = result.getString("Password");
		String firstName = result.getString("FirstName");
		String lastName = result.getString("LastName");
		
		User user = new User(username, password, firstName, lastName);
		return user;
	}
}











