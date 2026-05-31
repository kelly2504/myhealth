package tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import Utils.PasswordFormatChecker;

public class PasswordTest {
	PasswordFormatChecker checker;
	
	//password format checks
	@Before
	public void setup() {
		checker = new PasswordFormatChecker();
	}
	
	//good password
	@Test
	public void validPasswordTest() {
		assertEquals("Good", checker.checkPassword("Password123&"));
	}
	
	//password with no capital letter 
	@Test
	public void noCapitalLetterTest() {
		assertNotEquals("Good", checker.checkPassword("password123&"));
	}
	
	//password no number
	@Test
	public void noDigitTest() {
		assertNotEquals("Good", checker.checkPassword("Password&"));
	}
	
	//password no special character
	@Test
	public void noSpecialCharacter() {
		assertNotEquals("Good", checker.checkPassword("Password12345"));
	}
}
