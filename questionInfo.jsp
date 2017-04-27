<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	int numTeams = (Integer) session.getAttribute("NumTeams");
	int turn = (Integer) session.getAttribute("Turn");
%>        

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

button {
  border: 2px solid maroon;
  background-color: maroon;
  font-family: Verdana, Geneva, sans-serif;
  color: white;
  font-size: 12px;
  border-radius: 5px;
  width: 100px;
} 

button:hover {
  cursor: pointer;
  box-shadow: 
}

button:focus {
  outline: 0;
}

div {
	box-shadow: 0 0 5px;
	background-color: white;
	padding: 20px;
	margin: 40px;
	border-radius: 15px;
}

input[type=submit] {
	width: 50px;
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

table, th, td {
	border: 1px solid black;
	color: white;
	background-color: maroon;
}

table {
	table-layout: fixed;
	border-collapse: collapse;
}

td {
	width: 100px;
	height: 60px;
	text-align: center;
}

table.team, tr.team, td.team {
	font-size: 12px;
	background-color: white;
	color: maroon;
	border-collapse: separate;
}

#team<%=turn%> {
	box-shadow: 0 0 2px 2px maroon;
}
</style>

<script type="text/javascript">
	function wrongAns() {
		<% 
		turn++; 
		session.setAttribute("Turn", turn);
		%>
		location.href = "http://localhost:8080/Jeopardy/playGame.jsp";
	}
</script>
</head>
<body>
	<center>
		<div>
			<h2>QUESTION</h2>
			<a>Reveal Answer</a>
			<p>Did you answer correctly?</p>
			<button>Yes</button>
			<button onclick="wrongAns()">No</button>
			<br>
			<br>
			<table class="team">
				<tr class="team">
					<%
						for (int i = 1; i <= numTeams; i++) {
					%>
					<td class="team" id="team<%=i%>">
						Team 
						<%
							out.print(i);
						%> 
						<br> 
						Score: 
						<%
							//out.print(teams.get(i));
						%>
					</td>
					<%
						}
					%>
				</tr>
			</table>
		</div>
	</center>
</body>
</html>
