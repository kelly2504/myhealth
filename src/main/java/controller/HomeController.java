package controller;

import java.awt.Menu;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class HomeController{
	private Model model;
	private Stage stage;
	private Stage parentStage;
	@FXML
	private MenuItem homepage;
	@FXML
	private Text welcome;
	@FXML
	private MenuItem viewProfile; // Corresponds to the Menu item "viewProfile" in HomeView.fxml
	@FXML
	private MenuItem updateProfile; // // Corresponds to the Menu item "updateProfile" in HomeView.fxml
	@FXML
	private Label message;
	@FXML 
	private Button AddRecord;
//	@FXML 
//	private VBox records_table;
//	@FXML 
//	private RecordTableController recordTableController;
//	
	//added user to allow for polymorphism
	public HomeController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	
	// Add your code to complete the functionality of the program
	@FXML 
	public void initialize() {
		User user = model.getCurrentUser();
		welcome.setText("Welcome back " + user.getUsername() + "!");
		
		//check if the user has any records and add them to the table 
		
		
		viewProfile.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProfileView.fxml"));
				
				ProfileController profileController = new ProfileController(stage, model);
				
				loader.setController(profileController);
				VBox root = loader.load();
				
				profileController.showStage(root);
				
			} catch (IOException e) {
				message.setText(e.getMessage());
				message.setTextFill(Color.RED);
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
				message.setText(e.getMessage());
				message.setTextFill(Color.RED);
			}
		
		});
		
		AddRecord.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddRecordView.fxml"));
				
				AddRecordController addRecordController = new AddRecordController(stage, model);
				loader.setController(addRecordController);
				GridPane root = loader.load();
				
				addRecordController.showStage(root);
				
			} catch (IOException e) {
				message.setText(e.getMessage());
				message.setTextFill(Color.RED);
			}
		});
		
		
		
		
		
	}
	
	
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 800, 700);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Home");
		stage.show();
	}
}
