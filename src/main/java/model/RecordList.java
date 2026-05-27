package model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Comparator;

//stores the record that belongs specifically to a certain user
public class RecordList {
//	ArrayList<HealthRecord> records;
	private final ObservableList<HealthRecord> records = FXCollections.observableArrayList();
	
	
	public RecordList() {
	}
	
	public void addRecord(HealthRecord record) {
		records.add(record);
	}
	
	public boolean removeRecord(HealthRecord record) {
		if (records.contains(record)) {
			records.remove(record);
			return true;
		}
		
		return false;
	}
	
	public HealthRecord getLatestRecord() {
		//TODO IMPLEMENT STREAM TO SORT/COMPARE THROUGH THE MAP AND GET THE LASTEST RECORD BASED ON DATE
//		return records.getLast();
		//returns null if list is empty
		return records.stream()
				.max(Comparator.comparing(HealthRecord::getDate))
				.orElse(null);
	}
	
	public int getLength() {
		return records.size();
	}
	
	public HealthRecord getRecordAtIndex(int index) {
		return records.get(index);
	}
	
	public ObservableList<HealthRecord> getObservableList(){
		return records;
	}
	
	//TODO -- RECORDS SHOULD BE CHECKED AS PER THE CURRENT USERNAME - IF USERNAME DOESNT MATCH TO THE ONE BEING INSERTED IN THE 
	//LIST - A MESSAGE SHOULD BE PRINTED TO CHECK THAT A RECORD FROM THE CORRECT USER IS BEING INSERTED. 
}
