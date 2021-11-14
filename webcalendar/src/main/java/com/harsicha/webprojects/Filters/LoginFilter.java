package com.harsicha.webprojects.Filters;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/Web Calendar/index.jsp"})
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		//String loginURI = req.getContextPath() + "/Login/login.jsp";
		
		System.out.println("Inside Filter!");
        boolean loggedIn = session != null && session.getAttribute("user") != null;
        //boolean loginRequest = req.getRequestURI().equals(loginURI);
        System.out.println(loggedIn);
        if (loggedIn) {
        	req.getRequestDispatcher("/Web Calendar/index.jsp").forward(req, res);
        	//res.sendRedirect("/webcalendar/Web Calendar/index.jsp");
        } else {
        	res.sendRedirect("/webcalendar/Login/login.jsp");
        }
	}

}
