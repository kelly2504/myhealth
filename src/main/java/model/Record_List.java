package model;

import java.util.ArrayList;

public class Record_List {
	ArrayList<Record> records = new ArrayList<>();
	
	public void add_record(Record record) {
		records.add(record);
	}
	
	public boolean remove_record(Record record) {
		if (records.contains(record)) {
			records.remove(record);
			return true;
		}
		
		return false;
	}
	
	public int get_length() {
		return records.size();
	}
}
