package model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Comparator;

//stores the record that belongs specifically to a certain user
public class Record_List {
//	ArrayList<HealthRecord> records;
	private final ObservableList<HealthRecord> records = FXCollections.observableArrayList();
	
	
	public Record_List() {
	}
	
	public void add_record(HealthRecord record) {
		records.add(record);
	}
	
	public boolean remove_record(HealthRecord record) {
		if (records.contains(record)) {
			records.remove(record);
			return true;
		}
		
		return false;
	}
	
	public HealthRecord get_latest_record() {
		//TODO IMPLEMENT STREAM TO SORT/COMPARE THROUGH THE MAP AND GET THE LASTEST RECORD BASED ON DATE
//		return records.getLast();
		//returns null if list is empty
		return records.stream()
				.max(Comparator.comparing(HealthRecord::getDate))
				.orElse(null);
	}
	
	public int get_length() {
		return records.size();
	}
	
	public HealthRecord get_record_at_index(int index) {
		return records.get(index);
	}
	
	public ObservableList<HealthRecord> getObservableList(){
		return records;
	}
	
	//TODO -- RECORDS SHOULD BE CHECKED AS PER THE CURRENT USERNAME - IF USERNAME DOESNT MATCH TO THE ONE BEING INSERTED IN THE 
	//LIST - A MESSAGE SHOULD BE PRINTED TO CHECK THAT A RECORD FROM THE CORRECT USER IS BEING INSERTED. 
}
