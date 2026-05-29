package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecordNumberGenerator {
	//singleton that allows the next record number to be generated and stay consistent through different user
	private static RecordNumberGenerator instance;
	private int counter;
	
	private RecordNumberGenerator(int startFrom) {
		this.counter = startFrom;
	}
	
	public static RecordNumberGenerator getInstance(Connection connection) throws SQLException {
		if (instance == null) {
			int max = fetchMaxRecordNumber(connection);
			instance = new RecordNumberGenerator(max + 1);
		}
		return instance;
	}
	
	 public synchronized int next(){
		 return counter++;
	 }
	 
	 //return next formatted record string 
	 public synchronized String nextFormatted() {
		 // Format - RN-0000 
		 return String.format("RN-%04d", counter++);
	 }
	
	private static int fetchMaxRecordNumber(Connection connection) throws SQLException {
		String sql = "SELECT COALESCE(MAX(CAST(SUBSTR(recordNumber, 4) AS INTEGER)), 0) FROM records";
		try (PreparedStatement stmt = connection.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			rs.next();
			return rs.getInt(1);
		}
	}
}
