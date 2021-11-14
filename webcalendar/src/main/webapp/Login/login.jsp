<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath() %>/Login/login.css">
</head>
<body>
	<section class="box">
        <div class="d1">
            <h2>PLEASE LOGIN/REGISTER TO CONTINUE</h2>
            <form action="/webcalendar/Login" method="post" id="loginForm">
                <label for="username">Username</label><br>
                <input type="text" placeholder="Enter username" name="username" id="username"><br>
				<p id="nameAlert"></p>
				
                <label for="password">Password</label><br>
                <input type="password" placeholder="Enter password" name="password" id="password"><br>
				<p id="passAlert"></p>
				
                <input type="submit" id="submit">
            </form>
            <br>
            <h4><% 	Object error = request.getAttribute("error");
            		
	            	if (error != null) {
	            		out.print((String)error);
	            	}
           		%>
       		</h4>
        </div>
    </section>
    
    <script src="<%= request.getContextPath() %>/Login/login.js"></script>
</body>
</html>