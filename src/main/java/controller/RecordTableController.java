package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.HealthRecord;
import model.Model;
import model.Record_List;
import model.User;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class RecordTableController {
	private Stage stage;
	private Model model;
	
	private final List<HealthRecord> selected_records = new ArrayList<>();
	@FXML
	private TableView<HealthRecord> records_table;
	@FXML
	private TableColumn<HealthRecord, Void> select_record;
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
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	@FXML
	public void initialize() {

		selectRecord();
		
		System.out.println("Setting up table columns");
		setUpColumns();
		System.out.println("Set up table columns");
		
		viewRecord();
			
		deleteRecord();		
	}
	
	//checkbox to download record
	private void selectRecord() {
		select_record.setCellFactory(col -> new TableCell<HealthRecord, Void>(){
			private final CheckBox checkBox = new CheckBox(); {
				checkBox.setOnAction(event -> {
					HealthRecord record = getTableRow().getItem();
					if (record != null) {
						if (checkBox.isSelected()) {
							if (!selected_records.contains(record)) {
								selected_records.add(record);
							}
						} else {
							selected_records.remove(record);
						}
					}
				});
			}
			
			@Override
	        protected void updateItem(Void item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || getTableRow().getItem() == null) {
	                setGraphic(null); 
	            } else {
	                // Keep checkbox in sync with selected_records
	                checkBox.setSelected(selected_records.contains(getTableRow().getItem()));
	                setGraphic(checkBox); 
	            }
	        }
		});
		
	}
	
	//button to view record
	private void viewRecord() {
		view_record.setCellFactory(col -> new TableCell<HealthRecord, Void>() {
			private final Button btn = new Button("View");
			{
				btn.setOnAction(event ->{
//					//TODO: IMPLEMENT VIEW 
//					System.out.println("View btn Clicked!");
					HealthRecord record = getTableRow().getItem();
					
					if (record != null) {
						try {
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecordView.fxml"));
							
							RecordController recordController = new RecordController(stage, model, record);
							loader.setController(recordController);
							Pane root = loader.load();
							recordController.showStage(root);
							
								
						} catch (IOException e) {
							System.err.println(e.getMessage());
						}	
					}
					
				});
			}
			
			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				setGraphic(empty ? null : btn);
			}
		});
	}
	
	//button to delete record
	private void deleteRecord() {
		delete_record.setCellFactory(col -> new TableCell<HealthRecord, Void>(){
			private final Button btn = new Button("Delete");
			{
				btn.setOnAction(event -> {
					
					HealthRecord record = getTableRow().getItem();
					
					if (record != null) {
						try {
							model.getRecordDao()
							.deleteRecord(record.getRecord_number(), record.getUser());
							
							model.getCurrentUser().getRecords().remove_record(record);
							
							
						} catch (SQLException e) {
							System.err.println("Delete failed: " + e.getMessage());
						}
					}
				});		
			}
			
			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				setGraphic(empty ? null : btn);
			}
		});
	}
	
	private void setUpColumns() {
		record_number.setCellValueFactory(new PropertyValueFactory<>("record_number"));
		record_date.setCellValueFactory(new PropertyValueFactory<>("date"));
		record_note.setCellValueFactory(new PropertyValueFactory<>("note"));	
	}
	
	public void loadCurrentUser() {
        try {
            loadUserRecords();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
	
	public void loadUserRecords() throws SQLException {
		User user = model.getCurrentUser();
		try {
		    Record_List fetchedList = model.getRecordDao().viewRecords(user.getUsername());
	    
		    user.setRecords(fetchedList);
		    
		    records_table.setItems(user.getRecords().getObservableList());
			       
		} catch (NullPointerException e) {
			System.err.println(e);
		}
	}
	
	public List<HealthRecord> getSelectedRecords() {
		return selected_records;
	}
}
