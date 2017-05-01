import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.text.html.HTMLDocument.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

//*********************************************************************

@WebServlet("/browse")
public class browse extends HttpServlet {
	private static final long serialVersionUID = 2L;

	// **** setting for local ****/
	private static String LoginServlet = "http://localhost:8080/Jeopardy/login";
	private static String LogoutServlet = "http://localhost:8080/Jeopardy/logout";
	private static String CreateGridServlet = "http://localhost:8080/Jeopardy/createGrid";
	private static String BrowseServlet = "http://localhost:8080/Jeopardy/browse";
	private static String StartGameJSP = "http://localhost:8080/Jeopardy/startGame.jsp";

	// doPost() tells doGet() when the login is invalid.
	private static boolean invalidID = false;

	private static String btn;

	private String user;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		user = (String) session.getAttribute("UserID");

		if (user == null || user.length() == 0)
			response.sendRedirect(LoginServlet);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		PrintHead(out);
		try {
			PrintBody(out);
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}

		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		btn = request.getParameter("btn");

		// session.setAttribute("updated", false);

		int gameNum;
		Map<String, String> gameList = null;
		try {
			gameList = getGames();
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();

		}
		// PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		if (btn.contains("Delete")) {
			btn = btn.replace("Delete Game ", "");
			gameNum = Integer.parseInt(btn);

