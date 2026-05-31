package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Model;
import model.User;

public class UserTest {
	Model model;
	//user uniqueness
	@Before
	public void setup() {
		model = new Model();
		try {
			model.setup();
		} catch (SQLException e) {
			System.err.println("Error: Could not set up model");
		}
	}
	
	
	//user names should be unique
	@Test (expected = SQLException.class)
	public void usernameUniquenessTest() throws SQLException{
		//create first user
		User user1 = model.getUserDao().createUser("Lily", "Lily", "Rose", "Password345&");
		//creating a second user will result in an SQLException
		User user2 = model.getUserDao().createUser("Lily", "Lily", "Rose", "Password345&");
		
	}
	
	@Test
	public void usernameDifferentTest() throws SQLException {
		User user1 = model.getUserDao().createUser("Lily", "Lily", "Rose", "Password345*");
		//Creating a second user should NOT result in an SQLException
		User user2 = model.getUserDao().createUser("Lucas", "Lucas", "Freyar", "Pudding123&");
		
		assertNotNull("User 1 should not be null", user1);
		assertNotNull("User 2 should not be null", user2);
	}

	
	//password encryption test 
	@Test
	public void loginSuccess() throws SQLException {
		//create user on signup
		User user1 = model.getUserDao().createUser("Lily", "Lily", "Rose", "Password123*");
		
		//get the user on login and check if the user is received correctly
		assertNotNull("User should not be null", model.getUserDao().getUser("Lily", "Password123*"));
		
//		User currentUser = model.getUserDao().getUser("Lily", "Password123*");
	}
	
	@Test 
	public void loginFailure() throws SQLException {
		User user1 = model.getUserDao().createUser("Lily", "Lily", "Rose", "Password123*");
		
//		User currentUser = model.getUserDao().getUser("Lily", "Pudding123*");
		assertNull("User should not be null", model.getUserDao().getUser("Lily", "Pudding123*"));
		
	}
	
	@After 
	public void teardown() throws SQLException{
		model.getUserDao().deleteUser("Lily");
		
		//will not delete if does not exist
		model.getUserDao().deleteUser("Lucas");
	}
	
}
