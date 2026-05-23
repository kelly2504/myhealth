package model;

import java.sql.SQLException;

import dao.UserDao;
import dao.UserDaoImpl;
import dao.RecordDaoImpl;
import dao.RecordDao;

public class Model {
	private UserDao userDao;
	private User currentUser; 
	private RecordDao recordDao;
	
	public Model() {
		userDao = new UserDaoImpl();
		recordDao = new RecordDaoImpl();
	}
	
	//sets up the database tables for both the user and records if they dont exist yet
	public void setup() throws SQLException {
		userDao.setup();
		recordDao.setup();
	}
	
	public UserDao getUserDao() {
		return userDao;
	}
	
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	public void setCurrentUser(User user) {
		currentUser = user;
	}
	
	//to create new records -- New records are then added in the record_List on the user's class side 
	public RecordDao getRecordDao() {
		return recordDao;
	}
	
	
}
