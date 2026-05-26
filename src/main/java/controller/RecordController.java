package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
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
	private Text blood_pressure;
	@FXML
	private Text note;
	@FXML
	private Button download;
	@FXML
	private Button go_back;
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
		blood_pressure.setText(String.valueOf(record.getBlood_pressure()));
		note.setText(record.getNote());
		
		// TODO : IMPLEMENT DOWNLOAD
		
		// TODO : IMPLEMENT GOBACK
		go_back.setOnAction(e -> {
			stage.close();
			parentStage.show();
		});
		
		
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 500);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Record: " + record.getRecord_number());
		stage.show();
	}
}
