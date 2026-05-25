package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.HealthRecord;
import model.Model;
import model.Record_List;
import model.User;
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
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	@FXML
	public void initialize() {
		
		
		System.out.println("Setting up table columns");
		setUpColumns();
		System.out.println("Set up table columns");
		
		view_record.setCellFactory(col -> new TableCell<HealthRecord, Void>() {
			private final Button btn = new Button("View");
			{
				btn.setOnAction(e->{
					//TODO: IMPLEMENT VIEW 
					System.out.println("View btn Clicked!");
				});
			}
			
			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				setGraphic(empty ? null : btn);
			}
		});
		
		delete_record.setCellFactory(col -> new TableCell<HealthRecord, Void>(){
			private final Button btn = new Button("Delete");
			{
				btn.setOnAction(e -> {
//					//TODO: IMPLEMENT DELETE
//					System.out.println("Delete btn Clicked!");
					
					HealthRecord record = getTableRow().getItem();
					
					if (record != null) {
						try {
							model.getRecordDao()
							.deleteRecord(record.getRecord_number(), record.getUser());
							
							model.getCurrentUser().getRecords().remove_record(record);
							
							System.out.println("Deleted: " + record.getRecord_number());
						} catch (SQLException ex) {
							System.err.println("Delete failed: " + ex.getMessage());
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
	
	@SuppressWarnings("unchecked")
	private void setUpColumns() {
		record_number.setCellValueFactory(new PropertyValueFactory<>("record_number"));
		record_date.setCellValueFactory(new PropertyValueFactory<>("date"));
		record_note.setCellValueFactory(new PropertyValueFactory<>("note"));
		
		
	}
	
	public void loadCurrentUser() {
        User user = model.getCurrentUser();
        try {
            loadUserRecords();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
	
	
	
	public void loadUserRecords() throws SQLException {
		User user = model.getCurrentUser();
		try {
//			System.out.println("checkpoint 1");
//			Record_List fetchedList = model.getRecordDao().viewRecords(user.getUsername());
//			if (fetchedList != null) {
//				user.setRecords(fetchedList);
//				System.out.println("checkpoint 2");
//				// Bind the user's list to the table
//				if (user.getRecords() != null) {
//					System.out.println("checkpoint 3");
//					records_table.setItems(user.getRecords().getObservableList());
//					System.out.println("checkpoint 4");
//				} else {
//					System.err.println("no records for user " + user.getUsername());
//				}
//		        
//			}
			
			System.out.println("Loading records for: " + user.getUsername());

		    Record_List fetchedList = model.getRecordDao().viewRecords(user.getUsername());
		    System.out.println("flag");
		    
		    
		    user.setRecords(fetchedList);
		    
		    System.out.println("Setting list");
		    records_table.setItems(user.getRecords().getObservableList());
		    
		    System.out.println("List set");

		    System.out.println("Table loaded with " + fetchedList.get_length() + " records");
			       
		} catch (NullPointerException e) {
			System.err.println(e);
		}
	}
}
