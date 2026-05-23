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
	private TextField blood_pressure;
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
			
			double temp_weight = 0.0;
			double temp_temperature = 0.0;
			double temp_blood_pressure = 0.0;
			String temp_note = "";
			
			
			//default values based on previous 
		
			if (user.getRecords() != null) {
				HealthRecord last_record = user.getRecords().get_latest_record();
				temp_weight = last_record.getWeight();
				temp_temperature = last_record.getTemperature();
				temp_blood_pressure = last_record.getBlood_pressure();
				temp_note = last_record.getNote();
			}
			
			//check if all the textfields are empty - message at least one of the text fields have to change
			if (!weight.getText().isEmpty() && !temperature.getText().isEmpty() && !blood_pressure.getText().isEmpty()) {
				if (!weight.getText().isEmpty()) {
					temp_weight = Double.parseDouble(weight.getText());
				}
				
				if (!temperature.getText().isEmpty()) {
					temp_temperature = Double.parseDouble(temperature.getText());
				}
				
				if (!blood_pressure.getText().isEmpty()) {
					temp_blood_pressure = Double.parseDouble(blood_pressure.getText());
				}
				
				if (!note.getText().isEmpty()) {
					temp_note = note.getText();
				}
				
					
			} else {
				message.setText("At least one record metric should be entered");
				message.setTextFill(Color.RED);
				return;
			}
			
			HealthRecord record;
			//get recordDao to create new record then add it to the record list of the user.
			try {
				record = model.getRecordDao().addRecord(user.getUsername(), temp_weight, temp_temperature, temp_blood_pressure, temp_note);
				
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
	}
	
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 500);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("New Record");
		stage.show();
	}
}
