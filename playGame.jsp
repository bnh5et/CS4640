<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	int numTeams = (Integer) session.getAttribute("NumTeams");
	
	int turn = (Integer) session.getAttribute("Turn");
	if(request.getParameter("hiddenInput") != null) {
		turn++;
	}
	if(turn > numTeams) {
		turn = 1;
	}
	session.setAttribute("Turn", turn);
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

td:hover {
	cursor: pointer;
}

table.team, tr.team, td.team {
	font-size: 12px;
	background-color: white;
	color: maroon;
	border-collapse: separate;
}

td.team:hover {
	cursor: auto;
}

#team<%=turn%> {
	box-shadow: 0 0 2px 2px maroon;
}

</style>
<script type="text/javascript">
	function skip() {
		document.form1.hiddenInput.value = "true";
		form1.submit();
	}
</script>
</head>

<body>
	<center>
		<div>
			<h3>Team <%out.print(turn); %>'s Turn</h3>
			<table>
				<%
					for (int i = 0; i < 4; i++) { //maxrows
				%>
				<tr>
					<%
						for (int j = 0; j < 4; j++) { //maxcols
					%>
					<td onclick="alert('Clicked ')">
						<%
							out.println("Hi");
									/*if (scores[i][j] != 0) {
										out.println(scores[i][j]);
									}*/
							
						%>
					</td>
					<%
						}
					%>
				</tr>
				<%
					}
				%>
			</table>
			<%
			Map<Integer, Integer> teams = new HashMap<Integer, Integer>();
			for(int i = 1; i <= numTeams; i++){
				teams.put(i, 0);
			}

			%>
			<br>
			<table class="team">
				<tr class="team">
					<%
						for (int i = 1; i <= numTeams; i++) {
					%>
					<td class="team" id="team<%=i%>">Team 
						<%
							out.print(i);
						%> 
						<br> 
						Score: 
						<%
							out.print(teams.get(i));
						%>
					</td>
					<%
						}
					%>
				</tr>
			</table>
			<br>
			<br>
			<form method="post" name="form1">
				<input onclick="location.href='http://localhost:8080/Jeopardy/browse';" type="button" value="Quit Game">
				<input type="hidden" name="hiddenInput">
				<input type="button" value="Skip Turn" onclick="skip()">
			</form>
			<br>
			<br>
			<button onclick="location.href='http://localhost:8080/Jeopardy/questionInfo.jsp';" style="width: auto;">TEMP: Navigate to Question Info</button>
		</div>
	</center>
</body>
</html>
