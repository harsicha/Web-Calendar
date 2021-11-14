package com.harsicha.webprojects.Services;

import com.harsicha.webprojects.Models.*;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import org.apache.commons.codec.*;
import org.apache.commons.codec.digest.DigestUtils;

public class LoginService {
	/**
	 * Creates a database entry for username and a hash of username & password.
	 * 
	 * @param username String containing username
	 * @param password String containing this user's password
	 * 
	 * @author harsicha
	 */
	public User createUser(String username, String password) {
		User user = new User();
		if (!checkIfUserExists(username, password)) {
			try (Connection con = DBCPDataSource.getConnection()) {
				String passToSHA256 = DigestUtils.sha256Hex(username + password);
				PreparedStatement ps = con.prepareStatement("INSERT INTO users VALUES(?, ?)");
				ps.setString(1, username);
				ps.setString(2, passToSHA256);
				ps.executeUpdate();
			}
			catch (SQLException e) {
				System.out.println("Exception at LoginService: checkIfUserExists method for " + username + ": ");
				e.printStackTrace();
				return null;
			}
		}
		else if (!authenticate(username, password)) return null;
		user.setUsername(username);
		return user;
	}
	
	/**
	 * Checks if there is already an entry present in the database or not.
	 * 
	 * @param username String containing username
	 * @param password String containing this user's password
	 * 
	 * @author harsicha
	 */
	private boolean checkIfUserExists(String username, String password) {
		try (Connection con = DBCPDataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("SELECT EXISTS(SELECT * FROM users WHERE username=?) \"EXISTS\";");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int exists = rs.getInt("EXISTS");
			if (exists == 1) return true;
		} 
		catch (SQLException e) {
			System.out.println("Exception at LoginService: checkIfUserExists method for " + username + ": ");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Checks if the password of this user corresponds to the username.
	 * 
	 * @param username String containing username
	 * @param password String containing this user's password
	 * 
	 * @author harsicha
	 */
	private boolean authenticate(String username, String password) {
		try (Connection con = DBCPDataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("SELECT hash FROM users WHERE username=?;");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String hash = rs.getString("hash");
			String passToSHA256 = DigestUtils.sha256Hex(username + password);
			if (passToSHA256.equals(hash)) return true;
		}
		catch (SQLException e) {
			System.out.println("Exception at LoginService: authenticate method for " + username + ": " + e.getStackTrace());
		}
		return false;
	}
}
