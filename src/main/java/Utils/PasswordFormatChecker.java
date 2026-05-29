package Utils;

import javafx.scene.paint.Color;

public class PasswordFormatChecker {
	public String checkPassword(String password) {
		/*
		 * A password should : 
		 * 	1. Be at least 8 characters long
		 *  2. include letters, numbers and at least one character and 
		 *   one upper letter 
		 *   */
		
		boolean hasSpecial = false;
		boolean hasDigit = false;
		boolean hasUpper = false;
		boolean hasAlpha = false;
		
		String message = "Good";
		if (password.length() < 8)  { 
			message = "Invalid Password. Password needs to contain at least 8 characters!";
			return message;
		}
			
		// password validation
		for (int i = 0; i < password.length(); ++i) {
			if (Character.isDigit(password.charAt(i)) ) {
				hasDigit = true;
			} else if (Character.isLetter(password.charAt(i))) {
				hasAlpha = true;
				if (Character.isUpperCase(password.charAt(i))) {
					hasUpper = true;
				}
				//already filtered digits and characters - else special char or whitespace
			} else if (!Character.isWhitespace(password.charAt(i))) {
					hasSpecial = true;
			}
		}
			
		// actually apply the validations made
		if (!(hasSpecial && hasDigit && hasUpper && hasAlpha)) {
			message = "\tInvalid Password.\n Your password should contain letters, digits, an uppercase letter and at least one special character";
			return message;
		}
		
		return message;
	}
}
