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
import model.RecordList;
import model.User;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

public class RecordTableController {
	private Stage stage;
	private Model model;
	
	private final List<HealthRecord> selectedRecords = new ArrayList<>();
	@FXML
	private TableView<HealthRecord> recordsTable;
	@FXML
	private TableColumn<HealthRecord, Void> selectRecord;
	@FXML
	private TableColumn<HealthRecord, String> recordNumber;
	@FXML
	private TableColumn<HealthRecord, String> recordDate;
	@FXML
	private TableColumn<HealthRecord, String> recordNote;
	@FXML
	private TableColumn<HealthRecord, Void> viewRecord;
	@FXML
	private TableColumn<HealthRecord, Void> deleteRecord;
	private boolean confirmDelete = false;
	
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
		setUpColumns();
		
		viewRecord();
			
		deleteRecord();		
	}
	
	//checkbox to download record
	private void selectRecord() {
		selectRecord.setCellFactory(col -> new TableCell<HealthRecord, Void>(){
			private final CheckBox checkBox = new CheckBox(); {
				checkBox.setOnAction(event -> {
					HealthRecord record = getTableRow().getItem();
					if (record != null) {
						if (checkBox.isSelected()) {
							if (!selectedRecords.contains(record)) {
								selectedRecords.add(record);
							}
						} else {
							selectedRecords.remove(record);
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
	                checkBox.setSelected(selectedRecords.contains(getTableRow().getItem()));
	                setGraphic(checkBox); 
	            }
	        }
		});
		
	}
	//TODO - REPLACE ALL PRINT OUT TO A MESSAGE
	//button to view record
	private void viewRecord() {
		viewRecord.setCellFactory(col -> new TableCell<HealthRecord, Void>() {
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
		
		deleteRecord.setCellFactory(col -> new TableCell<HealthRecord, Void>(){
			private final Button btn = new Button("Delete");
			{	
				btn.setOnAction(event -> {
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DeleteConfirmationView.fxml"));
						DeleteConfirmationController dCC = new DeleteConfirmationController(stage);
						loader.setController(dCC);
						VBox root = loader.load();
						confirmDelete = dCC.showAndWait(root);
					} catch (IOException e) {
						System.err.println(e.getMessage());
					}
					
					if (confirmDelete) {
						HealthRecord record = getTableRow().getItem();
						
						if (record != null) {
							try {
								model.getRecordDao()
								.deleteRecord(record.getRecord_number(), record.getUser());
								
								model.getCurrentUser().getRecords().removeRecord(record);
								//TODO - deletion verification
						
								
							} catch (SQLException e) {
								System.err.println("Delete failed: " + e.getMessage());
							}
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
	
	@FXML
    public void onSelectAll() {
        selectedRecords.clear();
        selectedRecords.addAll(recordsTable.getItems());
        recordsTable.refresh(); // refresh to update checkbox states
//        System.out.println("All records selected: " + selectedRecords.size());
    }

    @FXML
    public void onClearSelection() {
        selectedRecords.clear();
        recordsTable.refresh(); // refresh to uncheck all checkboxes
//        System.out.println("Selection cleared");
    }
	
	private void setUpColumns() {
		recordNumber.setCellValueFactory(new PropertyValueFactory<>("record_number"));
		recordDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		recordNote.setCellValueFactory(new PropertyValueFactory<>("note"));	
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
		    RecordList fetchedList = model.getRecordDao().viewRecords(user.getUsername());
	    
		    user.setRecords(fetchedList);
		    
		    recordsTable.setItems(user.getRecords().getObservableList());
			       
		} catch (NullPointerException e) {
			System.err.println(e);
		}
	}
	
	public List<HealthRecord> getSelectedRecords() {
		return selectedRecords;
	}
}
