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
				/*
				 * A password should : 
				 * 	1. Be at least 8 characters long
				 *  2. include letters, numbers and at least one character and 
				 *   one upper letter 
				 *   */
				
				boolean hasSpecial = false;
				boolean hasDigit = false;
				boolean hasUpper = false;
				boolean hasAlpha = false;
				
				String pwd = password.getText();
				if (pwd.length() < 8)  { 
					status.setText("Invalid Password. Password needs to contain at least 8 characters!");
					status.setTextFill(Color.RED);
					return;
				}
					
				// password validation
				for (int i = 0; i < pwd.length(); ++i) {
					if (Character.isDigit(pwd.charAt(i)) ) {
						hasDigit = true;
					} else if (Character.isLetter(pwd.charAt(i))) {
						hasAlpha = true;
						if (Character.isUpperCase(pwd.charAt(i))) {
							hasUpper = true;
						}
						//already filtered digits and characters - else special char or whitespace
					} else if (!Character.isWhitespace(pwd.charAt(i))) {
							hasSpecial = true;
					}
				}
					
				// actually apply the validations made
				if (!(hasSpecial && hasDigit && hasUpper && hasAlpha)) {
					
					status.setText("\tInvalid Password.\n Your password should contain letters, digits, an uppercase letter and at least one special character");
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
