package model;

import java.util.ArrayList;

//stores the record that belongs specifically to a certain user
public class Record_List {
	ArrayList<HealthRecord> records;
	
	public Record_List() {
		records = new ArrayList<>();
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
		return records.getLast();
	}
	
	public int get_length() {
		return records.size();
	}
	
	//TODO -- RECORDS SHOULD BE CHECKED AS PER THE CURRENT USERNAME - IF USERNAME DOESNT MATCH TO THE ONE BEING INSERTED IN THE 
	//LIST - A MESSAGE SHOULD BE PRINTED TO CHECK THAT A RECORD FROM THE CORRECT USER IS BEING INSERTED. 
}
