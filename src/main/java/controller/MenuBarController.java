package controller;

import java.io.IOException;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import model.Model;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MenuBarController {
	private Stage stage;
	private Model model;
	private Label message;
	
	private Consumer<String> onError;
	@FXML
	private MenuItem homepage;
	@FXML
	private MenuItem viewProfile;
	@FXML
	private MenuItem updateProfile;
	@FXML
	private MenuItem exit;
	
	
	public MenuBarController(){
		
	}
	
	public MenuBarController(Stage stage, Model model) {
		this.stage = stage;
		this.model = model;
		
	}
	
	public void setOnError(Consumer<String> onError) { this.onError = onError; }
	
	@FXML
	public void initialize() {
		homepage.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashboardView.fxml"));
				HomeController homeController = new HomeController(stage, model);
				
				loader.setController(homeController);
				VBox root = loader.load();

				homeController.showStage(root);
				stage.close();
			}catch (IOException e) {
				sendError(e.getMessage());
			}
		});
		
		viewProfile.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProfileView.fxml"));

				ProfileController profileController = new ProfileController(stage, model);

				loader.setController(profileController);
				VBox root = loader.load();

				profileController.showStage(root);
				
			} catch (IOException e) {
				sendError(e.getMessage());
			}
		});
		
		updateProfile.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateProfileView.fxml"));

				UpdateProfileController updateController = new UpdateProfileController(stage, model);
				loader.setController(updateController);
				GridPane root = loader.load();

				updateController.showStage(root);
			} catch (IOException e) {
				sendError(e.getMessage());
			}

		});
		
		exit.setOnAction(event -> {
			Platform.exit();
		});
		
	}
	
	private void sendError(String errorMessage) {
        if (onError != null) {
            onError.accept(errorMessage);
        } else {
            System.err.println("MenuBar error: " + errorMessage);
        }
    }
}
