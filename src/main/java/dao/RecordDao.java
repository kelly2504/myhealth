package dao;

import java.sql.SQLException;

import model.HealthRecord;
import model.Record_List;


public interface RecordDao {
	 void setup() throws SQLException;
	 HealthRecord addRecord(String username, double weight, double temperature, double bloodPressure, String note) throws SQLException;
	 void deleteRecord(String recordNumber, String username) throws SQLException;
	 public Record_List viewRecords(String username) throws SQLException;
}
