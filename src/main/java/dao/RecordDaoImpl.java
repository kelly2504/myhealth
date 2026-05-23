package dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import model.HealthRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RecordDaoImpl implements RecordDao {
	private final String TABLE_NAME = "records";
	
	public RecordDaoImpl() {
	}

	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();) {
			// TODO: IMPLEMENT ACTUAL SQL TO CREATE RECORD TABLE
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (recordNumber VARCHAR(7) NOT NULL," + " username VARCHAR(10) NOT NULL," +  " date DATETIME NOT NULL," +
			" weight DECIMAL," + " temperature DECIMAL," + " bloodpressure DECIMAL," + "note VARCHAR(50), " + "PRIMARY KEY (recordNumber))";
			stmt.executeUpdate(sql);
		} 

	}
	
	@Override
	public HealthRecord addRecord(String username, double weight, double temperature, double blood_pressure, String note) throws SQLException{
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			
			//TODO: add recordNumber from recordNumberGenerator
			String recordNumber = RecordNumberGenerator.getInstance(connection).nextFormatted();
			
			LocalDate date = LocalDate.now();
			
			stmt.setString(1, recordNumber);
			stmt.setString(2, username);
			stmt.setDate(3, java.sql.Date.valueOf(date));
			stmt.setDouble(4, weight);
			stmt.setDouble(5, temperature);
			stmt.setDouble(6, blood_pressure);
			stmt.setString(7, note);
			
			stmt.executeUpdate();
			return new HealthRecord(recordNumber, username, date, weight, temperature, blood_pressure, note);
			
		}	
	}
	@Override
	public void deleteRecord(String recordNumber, String username) throws SQLException{
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE recordNumber = ? AND username = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
				stmt.setString(1, recordNumber);
				stmt.setString(2, username);
				
				stmt.executeUpdate(sql);
		}
				
	}
	
	public void updateDetails() {
		
	}


}
