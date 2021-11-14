package com.harsicha.webprojects.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.harsicha.webprojects.Models.DBCPDataSource;
import com.harsicha.webprojects.Models.User;

public class CalendarService {
	/**
	 * Creates a reminder for this user in database.
	 * 
	 * @param user Object containing username
	 * @param reminder Reminder string
	 * @param date Date for which the reminder is created
	 * 
	 * @author harsicha
	 */
	public boolean createReminder(User user, String reminder, String date) {
		try (Connection con = DBCPDataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("INSERT INTO reminders (REMINDER, USERNAME, RDATE) VALUES (?, ?, ?);");
			ps.setString(1, reminder);
			ps.setString(2, user.getUsername());
			ps.setString(3, date);
			ps.executeUpdate();
			return true;
		}
		catch (SQLException e) {
			System.out.println("Exception at CalendarService: createReminder method for " + user.getUsername() + ": ");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Gets all reminders for this user from database.
	 * 
	 * @param user Object containing username
	 * 
	 * @author harsicha
	 */
	public boolean getReminder(User user) {
		try (Connection con = DBCPDataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("SELECT RDATE, REMINDER FROM reminders WHERE USERNAME=?");
			ps.setString(1, user.getUsername());
			Map<String, String> rmap = new HashMap<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String rDate = rs.getString("RDATE");
				String rem = rs.getString("REMINDER");
				if (rmap.containsKey(rDate)) rmap.put(rDate, rmap.get(rDate) + rem);
				else rmap.put(rDate, rem);
			}
			user.setReminders(rmap);
			return true;
		}
		catch (SQLException e) {
			System.out.println("Exception at CalendarService: getReminder method for " + user.getUsername() + ": ");
			e.printStackTrace();
		}
		return false;
	}
}
