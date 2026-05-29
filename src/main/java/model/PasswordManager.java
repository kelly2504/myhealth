package model;

import org.mindrot.jbcrypt.*;

public class PasswordManager {
	private static final int SALT = 12;
	
	public static String hashPassword(String plainPassword) {
		return BCrypt.hashpw(plainPassword, BCrypt.gensalt(SALT));
	}
	
	public static boolean verifyPassword(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);
	}
}
