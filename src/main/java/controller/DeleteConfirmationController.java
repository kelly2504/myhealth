package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DeleteConfirmationController {
	private Stage parentStage;
	private Stage stage;
	private Boolean confirmed = false;
	
	@FXML
	private Button yes;
	@FXML
	private Button no;
	
	public DeleteConfirmationController() {
		
	}
	public DeleteConfirmationController(Stage parentStage) {
		this.stage = new Stage();
		this.parentStage = parentStage;
	}
	
	@FXML
	public void initialize() {
		yes.setOnAction(event -> {
			confirmed = true;
			stage.close();
		});
		
		no.setOnAction(event -> {
			confirmed = false;
			stage.close();
		});
		
	}
	
	public boolean showAndWait(VBox root) {
		Scene scene = new Scene(root, 300, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Delete Confirmation");
		stage.showAndWait();
		
		return confirmed;
	}
}