			if (gameList.get(String.valueOf(gameNum)).equals(user)) {
				try {
					deleteGame(gameNum);
				} catch (ParserConfigurationException | SAXException e) {
					e.printStackTrace();
				}
			}
		} else if (btn.contains("Update")) {
			btn = btn.replace("Update Game ", "");
			gameNum = Integer.parseInt(btn);
			if (gameList.get(String.valueOf(gameNum)).equals(user)) {
				session.setAttribute("GameNum", gameNum);
				session.setAttribute("updated", true);
				response.sendRedirect(CreateGridServlet);
			}
		} else if (btn.contains("Play")) {
			btn = btn.replace("Play Game ", "");
			gameNum = Integer.parseInt(btn);
			session.setAttribute("GameNum", gameNum);
			response.sendRedirect(StartGameJSP);
		}
		doGet(request, response);
	}

	public void PrintHead(PrintWriter out) {
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Browse Jeopardy Games</title>");
		out.println("<style>");
		out.println("body {");
		out.println("	font-family: Verdana, Geneva, sans-serif;");
		out.println("	background-color: SlateGrey;");
		out.println("}");
		out.println("div {");
		out.println("	box-shadow: 0 0 5px;");
		out.println("	background-color: white;");
		out.println("	padding: 20px;");
		out.println("	margin: 40px;");
		out.println("	border-radius: 15px;");
		out.println("}");
		out.println("div.logout {");
		out.println("	padding: 0px;");
		out.println("	margin: -8px;");
		out.println("	border-radius: 0px;");
		out.println("	font-size: 12px;");
		out.println("}");
		out.println("input[type=submit] {");
		out.println("	width: 150px;");
		out.println("	display: inline-block;");
		out.println("	background-color: maroon;");
		out.println("	border: 1px solid maroon;");
		out.println("	color: white;");
		out.println("	text-decoration: none;");
		out.println("	font-size: 12px;");
		out.println("	border-radius: 5px;");
		out.println("	padding: 5px;");
		out.println("}");
		out.println("input[type=submit]:hover {");
		out.println("	cursor: pointer;");
		out.println("}");
		out.println("table.logout {");
		out.println("	align: right;");
		out.println("	margin-right: 0;");
		out.println("	margin-left: auto;");
		out.println("}");
		out.println("table.games {");
		out.println("	width: 100%;");
		out.println("	border-collapse: collapse;");
		out.println("	border: 1px solid black;");
		out.println("}");
		out.println("table.games th, table.games td {");
		out.println("	border: 1px solid black;");
		out.println("	/*padding: 0 5px;*/");
		out.println("}");
		out.println("</style>");
		out.println("</head>");
	}

	public void PrintBody(PrintWriter out) throws ParserConfigurationException, SAXException, IOException {
		out.println("<body>");
		out.println("	<div class=\"logout\">");
		out.println("		<table class=\"logout\">");
		out.println("			<tr>");
		out.println("				<td><p>Welcome, " + user + "!</p></td>");
		out.println("				<td></td>");
		out.println("				<td>");
		out.println("					<form class=\"logout\" action=\"" + LogoutServlet + "\" method=\"post\">");
		out.println("						<input style=\"width: 100px;\" type=\"submit\" value=\"Logout\"></input>");
		out.println("					</form>");
		out.println("				</td>");
		out.println("			</tr>");
		out.println("		</table>");
		out.println("	</div>");

		out.println("	<div>");
		out.println("		<center>");
		out.println("			<h1>Let's Play Jeopardy!</h1>	");
		out.println("			<form action=\"" + CreateGridServlet + "\" method=\"get\">");
		out.println("				<input style=\"width: 150px;\" type=\"submit\" value=\"Create New Game\"></input>");
		out.println("			</form>");
		out.println("			<br/>");
		out.println("		</center>");
		out.println("		<table class=\"games\">");
		out.println("			<tr>");
		out.println("			 	<th>Game ID</th>");
		out.println("			 	<th>User</th>");
		out.println("			 	<th></th>");
		out.println("			 	<th></th>");
		out.println("			 	<th></th>");
		out.println("			 </tr>");

		Map<String, String> gameList = getGames();

		Set<String> keys = gameList.keySet();

		for (String s : keys) {
			out.println("			 <tr>");
			out.println("			 	<td>" + s + "</td>");
			out.println("			 	<td>" + gameList.get(s) + "</td>");
			out.println("			 	<td align=\"center\">");
			out.println("			 		<form action=\"" + BrowseServlet + "\" method=\"post\">");
			out.println("			 			<input class=\"form\" type=\"submit\" value=\"Update Game " + s
					+ "\" name=\"btn\"> ");
			out.println("			 		</form>");
			out.println("			 	</td>");
			out.println("			 	<td align=\"center\">");
			out.println("			 		<form action=\"" + BrowseServlet + "\" method=\"post\">");
			out.println("			 			<input class=\"form\" type=\"submit\" value=\"Delete Game " + s
					+ "\" name=\"btn\">");
			out.println("			 		</form>");
			out.println("			 	</td>");
			out.println("			 	<td align=\"center\">");
			out.println("			 		<form action=\"" + BrowseServlet + "\" method=\"post\">");
			out.println("			 			<input class=\"form\" type=\"submit\" value=\"Play Game " + s
					+ "\" name=\"btn\">");
			out.println("			 		</form>");
			out.println("			 	</td>");
			out.println("			 </tr>");
		}

		out.println("		</table>");
		out.println("	</div>");
		out.println("</body>");
		out.println("</html>");
	}

	public Map<String, String> getGames() throws ParserConfigurationException, SAXException, IOException {
		Map<String, String> games = new HashMap<String, String>();
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
			Node user = userGameId.item(1);

			String user_string = user.toString();
			user_string = user_string.substring(5);
			user_string = user_string.replace("\"", "");

			String id_string = id.toString();
			id_string = id_string.replace("\"", "");
			id_string = id_string.replace("i", "");
			id_string = id_string.replace("d", "");
			id_string = id_string.replace("=", "");

			if (!games.containsKey(id_string)) {
				games.put(id_string, user_string);
			}
		}
		return games;
	}

	public void deleteGame(int gameNum) throws ParserConfigurationException, SAXException {
		try {

			// find game with matching gameNum as id
			// take out corresponding lines in the file

			ArrayList<String> fileText = new ArrayList<String>();

			fileText.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			fileText.add("<jeopardy>\n");

			// get gameID and check to see if == gameNum

			File xml = new File("/Users/brianahart/Documents/submission.txt");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			org.w3c.dom.Document doc = builder.parse(xml);
			doc.getDocumentElement().normalize();

			String root = doc.getDocumentElement().getNodeName();
			NodeList nodeList_games = doc.getElementsByTagName("game");
			NodeList nodeList_questions;
			String idString = "";
			String userString = "";

			for (int k = 0; k < fileText.size(); k++) {
				if (fileText.get(k).equals("</jeopardy>")) {
					fileText.remove(k);
				}
			}

			// In list of games
			for (int i = 0; i < nodeList_games.getLength(); i++) {
				NamedNodeMap userGameId = nodeList_games.item(i).getAttributes();
				Node id = userGameId.item(0);
				Node user = userGameId.item(1);

				idString = id.toString();
				idString = idString.replace("\"", "");
				idString = idString.replace("i", "");
				idString = idString.replace("d", "");
				idString = idString.replace("=", "");

				userString = user.toString();
				userString = userString.substring(5);
				userString = userString.replace("\"", "");

				for (int k = 0; k < fileText.size(); k++) {
					if (fileText.get(k).equals("</jeopardy>")) {
						fileText.remove(k);
					}
				}
				if (Integer.parseInt(idString) != gameNum) {

					fileText.add(" <game id=\"" + idString + "\" user=\"" + userString + "\">\n");
					fileText.add(" <question>\n");
				}

				// gets list of questions
				nodeList_questions = ((Element) nodeList_games.item(i)).getElementsByTagName("question");
				for (int j = 0; j < nodeList_questions.getLength(); j++) {

					// get q, a, row, col, score
					Node q = nodeList_questions.item(j);
					// System.out.println(q.getNodeName());
					NodeList nodeList_q = q.getChildNodes();

					for (int k = 0; k < nodeList_q.getLength(); k++) {
						if (Integer.parseInt(idString) != gameNum
								&& nodeList_q.item(k).getNodeType() == Node.ELEMENT_NODE) {

							fileText.add("  <" + nodeList_q.item(k).getNodeName() + ">");
							fileText.add(nodeList_q.item(k).getTextContent());
							fileText.add("</" + nodeList_q.item(k).getNodeName() + ">\n");
						}
						// actual question, answer, row, col, score
						// System.out.println(nodeList_q.item(k).getTextContent());
					}

				}

				if (Integer.parseInt(idString) != gameNum) {
					fileText.add("   </question>\n");
					fileText.add("  </game>\n");
				}

			}
			fileText.add("</jeopardy>\n");

			// write fileText to the file again
			FileWriter fw = new FileWriter("/Users/brianahart/Documents/submission.txt", false);
			for (int i = 0; i < fileText.size(); i++) {
				fw.write(fileText.get(i));
			}
			fw.close();

		} catch (IOException e) {
			System.out.println("Could not write to file");
		}

	} // end try

}
