package dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class RecordDaoImpl implements RecordDao {
	private final String TABLE_NAME = "records";

	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();) {
			// TODO: IMPLEMENT ACTUAL SQL TO CREATE RECORD TABLE
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (username VARCHAR(10) NOT NULL," + "PRIMARY KEY (username))";
			stmt.executeUpdate(sql);
		} 

	}

}
