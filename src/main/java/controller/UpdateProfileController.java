package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class UpdateProfileController {
	private Stage stage;
	private Stage parentStage;
	private Model model;
	@FXML
	private Text username;
	@FXML
	private TextField firstname;
	@FXML
	private TextField lastname;
	@FXML
	private Button submit;
	@FXML 
	private Button cancel;
	@FXML
	private Label message;
	
	public UpdateProfileController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;		
	}
	
	@FXML
	public void initialize() {
		User user = model.getCurrentUser();
		username.setText(user.getUsername());
		firstname.setPromptText(user.getFirstname());
		lastname.setPromptText(user.getLastname());
		
		submit.setOnAction(event -> {
			if (!firstname.getText().isEmpty() || !lastname.getText().isEmpty()) {
				if (!firstname.getText().isEmpty() && !lastname.getText().isEmpty()) {
					try {
						model.getUserDao().changeDetails(username.getText(), firstname.getText(), lastname.getText(), user.getPassword());
						
						user.setFirstname(firstname.getText());
						user.setLastname(lastname.getText());
						message.setText("Firstname and Lastname changed successfully!");
						message.setTextFill(Color.GREEN);
						
					} catch (SQLException e) {
						message.setText(e.getMessage());
						message.setTextFill(Color.RED);
					}
				} else if (!firstname.getText().isEmpty()) {
					try {
						model.getUserDao().changeDetails(username.getText(), firstname.getText(), user.getPassword());
						user.setFirstname(firstname.getText());
						
						message.setText("Firstname changed successfully!");
						message.setTextFill(Color.GREEN);
					} catch (SQLException e) {
						message.setText(e.getMessage());
						message.setTextFill(Color.RED);
					}
				} else {
					try {
						model.getUserDao().changeDetails(username.getText(), lastname.getText(), user.getPassword(), 0);
						user.setLastname(lastname.getText());
						
						message.setText("Lastname changed successfully!");
						message.setTextFill(Color.GREEN);
					} catch (SQLException e) {
						message.setText(e.getMessage());
						message.setTextFill(Color.RED);
					}
				}
				

				firstname.setPromptText(user.getFirstname());
				lastname.setPromptText(user.getLastname());
			} else {
				message.setText("Firstname and Lastname cannot be left empty.");
				message.setTextFill(Color.RED);
			}
			
						
			firstname.clear();
			lastname.clear();
		});
		
		cancel.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});
			
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("UpdateProfile");
		stage.show();
	}
}
