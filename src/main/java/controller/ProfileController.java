package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class ProfileController {
	private Stage stage;
	private Model model;
	@FXML
	private Text username;
	@FXML
	private Text firstname;
	@FXML
	private Text lastname;
	@FXML 
	private MenuItem homepage;
	@FXML
	private MenuItem viewProfile; 
	@FXML
	private MenuItem updateProfile; 
	@FXML
	private Label message;
	@FXML
	private VBox menuBarContainer;
	@FXML
	private Button changePassword;
	
	
	public ProfileController(Stage stage, Model model){
		this.stage = stage;
		this.model = model;
	}
	
	@FXML
	public void initialize() {
		User user = model.getCurrentUser();
		username.setText(user.getUsername());
		firstname.setText(user.getFirstname());
		lastname.setText(user.getLastname());
		

		//load menu bar 
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MenuBar.fxml"));
			MenuBarController menuBarController = new MenuBarController(stage, model);
			
			loader.setController(menuBarController);
			
			Node menuBarNode = loader.load();
			
			menuBarContainer.getChildren().add(menuBarNode);
		} catch (IOException e) {
			message.setText(e.getMessage());
			message.setTextFill(Color.RED);
		}
		
		
		//button to change the password
		changePassword.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChangePasswordView.fxml"));
				ChangePasswordController pwdController = new ChangePasswordController(stage, model);
				
				loader.setController(pwdController);
				GridPane root = loader.load();
				
				pwdController.ShowStage(root);
			} catch (IOException e) {
				message.setText(e.getMessage());
				message.setTextFill(Color.RED);
			}
			
			
			
		});
		
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("ProfileView");
		stage.show();
	}
}
