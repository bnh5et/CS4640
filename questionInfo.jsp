<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.io.FileWriter" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="javax.xml.parsers.DocumentBuilder" %>
<%@ page import="javax.xml.parsers.DocumentBuilderFactory" %>
<%@ page import="javax.xml.parsers.ParserConfigurationException" %>
<%@ page import="org.w3c.dom.Element" %>
<%@ page import="org.w3c.dom.NamedNodeMap" %>
<%@ page import="org.w3c.dom.Node" %>
<%@ page import="org.w3c.dom.NodeList" %>
<%@ page import="org.xml.sax.SAXException" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<%!
	ArrayList<String> lines = new ArrayList<String>();
	Map<Integer, Integer> scores = new HashMap<Integer, Integer>();
	int numTeams;
	int turn;
	String question, answer;
%> 
<%
	//Set up game
	numTeams = (Integer) session.getAttribute("NumTeams");
	turn = (Integer) session.getAttribute("Turn");
	question = (String)session.getAttribute("Question");
	answer = (String)session.getAttribute("Answer");
	
	//Read the scores from file
	scores = getScores();
	
	//If user clicks no
	if(request.getParameter("hiddenInput1") != null) {
		turn++; 
		session.setAttribute("Turn", turn);
		response.sendRedirect("http://localhost:8080/Jeopardy/playGame.jsp");
	}
	
	//Increase the score and write to file 
	if(request.getParameter("hiddenInput2") != null) {
		int teamScore = scores.get(turn);
		int newScore = teamScore + 20; //+ passed score 
		scores.put(turn, newScore);
		
		turn++; 
		session.setAttribute("Turn", turn);
		response.sendRedirect("http://localhost:8080/Jeopardy/playGame.jsp");
		
		writeScores();
	}
	
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
		document.form1.hiddenInput1.value = "true";
		form1.submit();
	}
	function rightAns() {
		document.form2.hiddenInput2.value = "true";
		form2.submit();
	}
</script>
</head>
<body>
	<center>
		<div>
			<h2><%out.print(question); %></h2>
			<a><%out.print(answer); %></a>
			<p>Did you answer correctly?</p>
			
			<form method="post" name="form1">
				<input type="hidden" name="hiddenInput1">
				<input type="button" value="No" onclick="wrongAns()">
			</form>
			<form method="post"	name="form2">
				<input type="hidden" name="hiddenInput2">
				<input type="button" value="Yes" onclick="rightAns()">
			</form>
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
							out.print(scores.get(i));
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
<%!

	public Map<Integer, Integer> getScores() throws ParserConfigurationException, SAXException, IOException {
		Map<Integer, Integer> scores = new HashMap<Integer, Integer>();
	
		File xml = new File("/Users/brianahart/workspace/Jeopardy/scores.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		org.w3c.dom.Document doc = builder.parse(xml);
		doc.getDocumentElement().normalize();
	
		String root = doc.getDocumentElement().getNodeName();
		NodeList nl_teams = doc.getElementsByTagName("team");
		
		// In list of teams
		for (int i = 0; i < nl_teams.getLength(); i++) {
			NamedNodeMap teamId = nl_teams.item(i).getAttributes();
			Node id = teamId.item(0);
			
			String id_string = id.toString();
			id_string = id_string.replace("\"", "");
			id_string = id_string.replace("i", "");
			id_string = id_string.replace("d", "");
			id_string = id_string.replace("=", "");
			int id_int = Integer.parseInt(id_string);
			
			NodeList nl_score = ((Element)nl_teams.item(i)).getElementsByTagName("score");	
			
			Node s = nl_score.item(0);
			NodeList nl_s = s.getChildNodes();
			
			int score_int = Integer.parseInt(nl_s.item(0).getTextContent());
	
			scores.put(id_int, score_int);
			
		}
		return scores;
	}
	
	public void writeScores() {		
		try {
			FileWriter fw = new FileWriter("/Users/brianahart/workspace/Jeopardy/scores.xml", false);
			fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			fw.write("<scores>\n");
			
			for (int i = 0; i < numTeams; i++) 
			{
				fw.write("	<team id=\"" + (i + 1) + "\">\n");
				fw.write("		<score>" + scores.get(i + 1) + "</score>\n");
				fw.write("	</team>\n");
			}
			fw.write("</scores>\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
%>
