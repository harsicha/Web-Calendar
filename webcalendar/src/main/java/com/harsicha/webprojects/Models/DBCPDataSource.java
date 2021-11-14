package com.harsicha.webprojects.Models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class DBCPDataSource {
	
	private static BasicDataSource ds = new BasicDataSource();
	
	private DBCPDataSource() {
		
	}
	
	static {
		List<String> db = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("F:\\Work Configs\\db.txt"))) {
			String line = reader.readLine();
			while (line != null) {
				db.add(line);
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		ds.setUrl(db.get(0));
		ds.setUsername(db.get(1));
		ds.setPassword(db.get(2));
		ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
	}
	
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}
