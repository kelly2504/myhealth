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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.HealthRecord;
import model.Model;
import model.User;

public class RecordUpdateController {
	private Stage stage;
	private Model model;
	private Stage parentStage;
	private HealthRecord record;
	
	@FXML
	private Text username;
	@FXML
	private Text fullname;
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
	private Label date;
	@FXML
	private Button save;
	@FXML
	private Button goBack;
	@FXML
	private Label message;
	
	public RecordUpdateController() {
		
	}
	
	public RecordUpdateController(Stage parentStage, Model model, HealthRecord record) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.record = record;
	}
	
	@FXML
	public void initialize() {
		
		User user = model.getCurrentUser();
		//set date and prompt texts
		date.setText(record.getDate().toString());
		
		username.setText(user.getUsername());
		fullname.setText(user.getLastname() + " " + user.getFirstname());
		
		weight.setPromptText(String.valueOf(record.getWeight()));
		temperature.setPromptText(String.valueOf(record.getTemperature()));
		//bloodPressure.setPromptText(String.valueOf(record.getBloodPressure()));
		systolic.setPromptText(String.valueOf(record.getSystolic()));
		diastolic.setPromptText(String.valueOf(record.getDiastolic()));
		note.setPromptText(record.getNote());
		
		//save new record details -> send them to RecordDao -> SQL updateDetails
		save.setOnAction(event -> {
			message.setText("save button clicked");
			double tempWeight = record.getWeight();
			double tempTemperature = record.getTemperature();
			int tempSystolic = record.getSystolic();
			int tempDiastolic = record.getDiastolic();
			//double tempBloodPressure = record.getBloodPressure();
			String tempNote = record.getNote();
			
			//TODO - creaate new class with AddRecord that allows for value checking - to lower code duplication
			// or double format checker ?
			
			//if any field has value in it
			if (!weight.getText().isEmpty() && !temperature.getText().isEmpty() && !systolic.getText().isEmpty() && !diastolic.getText().isEmpty() && !note.getText().isEmpty()) {
				if (!weight.getText().isEmpty()) {
					try {
						tempWeight = Double.parseDouble(weight.getText());
					} catch (NumberFormatException e) {
						message.setText(e.getMessage());
						message.setTextFill(Color.RED);
						return;
					}
				}
				
				if (!temperature.getText().isEmpty()) {
					try {
						tempTemperature = Double.parseDouble(temperature.getText());
					} catch (NumberFormatException e) {
						message.setText(e.getMessage());
						message.setTextFill(Color.RED);
						return;
					}
				}
				
//				if (!bloodPressure.getText().isEmpty()) {
//					try {
//						tempBloodPressure = Double.parseDouble(bloodPressure.getText());
//					} catch (NumberFormatException e) {
//						message.setText(e.getMessage());
//						message.setTextFill(Color.RED);
//					}
//				}
				
				if (!systolic.getText().isEmpty() && !diastolic.getText().isEmpty()) {
					try {
						tempSystolic = Integer.parseInt(systolic.getText());
						tempDiastolic = Integer.parseInt(diastolic.getText());
					} catch (NumberFormatException e) {
						message.setText(e.getMessage());
						message.setTextFill(Color.RED);
					}
				} else if (!systolic.getText().isEmpty() || !diastolic.getText().isEmpty()) {
					message.setText("Please fill both systolic and diastolic values for blood pressure");
					message.setTextFill(Color.RED);
					return;
				}
				
				//TODO Make a note checker - to see if 50+ words are being entered
				if (!note.getText().isEmpty()) {
					tempNote = note.getText();
				}
			} else {
				message.setText("No value changed. Please enter a value to update details");
				message.setTextFill(Color.RED);
			}
			
			//TODO - ADD SQL For updating records
			try {
				model.getRecordDao().updateDetails(record.getRecord_number(), tempWeight, tempTemperature, tempSystolic, tempDiastolic, tempNote);
				
				record.setWeight(tempWeight);
				record.setTemperature(tempTemperature);
//				record.setBloodPressure(tempBloodPressure);
				record.setSystolic(tempSystolic);
				record.setDiastolic(tempDiastolic);
				record.setNote(tempNote);
				message.setText("Record updated!");
				message.setTextFill(Color.GREEN);
			} catch (SQLException e) {
				message.setText(e.getMessage());
				message.setTextFill(Color.RED);
			}
			
		});
		
		//brings back to view record
		goBack.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 500);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Record Update: " + record.getRecord_number());
		stage.show();
	}
}
