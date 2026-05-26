package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.HealthRecord;
import model.Model;
import model.Record_List;
import model.User;

public class AddRecordController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	
	@FXML
	private TextField weight;
	@FXML
	private TextField temperature;
	@FXML
	private TextField bloodPressure;
	@FXML
	private TextArea note;
	@FXML
	private Button submit;
	@FXML
	private Button cancel;
	@FXML
	private Label message;
	
	public AddRecordController() {
		
	}
	
	public AddRecordController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.model = model;
		this.parentStage = parentStage;
	}
	
	@FXML
	public void initialize() {
		User user = model.getCurrentUser();
				
		submit.setOnAction(event -> {
			
			double tempWeight = 0.0;
			double tempTemperature = 0.0;
			double tempBloodPressure = 0.0;
			String tempNote = "";
			
			
			//default values based on previous 
		
			if (user.getRecords() != null) {
				HealthRecord last_record = user.getRecords().get_latest_record();
				tempWeight = last_record.getWeight();
				tempTemperature = last_record.getTemperature();
				tempBloodPressure = last_record.getBlood_pressure();
				tempNote = last_record.getNote();
			}
			
			//check if all the textfields are empty - message at least one of the text fields have to change
			if (!weight.getText().isEmpty() && !temperature.getText().isEmpty() && !bloodPressure.getText().isEmpty() && !note.getText().isEmpty()) {
				//TODO: ADD PROPER VALIDATION FOR INPUTS 
				if (!weight.getText().isEmpty()) {
					try {
						tempWeight = Double.parseDouble(weight.getText());
					} catch (NumberFormatException e) {
						message.setText("Weight should be a valid number");
						message.setTextFill(Color.RED);
						return;
					}	
					
					if (tempWeight < 0 || tempWeight > 1000) {
						message.setText("Weight invalid");
						message.setTextFill(Color.RED);
						return;
					}
				}
				
				if (!temperature.getText().isEmpty()) {
					try {
						tempTemperature = Double.parseDouble(temperature.getText());
					} catch (NumberFormatException e){
						message.setText("Temperature should be a vaid number");
						message.setTextFill(Color.RED);
						return;
					}
					
					
					if (tempTemperature < 0 || tempTemperature > 50) {
						message.setText("Temperature Invalid");
						message.setTextFill(Color.RED);
						return;
					}
				}
				
				if (!bloodPressure.getText().isEmpty()) {
					try {
						tempBloodPressure = Double.parseDouble(bloodPressure.getText());
					} catch (NumberFormatException e) {
						message.setText("Blood pressure should be a valid number");
						message.setTextFill(Color.RED);
						return;
					}
					
					if (tempBloodPressure < 0 || tempBloodPressure > 300) {
						message.setText("Blood pressure invalid");
						message.setTextFill(Color.RED);
						return;
					}
					
				}
				
				if (!note.getText().isEmpty()) {
					//TODO: verify less 50 words
					
					tempNote = note.getText();
					
					String[] words = tempNote.trim().split(" ");
					if (words.length > 50) {
						message.setText("Note should be not be more than 50 words.");
						message.setTextFill(Color.RED);
					}	
					
				}
					
			} else {
				message.setText("At least one record metric should be entered");
				message.setTextFill(Color.RED);
				return;
			}
			
			HealthRecord record;
			//get recordDao to create new record then add it to the record list of the user.
			try {
				record = model.getRecordDao().addRecord(user.getUsername(), tempWeight, tempTemperature, tempBloodPressure, tempNote);
				
				if (record != null) {
					//add record to user's list
					if (user.getRecords() == null) {
						Record_List record_list = new Record_List();
						record_list.add_record(record);
						user.setRecords(record_list);
					} else {
						user.getRecords().add_record(record);
					}
					
					
					message.setText("Your record has been added successfully!");
					message.setTextFill(Color.GREEN);
					
				} else {
					message.setText("Could not add record");
					message.setTextFill(Color.RED);
				}
				
			} catch (SQLException e ) {
				System.err.println(e.getMessage());
				message.setText(e.getMessage());
				message.setTextFill(Color.RED);
			}
				
		});
		
		cancel.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});
	}
	
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 500);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("New Record");
		stage.show();
	}
}
