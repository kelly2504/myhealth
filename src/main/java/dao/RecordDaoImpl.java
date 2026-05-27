package dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import model.HealthRecord;
import model.RecordList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RecordDaoImpl implements RecordDao {
	private final String TABLE_NAME = "records";

	// each user has their own record list
	private final Map<String, RecordList> userRecordLists = new HashMap<>();

	public RecordDaoImpl() {
	}

	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection(); Statement stmt = connection.createStatement();) {
			// TODO: IMPLEMENT ACTUAL SQL TO CREATE RECORD TABLE
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (recordNumber VARCHAR(7) NOT NULL,"
					+ " username VARCHAR(10) NOT NULL," + " date DATETIME NOT NULL," + " weight DECIMAL,"
					+ " temperature DECIMAL," + " bloodpressure DECIMAL," + " note VARCHAR(250), "
					+ "PRIMARY KEY (recordNumber))";
			stmt.executeUpdate(sql);
		}

	}

	@Override
	public HealthRecord addRecord(String username, double weight, double temperature, double bloodPressure,
			String note) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {

			// TODO: add recordNumber from recordNumberGenerator
			String recordNumber = RecordNumberGenerator.getInstance(connection).nextFormatted();

			LocalDate date = LocalDate.now();

			stmt.setString(1, recordNumber);
			stmt.setString(2, username);
			stmt.setDate(3, java.sql.Date.valueOf(date));
			stmt.setDouble(4, weight);
			stmt.setDouble(5, temperature);
			stmt.setDouble(6, bloodPressure);
			stmt.setString(7, note);

			stmt.executeUpdate();
			return new HealthRecord(recordNumber, username, date, weight, temperature, bloodPressure, note);

		}
	}

	@Override
	public void deleteRecord(String recordNumber, String username) throws SQLException {
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE recordNumber = ? AND username = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, recordNumber);
			stmt.setString(2, username);

			stmt.executeUpdate();
		}

	}

	@Override
	public RecordList viewRecords(String username) throws SQLException {
		RecordList record_List = new RecordList();
//		System.out.println("View Records called");

		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? ORDER BY date DESC, recordNumber DESC";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, username);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					HealthRecord record = new HealthRecord();
					record.setRecord_number(rs.getString("recordNumber"));
					record.setUser(username);
					record.setDate(rs.getDate("date").toLocalDate());
					record.setWeight(rs.getDouble("weight"));
					record.setTemperature(rs.getDouble("temperature"));
					record.setBloodPressure(rs.getDouble("bloodpressure"));
					record.setNote(rs.getString("note"));

					// add to list of records
					//System.out.println(rs.getString("recordNumber") + " added!");
					record_List.addRecord(record);
				}

			}

		}
		
		return record_List;

	}

	@Override
	public void updateDetails(String recordNumber, double weight, double temperature, double bloodPressure, String note) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET weight = ?, temperature = ?, bloodpressure = ?, note = ? WHERE recordNumber = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setDouble(1, weight);
			stmt.setDouble(2, temperature);
			stmt.setDouble(3, bloodPressure);
			stmt.setString(4, note);
			stmt.setString(5, recordNumber);
			
			stmt.executeUpdate();	
		}
	}

}
