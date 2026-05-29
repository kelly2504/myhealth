package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Utils.FileManager;
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
		bloodPressure.setText(String.valueOf(record.getSystolic()) + "/" + String.valueOf(record.getDiastolic()));
		note.setText(record.getNote());
		
		//DOWNLOAD 1 FILE
		download.setOnAction(event -> {
			FileManager fileManager = new FileManager(stage, model);
			try {
				fileManager.SaveRecordToFile(record);
				message.setText("Your file has been successfully downloaded");
				message.setTextFill(Color.GREEN);
			} catch (NullPointerException e){
				message.setText(e.getMessage());
				message.setTextFill(Color.RED);
			}
			
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
				System.err.println(e.getMessage());
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
	
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 500);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Record: " + record.getRecord_number());
		stage.show();
	}
}
