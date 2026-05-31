package controller;

import java.sql.SQLException;

import Utils.PasswordFormatChecker;
import Utils.PasswordManager;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class ChangePasswordController {
	private Stage stage;
	private Stage parentStage;
	private Model model;
	@FXML
	private PasswordField oldPassword;
	@FXML
	private PasswordField newPassword;
	@FXML
	private PasswordField confirmNewPassword;
	@FXML
	private Button confirm;
	@FXML
	private Button cancel;
	@FXML
	private Label message;
	public ChangePasswordController() {
		
	}
	
	public ChangePasswordController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	
	@FXML
	public void initialize() {
		User user = model.getCurrentUser();
		//to confirm
		confirm.setOnAction(event -> {
			
			if (!oldPassword.getText().isEmpty() && !newPassword.getText().isEmpty() && !confirmNewPassword.getText().isEmpty()) {
				String currentPwd = oldPassword.getText();
				String newPwd = newPassword.getText();
				String confirmNewPwd = confirmNewPassword.getText();
				if (!PasswordManager.verifyPassword(currentPwd, user.getPassword())) {
					showError("Current password is incorrect");
					return;
				}
				
				if (!newPwd.equals(confirmNewPwd)) {
					showError("New passwords do not match");
					return;
				}
				
				if (PasswordManager.verifyPassword(newPwd, user.getPassword())) {
					showError("New password must be different from the old one");
				}
				
				PasswordFormatChecker pwdChecker = new PasswordFormatChecker();
				String passwordMsg = pwdChecker.checkPassword(newPwd);
				
				if (passwordMsg.equals("Good")) {
					try {
						model.getUserDao().updatePassword(user.getUsername(), newPwd);
						message.setText("Your password has been updated successfully!");
						message.setTextFill(Color.GREEN);
					} catch (SQLException e) {
						showError(e.getMessage());
					}
				} else {
					showError(passwordMsg);
					return;
				}
			} else {
				showError("Missing fields");
			}
			
			
			
		});
		
		//to cancel
		cancel.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});
	}
	
	public void showError(String msg) {
		message.setText(msg);
		message.setTextFill(Color.RED);
	}
	
	public void ShowStage(GridPane root) {
		Scene scene = new Scene(root, 500, 400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Change Password");
		stage.show();
	}
}
