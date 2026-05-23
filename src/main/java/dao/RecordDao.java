package dao;

import java.sql.SQLException;

import model.HealthRecord;


public interface RecordDao {
	 void setup() throws SQLException;
	 HealthRecord addRecord(String username, double weight, double temperature, double blood_pressure, String note) throws SQLException;
	 void deleteRecord(String recordNumber, String username) throws SQLException;
}
