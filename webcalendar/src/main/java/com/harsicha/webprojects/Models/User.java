package com.harsicha.webprojects.Models;

import java.util.Map;

/**
 * Object for storing username and a map of reminders.
 * 
 * @author harsicha
 */
public class User {
	
	private String username;
	private Map<String, String> reminders;
	
	public User() {
		
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Map<String, String> getReminders() {
		return this.reminders;
	}
	
	public void setReminders(Map<String, String> Reminders) {
		this.reminders = Reminders;
	}
}
