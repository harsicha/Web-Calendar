package com.harsicha.webprojects.Services;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspFactory;

// Singleton Thread-safe Design Pattern.
public class ServletJSPInfoService {
	
	private static ServletJSPInfoService instance;
	
	private ServletJSPInfoService() {
		
	}
	
	synchronized public static ServletJSPInfoService getInstance() {
		if (instance == null) {
			instance = new ServletJSPInfoService();
		}
		return instance;
	}
	
	synchronized public String getInfo(HttpServletRequest request) {
		ServletContext application = request.getServletContext();
		String serverInfo = "Server Version: " + application.getServerInfo();
		serverInfo += "\nServlet Version: " + application.getMajorVersion();
		serverInfo += "\nJSP Version: " + JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion();
		
		return serverInfo;
	}
	
}
