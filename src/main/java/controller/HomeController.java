package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class HomeController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	private User user;
	@FXML
	private Text welcome;
	@FXML
	private MenuItem viewProfile; // Corresponds to the Menu item "viewProfile" in HomeView.fxml
	@FXML
	private MenuItem updateProfile; // // Corresponds to the Menu item "updateProfile" in HomeView.fxml
	
	//added user to allow for polymorphism
	public HomeController(Stage parentStage, Model model, User user) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.user = user;
	}
	
	// Add your code to complete the functionality of the program
	@FXML 
	public void initialize() {
		
	}
	
	
	
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Home");
		stage.show();
	}
}
