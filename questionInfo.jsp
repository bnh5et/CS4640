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
%> 
<%
	int numTeams = (Integer) session.getAttribute("NumTeams");
	int turn = (Integer) session.getAttribute("Turn");
	
	//Read the scores from file
	scores = getScores();
	
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
	function rightAns() {
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
			<button onclick="rightAns()">Yes</button>
			<button onclick="wrongAns()">No</button>
			<br>
			<%
				out.print(session.getAttribute("Question"));
			%>
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
	public void rmLastLine() {
		String fileName = "/Users/brianahart/workspace/Jeopardy/scores.xml";

		try {
			BufferedReader r = new BufferedReader(new FileReader(fileName));
			String in;
			while ((in = r.readLine()) != null) {
				lines.add(in);
			}
			r.close();

			for (int k = 0; k < lines.size(); k++) {
				if (lines.get(k).equals("</scores>")) {
					lines.remove(k);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
