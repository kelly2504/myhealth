package Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.HealthRecord;
import model.Model;
import model.User;

public class FileManager {
	private Stage stage;
	private Model model;
	
	public FileManager() {}
	
	public FileManager(Stage stage, Model model) {
		this.stage = stage;
		this.model = model;
	}
	
	public void SaveRecordToFile(List<HealthRecord> records) throws NullPointerException {
		File file = ChooseFile();
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
				writer.write("Blood pressure: " + record.getBloodPressure());
				writer.newLine();
				writer.write("Note          : " + record.getNote());
				writer.newLine();
				writer.newLine();
			}
			writer.write("=========================================================");
			writer.newLine();
            writer.write("End of Report");
            writer.newLine();
            writer.write("=========================================================");
            
            writer.close();
            
            showAlert(Alert.AlertType.INFORMATION,
                    "Download Complete",
                    records.size() + " record(s) saved to:\n" + file.getAbsolutePath());
			
		} catch (IOException e) {
			
			showAlert(Alert.AlertType.ERROR,
                    "Download Failed",
                    "Could not save records. Please try again.");
		} catch (NullPointerException e) {
			showAlert(Alert.AlertType.ERROR,
					"Download Failed",
                    e.getMessage());
		}
		
	}
	
	public void SaveRecordToFile(HealthRecord record) throws NullPointerException{
		File file = ChooseFile();
		
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
			writer.write("Blood pressure: " + record.getSystolic() + "/" + record.getDiastolic());
			writer.newLine();
			writer.write("Note          : " + record.getNote());
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR,
                    "Download Failed",
                    "Could not save record. Please try again.");
		}
				
	}
	
	private File ChooseFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Records");
		fileChooser.setInitialFileName("health_records.txt");
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		
		File file = fileChooser.showSaveDialog(stage);
		return file;
	}
	
	private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
