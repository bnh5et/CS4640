<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.io.FileWriter" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
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
	ArrayList<Integer> rows = new ArrayList<Integer>();
	ArrayList<Integer> cols = new ArrayList<Integer>();
	ArrayList<Integer> points_list = new ArrayList<Integer>();
	ArrayList<String> q_list = new ArrayList<String>();
	ArrayList<String> a_list = new ArrayList<String>();

	int maxrows;
	int maxcols;
	int[][] points;
	String[][] question;
	String[][] answer;
	Map<Integer, Integer> scores = new HashMap<Integer, Integer>();
%>
<%	
	//Clear everything 
	if ((boolean) session.getAttribute("FirstTurn")) {
		rows.clear();
		cols.clear();
		points_list.clear();
		q_list.clear();
		a_list.clear();
		maxrows = 0;
		maxcols = 0;
		points = new int[0][0];
		question = new String[0][0];
		scores = new HashMap<Integer, Integer>();
	}

	//Get the game number
	int gameNum = (Integer) session.getAttribute("GameNum");
	
	//Get the number of teams
	int numTeams = (Integer) session.getAttribute("NumTeams");
	
	//Get the turn 
	int turn = (Integer) session.getAttribute("Turn");
	if(request.getParameter("hiddenInput") != null) {
		turn++;
	}
	if(turn > numTeams) {
		turn = 1;
	}
	session.setAttribute("Turn", turn);
	
	
	//Find the max rows and cols 
	int maxcols = 4;
	int maxrows = 4;
	
	//Create the scores file 
	if ((boolean) session.getAttribute("FirstTurn")) {
		session.setAttribute("FirstTurn", false);
		
		FileWriter fw = new FileWriter("/Users/brianahart/workspace/Jeopardy/scores.xml", false);
		fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		fw.write("<scores>\n");
		
		for (int i = 0; i < numTeams; i++) 
		{
			fw.write("	<team id=\"" + (i+1) + "\">\n");
			fw.write("		<score>0</score>\n");
			fw.write("	</team>\n");
		}
		fw.write("</scores>\n");
		fw.close();
	}
	
	//Read the scores from file
	scores = getScores();
	
	//Get the questions from the file 
	File xml = new File("/Users/brianahart/Documents/submission.txt");
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = factory.newDocumentBuilder();
	org.w3c.dom.Document doc = builder.parse(xml);
	doc.getDocumentElement().normalize();

	String root = doc.getDocumentElement().getNodeName();
	NodeList nodeList_games = doc.getElementsByTagName("game");
	NodeList nodeList_questions;
	
	// In list of games
	for (int i = 0; i < nodeList_games.getLength(); i++) {
		NamedNodeMap userGameId = nodeList_games.item(i).getAttributes();
		Node id = userGameId.item(0);
		String id_string = id.toString();
		id_string = id_string.replace("\"", "");
		id_string = id_string.replace("i", "");
		id_string = id_string.replace("d", "");
		id_string = id_string.replace("=", "");
		int id_int = Integer.parseInt(id_string);
		
		if(id_int == gameNum){
			nodeList_questions = ((Element)nodeList_games.item(i)).getElementsByTagName("question");
			for (int j = 0; j < nodeList_questions.getLength(); j++) {
				Node q = nodeList_questions.item(j);
				NodeList nodeList_q = q.getChildNodes();
				
				q_list.add(nodeList_q.item(1).getTextContent());
				a_list.add(nodeList_q.item(3).getTextContent());
				rows.add(Integer.parseInt(nodeList_q.item(5).getTextContent()));
				cols.add(Integer.parseInt(nodeList_q.item(7).getTextContent()));
				points_list.add(Integer.parseInt(nodeList_q.item(9).getTextContent()));
			}
		}	
	}
	maxrows = 0;
	maxcols = 0;
	for (int i = 0; i < rows.size(); i++) {
		int rowInt = rows.get(i);
		if (rowInt > maxrows) {
			maxrows = rowInt;
		}
	}

	for (int i = 0; i < cols.size(); i++) {
		int colInt = cols.get(i);
		if (colInt > maxcols) {
			maxcols = colInt;
		}
	}
	
	points = new int[maxrows][maxcols];
	question = new String[maxrows][maxcols];
	answer = new String[maxrows][maxcols];

	System.out.println("-----------");
	for (int i = 0; i < rows.size(); i++) {
		int rowNum = rows.get(i) - 1;
		int colNum = cols.get(i) - 1;
		points[rowNum][colNum] = points_list.get(i);
		question[rowNum][colNum] = q_list.get(i);
		answer[rowNum][colNum] = a_list.get(i);
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
			<h1>Team <%out.print(turn);%>'s Turn</h1>
			<table>
				<%
					for (int i = 0; i < maxrows; i++) { //maxrows
				%>
				<tr>
					<%
						for (int j = 0; j < maxcols; j++) { //maxcols
					%>
					<td <% 
					if(points[i][j] != 0) {
						out.print("onclick=\"alert(\'Hi\')\"");
					}
					%>>
						<%
							if (points[i][j] != 0) {
								out.println(points[i][j]);
							}
							if (question[i][j] != null) {
								session.setAttribute("Question", question[i][j]);
							}
							if (answer[i][j] != null) {
								session.setAttribute("Question", answer[i][j]);
							}
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
							out.print(scores.get(i));
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
%>
