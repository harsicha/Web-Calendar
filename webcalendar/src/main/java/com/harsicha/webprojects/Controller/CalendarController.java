package com.harsicha.webprojects.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.harsicha.webprojects.Models.User;
import com.harsicha.webprojects.Services.CalendarService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;

/**
 * Servlet implementation class CalendarController
 * 
 * The class implements GET and POST endpoints for storing and retrieving reminders from database.
 * 
 * @author harsicha
 */

@WebServlet(description = "CalendarController", urlPatterns = {"/Calendar"} )
public class CalendarController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SUCCESS_MSG = "success";
	private static final String FAILURE_MSG = "failure";
    /**
     * Default constructor. 
     */
    public CalendarController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * Returns JSON data by mapping user object to JSON object.
	 * The JSON data contains username and their reminders.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession hs = request.getSession(false);
		User user = (User) hs.getAttribute("user");
		CalendarService service = new CalendarService();
		service.getReminder(user);
		String userJsonString = new Gson().toJson(user);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(userJsonString);
		out.flush();
	}

	/**
	 * Receives reminder and date attributes from frontend which are then stored in database.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession hs = request.getSession(false);
		User user = (User) hs.getAttribute("user");
		
	    StringBuilder sb = new StringBuilder();
	    BufferedReader br = request.getReader();
	    String str;
	    while( (str = br.readLine()) != null ){
	        sb.append(str);
	    }    
	    
	    JSONObject jObj = new JSONObject(sb.toString());
	    System.out.println(jObj);
		String reminder = jObj.getString("reminder");
		String date = jObj.getString("date");
		CalendarService service = new CalendarService();
		boolean res = service.createReminder(user, reminder, date);
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String msg = res ? SUCCESS_MSG : FAILURE_MSG;
		out.print(msg);
		out.flush();
	}

}
