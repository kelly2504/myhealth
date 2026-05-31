package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;
import utils.PasswordFormatChecker;

//TODO -- Allow user to enter their first name and last name 
public class SignupController {
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private TextField firstname;
	@FXML 
	private TextField lastname;
	@FXML
	private Button createUser;
	@FXML
	private Button close;
	@FXML
	private Label status;
	
	private Stage stage;
	private Stage parentStage;
	private Model model;
	
	public SignupController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

	//TODO - ONLY ALLOW USERS TO CREATE THEIR ACCOUNT WHEN THE USERNAME AND PASSWORD ARE VALID
	@FXML
	public void initialize() {
		createUser.setOnAction(event -> {
			if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
				//check if password is in the right format
				PasswordFormatChecker pwdChecker = new PasswordFormatChecker();
				String pwdMsg = pwdChecker.checkPassword(password.getText());
				
				if (!pwdMsg.equals("Good")) {
					status.setText(pwdMsg);
					status.setTextFill(Color.RED);
					return;
				}
				
				User user;
				try {
					//passes in the user fields to the Database
					user = model.getUserDao().createUser(username.getText(), firstname.getText(), lastname.getText(), password.getText());
					
					if (user != null) {
						status.setText("User created");
						status.setTextFill(Color.GREEN);
						
					} else {
						status.setText("Cannot create user");
						status.setTextFill(Color.RED);
					}
					
					//clears the form after 
					username.clear();
					firstname.clear();
					lastname.clear();
					password.clear();
				} catch (SQLException e) {
					// output a text if the user name already exists
					status.setText("\tInvalid Username.\n This username already exists. Please choose another one");
					status.setTextFill(Color.RED);
				} 
				
			} else {
				status.setText("Empty username or password");
				status.setTextFill(Color.RED);
			}
		});
		
		//TODO -- Allow users to re-enter a new 
		close.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 600);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Sign up");
		stage.show();
	}
}
