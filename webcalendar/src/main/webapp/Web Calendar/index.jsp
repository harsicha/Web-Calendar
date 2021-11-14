<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
        href="https://fonts.googleapis.com/css2?family=Georama:ital,wght@0,400;0,500;0,600;0,700;1,400;1,700&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="app.css">
    <link rel="icon" href="favicon.ico">
    <title>Calendar</title>
</head>

<body>
    <label for="month" class="labgrp1">Select Month</label>
    <select name="month" id="month">

    </select>

    <label for="year" class="labgrp1">Select Year</label>
    <select id="year" name="year">

    </select>
    
	<span id="sp"></span>
	<button id="logout">Logout</button>
	
    <section id="sect1">
        <div id="div1"></div>
    </section>

    <script src="app.js"></script>
</body>

</html>