package com.harsicha.webprojects.Models;

import java.util.HashMap;
import java.util.Map;

public class User {
	
	private String username;
	private Map<String, String> reminders;
	
	public User() {
		// this.Reminders = new HashMap<>();
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
