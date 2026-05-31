package controller;

import java.sql.SQLException;

import Utils.RecordValuesChecker;
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
import model.RecordList;
import model.User;

public class AddRecordController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	
	@FXML
	private TextField weight;
	@FXML
	private TextField temperature;
//	@FXML
//	private TextField bloodPressure;
	@FXML
	private TextField systolic;
	@FXML
	private TextField diastolic;
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
	
	double tempWeight = 0.0;
	double tempTemperature = 0.0;
	int tempSystolic = 0;
	int tempDiastolic = 0;
	String tempNote = "";
	
	@FXML
	public void initialize() {
		User user = model.getCurrentUser();
				
		submit.setOnAction(event -> {				
			
//			//default values based on previous 
//			if (!user.getRecords().getObservableList().isEmpty()) {
//				HealthRecord last_record = user.getRecords().getLatestRecord();
//				tempWeight = last_record.getWeight();
//				tempTemperature = last_record.getTemperature();
//				tempBloodPressure = last_record.getBloodPressure();
//				tempSystolic = last_record.getSystolic();
//				tempDiastolic = last_record.getDiastolic();
//				tempNote = last_record.getNote();
//			}
			
			//reads the textfields and checks if they are correct values;
			String valuesErrorMsg = getTextFieldValues();
			if (!valuesErrorMsg.equals("")) {
				showError(valuesErrorMsg);
				return;
			}
			
			HealthRecord record;
			//get recordDao to create new record then add it to the record list of the user.
			try {
				record = model.getRecordDao().addRecord(user.getUsername(), tempWeight, tempTemperature, tempSystolic, tempDiastolic, tempNote);
				
				if (record != null) {
					//add record to user's list
					if (user.getRecords() == null) {
						RecordList record_list = new RecordList();
						record_list.addRecord(record);
						user.setRecords(record_list);
					} else {
						user.getRecords().addRecord(record);
					}
					
					
					message.setText("Your record has been added successfully!");
					message.setTextFill(Color.GREEN);
					
				} else {
					showError("Could not add record");
				}
				
			} catch (SQLException e ) {
				showError(e.getMessage());
			}
				
		});
		
		cancel.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});
	}
	
	
	public String getTextFieldValues() {
		
		//check if all the textfields are empty - message at least one of the text fields have to change
		if (!weight.getText().isEmpty() || !temperature.getText().isEmpty() || (!systolic.getText().isEmpty() && !diastolic.getText().isEmpty() ) || !note.getText().isEmpty()) {
			//TODO: ADD PROPER VALIDATION FOR INPUTS 
			if (!weight.getText().isEmpty()) {
				try {
					tempWeight = Double.parseDouble(weight.getText());
				} catch (NumberFormatException e) {
					
					return "Weight should be a valid number";
				}	
			}
			
			if (!temperature.getText().isEmpty()) {
				try {
					tempTemperature = Double.parseDouble(temperature.getText());
				} catch (NumberFormatException e){
					return "Temperature should be a valid number";
				}
				
			}
			
			if (!systolic.getText().isEmpty() && !diastolic.getText().isEmpty()) {
				try {
					tempSystolic = Integer.parseInt(systolic.getText());
					tempDiastolic = Integer.parseInt(diastolic.getText());
					
				} catch (NumberFormatException e) {
					return "Blood pressure should have valid values";
				}
				
					
			} else if (!systolic.getText().isEmpty() || !diastolic.getText().isEmpty()) {
				return "Blood pressure should have both values filled in";
			}
			
			if (!note.getText().isEmpty()) {
				tempNote = note.getText();	
			}
			
			//if values filled in properly, check if the values are correctly formatted
			RecordValuesChecker valuesChecker = new RecordValuesChecker();
			String msg = valuesChecker.checkRecords(tempWeight, tempTemperature, tempSystolic, tempDiastolic, tempNote);
			
			if (!msg.equals("Good")) {
				return msg;
			}
				
		} else {
			return "At least one record metric should be entered";
		}
		
		return "";
	}
	
	
	
	public void showError(String msg) {
		message.setText(msg);
		message.setTextFill(Color.RED);
	}
	
	
	
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 500);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("New Record");
		stage.show();
	}
}
