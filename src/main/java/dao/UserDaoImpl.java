package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;

public class UserDaoImpl implements UserDao {
	private final String TABLE_NAME = "users";

	public UserDaoImpl() {
	}

	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (username VARCHAR(10) NOT NULL," + "firstname VARCHAR(15) NOT NULL," + "lastname VARCHAR(15) NOT NULL,"
					+ "password VARCHAR(8) NOT NULL," + "PRIMARY KEY (username))";
			stmt.executeUpdate(sql);
		} 
	}

	@Override
	public User getUser(String username, String password) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
		try (Connection connection = Database.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setUsername(rs.getString("username"));
					user.setFirstname(rs.getString("firstname"));
					user.setLastname(rs.getString("lastname"));
					user.setPassword(rs.getString("password"));
					return user;
				}
				return null;
			} 
		}
	}

	@Override
	public User createUser(String username, String firstname, String lastname, String password) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?,?, ?, ?)";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, firstname);
			stmt.setString(3, lastname);
			stmt.setString(4, password);

			stmt.executeUpdate();
			return new User(username, firstname, lastname, password);
		} 
	}
	
	//when both change
	@Override
	public  void changeDetails(String username, String firstname, String lastname, String password) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET firstname = ?, lastname = ? WHERE username = ? AND password = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, firstname);
			stmt.setString(2, lastname);
			stmt.setString(3, username);
			stmt.setString(4, password);
			
			stmt.executeUpdate();
			
			
		}
	}
	
	//only first name
	@Override
	public  void changeDetails(String username, String firstname, String password) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET firstname = ? WHERE username = ? AND password = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, firstname);
			stmt.setString(2, username);
			stmt.setString(3, password);
			
			stmt.executeUpdate();
			
			
		}
	}

	//only last name
		@Override
		public  void changeDetails(String username, String lastname, String password, int i) throws SQLException {
			String sql = "UPDATE " + TABLE_NAME + " SET lastname = ? WHERE username = ? AND password = ?";
			try (Connection connection = Database.getConnection();
					PreparedStatement stmt = connection.prepareStatement(sql);) {
				stmt.setString(1, lastname);
				stmt.setString(2, username);
				stmt.setString(3, password);
				
				stmt.executeUpdate();
				
				
			}
		}
	
}
