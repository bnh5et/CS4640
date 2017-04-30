import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.text.html.HTMLDocument.Iterator;
import javax.servlet.annotation.*;

import java.io.*;
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
		PrintBody(out);

		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		
		btn = request.getParameter("btn");

		int gameNum;
		Map<String, String> gameList = getGames();
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");  

		if (btn.contains("Delete")) {
			btn = btn.replace("Delete Game ", "");
			gameNum = Integer.parseInt(btn);

			if (gameList.get(String.valueOf(gameNum)).equals(user)) {
				deleteGame(gameNum);
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

	public void PrintBody(PrintWriter out) {
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

	public Map<String, String> getGames() {
		File file = new File("/Users/brianahart/Documents/submission.txt");
		Scanner sc;
		Map<String, String> games = new HashMap<String, String>();

		try {
			sc = new Scanner(file);

			while (sc.hasNext()) {
				String nl = sc.nextLine();
				String[] parsedLine = nl.split(";");

				if (!games.containsKey(parsedLine[0])) {
					games.put(parsedLine[0], parsedLine[1]);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return games;
	}

	public void deleteGame(int gameNum) {

		try {
			File file = new File("/Users/brianahart/Documents/submission.txt");
			Scanner sc = new Scanner(file);
			String nl = "";
			int count = 0;

			while (sc.hasNext()) {
				nl = sc.nextLine();

				if (nl.contains(";")) {
					count++;
				}
			}
			sc.close();

			String[] newFile = new String[count];
			Scanner scanner = new Scanner(file);

			for (int i = 0; i < count; i++) {
				nl = scanner.nextLine();
				String[] parsedLine = nl.split(";");

				if (Integer.parseInt(parsedLine[0]) != gameNum) {
					newFile[i] = nl;
				}
			}
			scanner.close();

			FileWriter fw = new FileWriter("/Users/brianahart/Documents/submission.txt");

			for (int i = 0; i < count; i++) {
				if (newFile[i] != null) {
					fw.write(newFile[i]);
					fw.write("\n");
				}
			}
			fw.close();

		} catch (IOException e) {
			System.out.println("Could not write to file");
		}
	}
}
