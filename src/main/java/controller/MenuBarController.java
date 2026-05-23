package controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import model.Model;

public class MenuBarController {
	private Stage stage;
	private Model model;
	@FXML
	private MenuItem Homepage;
	@FXML
	private MenuItem viewProfile;
	@FXML
	private MenuItem updateProfile;
	@FXML
	private MenuItem exit;
	
	MenuBarController(){
		
	}
	
	@FXML
	public void initialize() {
		
	}
}
