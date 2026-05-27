package dao;

import java.sql.SQLException;

import model.HealthRecord;
import model.RecordList;


public interface RecordDao {
	 void setup() throws SQLException;
	 HealthRecord addRecord(String username, double weight, double temperature, double bloodPressure, String note) throws SQLException;
	 void deleteRecord(String recordNumber, String username) throws SQLException;
	 public RecordList viewRecords(String username) throws SQLException;
}
