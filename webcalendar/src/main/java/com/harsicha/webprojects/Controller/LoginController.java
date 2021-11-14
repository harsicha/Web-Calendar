package com.harsicha.webprojects.Controller;


import java.io.IOException;
import java.io.PrintWriter;

import com.harsicha.webprojects.Models.User;
import com.harsicha.webprojects.Services.LoginService;
import com.harsicha.webprojects.Services.ServletJSPInfoService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.*;
import jakarta.servlet.jsp.JspFactory;
/**
 * Servlet implementation class Controller
 */

@WebServlet(description = "LoginController", urlPatterns = {"/Login"} )
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 54353L;
    /**
     * Default constructor. 
     */
	public LoginController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	// READ
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		ServletJSPInfoService infoService = ServletJSPInfoService.getInstance();
		
		String serverInfo = infoService.getInfo(request);
		
		System.out.println(serverInfo);
		
		response.setContentType("text/html");
		String res = "<h3>Please use <a href='http://localhost:8080/webcalendar/Login/login.jsp'>this link</a> to login.</h3>";
		PrintWriter writer = response.getWriter();
		writer.print(res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	// CREATE
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		final LoginService service = new LoginService();
		HttpSession session = request.getSession();
		
		System.out.println("Inside Controller!");
	
		User user = service.createUser(request.getParameter("username"), request.getParameter("password"));
		if (user == null) {
			request.setAttribute("error", "Invalid login, please try again.");
			request.getRequestDispatcher("/Login/login.jsp").forward(request, response);
			//response.sendRedirect("/Login/login.jsp");
			return;
		}
		session.setAttribute("user", user);
		response.sendRedirect("/webcalendar/Web Calendar/index.jsp");	
	}

}
