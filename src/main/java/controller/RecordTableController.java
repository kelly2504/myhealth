package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.HealthRecord;
import javafx.scene.control.Button;

public class RecordTableController {
	@FXML
	private TableView<HealthRecord> records_table;
	@FXML
	private TableColumn<HealthRecord, String> record_number;
	@FXML
	private TableColumn<HealthRecord, String> record_date;
	@FXML
	private TableColumn<HealthRecord, String> record_note;
	@FXML
	private TableColumn<HealthRecord, Void> view_record;
	@FXML
	private TableColumn<HealthRecord, Void> delete_record;
	
	
	@FXML
	public void initialize() {
		
		view_record.setCellFactory(col -> new TableCell<HealthRecord, Void>() {
			private final Button btn = new Button("View");
			{
				btn.setOnAction(e->{
					//TODO: IMPLEMENT VIEW 
					System.out.println("View btn Clicked!");
				});
			}
			
			@Override
			public void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				setGraphic(empty ? null : btn);
			}
		});
		
		delete_record.setCellFactory(col -> new TableCell<HealthRecord, Void>(){
			private final Button btn = new Button("Delete");
			{
				btn.setOnAction(e -> {
					//TODO: IMPLEMENT DELETE
					System.out.println("Delete btn Clicked!");
				});
				
				
				
			}
		});
	}
}
