package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.HealthRecord;
import model.Model;
import model.Record_List;
import model.User;

public class HomeController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	
	private RecordTableController recordTableController;
	@FXML
	private MenuBar menu;
	@FXML
	private MenuItem homepage;
	@FXML
	private Text welcome;
	@FXML
	private MenuItem viewProfile; // Corresponds to the Menu item "viewProfile" in HomeView.fxml
	@FXML
	private MenuItem updateProfile; // // Corresponds to the Menu item "updateProfile" in HomeView.fxml
	@FXML
	private MenuItem exit;
	@FXML
	private Label message;
	@FXML
	private Button addRecord;
	@FXML
	private Button downloadRecord;
	@FXML
	private Button selectAll;
	@FXML
	private Button clearSelection;

	@FXML
	private VBox recordTableContainer;
	@FXML
	private VBox menuBarContainer;

	public HomeController() {
		
	}
	
	// added user to allow for polymorphism
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
		
		loadTable();
		
		//Menu Bar 
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

		
		//button to add new record for the user
		addRecord.setOnAction(event -> {
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
		
		//Button to download all selected records
		downloadRecord.setOnAction(event -> {
			List<HealthRecord> records = recordTableController.getSelectedRecords();
			if (!records.isEmpty()) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save Records");
				fileChooser.setInitialFileName("health_records.txt");
				fileChooser.getExtensionFilters().add(
						new FileChooser.ExtensionFilter("Text Files", "*.txt"));
				
				File file = fileChooser.showSaveDialog(stage);
				SaveRecordsToFile(records, file);
				
				
			} else {
				message.setText("You have not selected any records to download");
				message.setTextFill(Color.RED);
			}
		});

	}
	
	// creates layout for record - and writes it to the file
	private void SaveRecordsToFile(List<HealthRecord> records, File file) {
		User user = model.getCurrentUser();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			//Patient Main Information
			writer.write("========================================================");
			writer.newLine();
			writer.write("                   MyHealth                         ");
			writer.newLine();
			writer.write("Patient Info:");
			writer.newLine();
			writer.write("Username: " + user.getUsername());
			writer.newLine();
			writer.write("Fullname: " + user.getFirstname() + " " + user.getLastname());
			writer.newLine();
			writer.write("=========================================================");
			writer.newLine();
			writer.newLine();
			writer.write("Patient Records: ");
			writer.newLine();
			
			
			for (HealthRecord record : records) {
				writer.write("---------------------------------------------------------");
				writer.newLine();
				writer.write("Record Number : " + record.getRecord_number());
				writer.newLine();
				writer.write("Date:         : " + record.getDate().toString());
				writer.newLine();
				writer.write("Weight        : " + record.getWeight());
				writer.newLine();
				writer.write("Temperature   : " + record.getTemperature());
				writer.newLine();
				writer.write("Blood pressure: " + record.getBlood_pressure());
				writer.newLine();
				writer.write("Note          : " + record.getNote());
				writer.newLine();
				writer.newLine();
			}
			writer.write("==================================================");
			writer.newLine();
            writer.write("End of Report");
            writer.newLine();
            writer.write("===================================");
            
            writer.close();
			
		} catch (IOException e) {
			message.setText(e.getMessage());
			message.setTextFill(Color.RED);
		}
	}
	
	//Loads the record table on the dashboard
	public void loadTable() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecordTableView.fxml"));

			// Get the controller from the loader
//			System.out.println("setting CONTROLLER");
			recordTableController = new RecordTableController();
			recordTableController.setStage(stage);
			recordTableController.setModel(model);
			loader.setController(recordTableController);
			
//			System.out.println("setting node");
			Node tableNode = loader.load();
            
//			System.out.println("setting user");
			recordTableController.loadUserRecords();
			
			// Show message if no records found
	        if (model.getCurrentUser().getRecords().get_length() == 0) {
	            message.setText("No records found. Please add a record.");
	        }

			// Add the table to the container
			recordTableContainer.getChildren().add(tableNode);

//            recordTableController.loadUserRecords(model.getCurrentUser());
		} catch (IOException e) {
			message.setText(e.getMessage());
			message.setTextFill(Color.RED);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

	}
	
	

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 800, 700);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Home");
		stage.show();
	}
}
