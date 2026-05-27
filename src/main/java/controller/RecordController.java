package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.HealthRecord;
import model.Model;
import model.User;

public class RecordController {
	private Stage stage;
	private Model model;
	private Stage parentStage;
	private HealthRecord record;
	
	@FXML
	private Label date;
	@FXML
	private Text username;
	@FXML
	private Text fullname;
	@FXML
	private Text weight;
	@FXML
	private Text temperature;
	@FXML
	private Text bloodPressure;
	@FXML
	private Text note;
	@FXML
	private Button download;
	@FXML
	private Button goBack;
	@FXML
	private Button edit;
	@FXML
	private Label message;
	
	
	public RecordController() {
		
	}
	
	public RecordController(Stage parentStage, Model model, HealthRecord record) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.record = record;
		
	}
	
	@FXML
	public void initialize() {
		User user = model.getCurrentUser();
		
		date.setText(record.getDate().toString());
		username.setText(user.getUsername());
		fullname.setText(user.getLastname() + " " + user.getFirstname());
		weight.setText(String.valueOf(record.getWeight()));
		temperature.setText(String.valueOf(record.getTemperature()));
		bloodPressure.setText(String.valueOf(record.getBloodPressure()));
		note.setText(record.getNote());
		
		//DOWNLOAD 1 FILE
		download.setOnAction(event -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Record: " + record.getRecord_number());
			fileChooser.setInitialFileName(record.getRecord_number() + ".txt");
			fileChooser.getExtensionFilters().add(
					new FileChooser.ExtensionFilter("Text Files", "*.txt"));
			
			File file = fileChooser.showSaveDialog(stage);
			SaveRecordToFile(file);
		});
		
		//EDIT FILE
		edit.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecordUpdateView.fxml"));
				RecordUpdateController recordUpdateController = new RecordUpdateController(stage, model, record);
				
				loader.setController(recordUpdateController);
				Pane root = loader.load();
				
				recordUpdateController.showStage(root);
			} catch (IOException e) {
				message.setText(e.getMessage());
				message.setTextFill(Color.RED);
			}
			
			
		});
		
		// GO BACK TO DASHBOARD
		goBack.setOnAction(e -> {
			stage.close();
			parentStage.show();
		});
		
		
	}
	
	private void SaveRecordToFile(File file) {
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
			writer.newLine();
			writer.write("Record Number : " + record.getRecord_number());
			writer.newLine();
			writer.write("Date:         : " + record.getDate().toString());
			writer.newLine();
			writer.write("Weight        : " + record.getWeight());
			writer.newLine();
			writer.write("Temperature   : " + record.getTemperature());
			writer.newLine();
			writer.write("Blood pressure: " + record.getBloodPressure());
			writer.newLine();
			writer.write("Note          : " + record.getNote());
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			message.setText(e.getMessage());
			message.setTextFill(Color.RED);
		}
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 500);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Record: " + record.getRecord_number());
		stage.show();
	}
}
