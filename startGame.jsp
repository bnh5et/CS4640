<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jeopardy</title>
<style>
body {
	font-family: Verdana, Geneva, sans-serif;
	background-color: SlateGrey;
}

div {
	box-shadow: 0 0 5px;
	background-color: white;
	padding: 20px;
	margin: 40px;
	border-radius: 15px;
}

button {
	border: 1px solid maroon;
	background-color: maroon;
	font-family: Verdana, Geneva, sans-serif;
	color: white;
	font-size: 12px;
	border-radius: 5px;
	padding: 5px;
}

button:hover {
	cursor: pointer;
	box-shadow:
}

button:focus {
	outline: 0;
}

input[type=button] {
	width: 100px;
	display: inline-block;
	background-color: maroon;
	border: 1px solid maroon;
	color: white;
	text-decoration: none;
	font-size: 12px;
	border-radius: 5px;
	padding: 5px;
}

input[type=button]:hover {
	cursor: pointer;
}

input[type=submit] {
	width: 100px;
	display: inline-block;
	background-color: maroon;
	border: 1px solid maroon;
	color: white;
	text-decoration: none;
	font-size: 12px;
	border-radius: 5px;
	padding: 5px;
}

input[type=submit]:hover {
	cursor: pointer;
}
</style>
<script type="text/javascript">
	function validate() {
		var num = document.getElementById("num").value;		
		
		if (!isNaN(num)) {
			if (num < 2) {
				alert("You need to have at least 2 teams to play.");
				return false;
			} else if (num > 5) {
				alert("5 is the maximum number of teams.");
				return false;
			} else return true;
		} else {
			alert("Please enter a number");
			return false;
		}
		
	}
</script>
</head>
<body>
	<div>
		<center>
			<h1>Let's Play Jeopardy!</h1>
			<h4>Briana Hart and Samantha Pitcher</h4>
			<form method="post" action="" onsubmit="return validate()">
				Number of Teams: 
				<input type="text" name="numTeams" id="num"/> 
				<br> 
				<br> 
				<input type="button" value="Browse Games"
					onclick="location.href='http://localhost:8080/Jeopardy/browse'">
				<input type="submit" value="Start" id="submit">
			</form>
		</center>
	</div>
</body>
</html>

<%
	int numTeams;
	String str_num = request.getParameter("numTeams");
	
	if("POST".equalsIgnoreCase(request.getMethod())) {
		if (str_num != null && str_num.length() > 0) {
			numTeams = Integer.parseInt(str_num);
			session.setAttribute("NumTeams", numTeams);
			session.setAttribute("Turn", 1);
			session.setAttribute("FirstTurn", true);
			
			response.sendRedirect("http://localhost:8080/Jeopardy/playGame.jsp");
		}
	}
	
%>
