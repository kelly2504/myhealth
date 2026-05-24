package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.HealthRecord;
import model.Model;
import javafx.scene.control.Button;

public class RecordTableController {
	private Stage stage;
	private Model model;
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
	
	public RecordTableController() {
		
	}
	
	public RecordTableController(Stage stage, Model model) {
		this.stage = stage;
		this.model = model;
	}
	@FXML
	public void initialize() {
		
		setUpColumns();
		
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
	
	private void setUpColumns() {
		record_number.setCellValueFactory(new PropertyValueFactory<>("recordNumber"));
		record_date.setCellValueFactory(new PropertyValueFactory<>("date"));
		record_note.setCellValueFactory(new PropertyValueFactory<>("note"));
	}
}
