import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Servlet implementation class createGrid
 */
@WebServlet("/createGrid")
public class CreateGrid extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ArrayList<String> questions = new ArrayList<String>();
	ArrayList<String> answers = new ArrayList<String>();

	ArrayList<String> arraylist_lines = new ArrayList<String>();
	boolean existSubmission = false;

	private static String LogoutServlet = "http://localhost:8080/Jeopardy/logout";
	private static String LoginServlet = "http://localhost:8080/Jeopardy/login";
	private static String BrowseServlet = "http://localhost:8080/Jeopardy/browse";

	private String user;

	int gameID = 1;
	boolean updated = false;
	String[] row;
	String[] column;
	String[] score;
	int maxrows;
	int maxcols;
	int[][] scores;
	int maxGameID = 0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		user = (String) session.getAttribute("UserID");

		if (user == null || user.length() == 0)
			response.sendRedirect(LoginServlet);

		if (session.getAttribute("updated") != null) {
			updated = (boolean) session.getAttribute("updated");
			session.setAttribute("updated", false);
		}

		if (updated) {
			gameID = (Integer) session.getAttribute("GameNum");
		}

		// Making Questions and Answers Arrays
		URL url = new URL("http://plato.cs.virginia.edu/~bnh5et/HW/data/data.txt");
		Scanner scanner = new Scanner(url.openStream());

		int count = 0;
		while (scanner.hasNext()) {
			count++;
			scanner.next();
		}

		questions.clear();
		answers.clear();

		Scanner reader = new Scanner(url.openStream());
		count = count / 3;
		for (int i = 0; i < count; i++) {
			if (reader.hasNextLine()) {
				reader.nextLine();
				questions.add(reader.nextLine());
				answers.add(reader.nextLine());
			}
		}

		// Print Questions
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		printQuestions(out);
	}

	public void printQuestions(PrintWriter out) {

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Jeopardy</title>");

		out.println("<style type=\"text/css\">");
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
		out.println("input { ");
		out.println("width: 15px; ");
		out.println("}");
		out.println("input.form, a {");
		out.println("  width: 100px;");
		out.println("  display: inline-block;");
		out.println("  background-color: maroon;");
		out.println("  border: 1px solid maroon;");
		out.println("  color: white;");
		out.println("  text-decoration: none;");
		out.println("  font-size: 12px;");
		out.println("  border-radius: 5px;");
		out.println("  padding: 5px;  		");
		out.println("} ");
		out.println("input.form:hover {");
		out.println("  cursor: pointer;");
		out.println("} ");
		out.println("input[type=submit] {");
		out.println("	width: 50px;");
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
		out.println("a:hover {");
		out.println("  cursor: pointer;");
		out.println("} ");
		out.println("#multChoice, #trueFalse, #shortAns { ");
		out.println("  display: none;");
		out.println("} ");
		out.println("button {");
		out.println("  border: 2px solid maroon;");
		out.println("  background-color: maroon;");
		out.println("  font-family: Verdana, Geneva, sans-serif;");
		out.println("  color: white;");
		out.println("  font-size: 15px;");
		out.println("  border-radius: 5px;");
		out.println("} ");
		out.println("button:hover {");
		out.println("  cursor: pointer;");
		out.println("  box-shadow: ");
		out.println("}");
		out.println("button:focus {");
		out.println("  outline: 0;");
		out.println("}");
		out.println("table {");
		out.println("  border-collapse: collapse;");
		out.println("  table-layout: fixed;");
		out.println("}");
		out.println("table, th, td {");
		out.println("  border: 1px solid black;");
		out.println("}");
		out.println("td { ");
		out.println("  margin: 0 10px;");
		out.println("}");
		out.println("th {");
		out.println("  font-weight: bold;");
		out.println("  margin: 5px;");
		out.println("}");
		out.println("div.logout {");
		out.println("	padding: 0px;");
		out.println("	margin: -8px;");
		out.println("	border-radius: 0px;");
		out.println("	font-size: 12px;");
		out.println("}");
		out.println("table.logout {");
		out.println("	align: right;");
		out.println("	margin-right: 0;");
		out.println("	margin-left: auto;");
		out.println("	border: none;");
		out.println("}");
		out.println("table.logout td{");
		out.println("	border: none;");
		out.println("}");
		out.println("</style>");
		out.println("</head>");
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

		out.println("<center>");
		out.println("	<div id=\"mainpage\">");
		out.println("		<h1>Question Selector</h1>	");
		out.println("		<h4>Briana Hart and Samantha Pitcher</h4>");
		out.println("		<p>List of questions and answers:  </p> ");
		out.println("    <form method=\"post\">");
		out.println("        <table style=\"text-align: center;\">");
		out.println("          <tr>");
		out.println("            <th style=\"width: 300px;\">");
		out.println("              Question/Answer ");
		out.println("            </th>");
		out.println("            <th style=\"width: 70px;\">");
		out.println("              Row  ");
		out.println("            </th>");
		out.println("            <th style=\"width: 70px;\">");
		out.println("              Column");
		out.println("            </th>");
		out.println("            <th style=\"width: 70px;\">");
		out.println("              Score");
		out.println("            </th>");
		out.println("          </tr>");

		for (int i = 0; i < questions.size(); i++) {
			out.println("          <tr>");
			out.println("            <td>");
			out.println("              <p style=\"font-weight: bold; padding: 0;\">Question:<p>");
			out.print(questions.get(i));
			out.println("              <p style=\"font-weight: bold; padding: 0;\">Answer:<p> ");
			out.print(answers.get(i));
			out.println("            </td>");
			out.println("            <td>");
			out.println("              <input id=\"rowNum");
			out.print(i);
			out.println("\" type=\"text\" name=\"row\">  ");
			out.println("            </td>");
			out.println("            <td>");
			out.println("              <input id=\"colNum");
			out.print(i);
			out.println("\" type=\"text\" name=\"col\">  ");
			out.println("            </td>");
			out.println("            <td>");
			out.println("              <input id=\"score");
			out.print(i);
			out.println("\" type=\"text\" name=\"score\">  ");
			out.println("            </td>");
		}

		out.println("        </table>");
		out.println("        <br/>");
		out.println(
				"      <a class=\"form\" style=\"width: 100px;\" href=\"http://plato.cs.virginia.edu/~bnh5et/HW/jeopardy.php\">Add More Q/A</a>");
		out.println(
				"      <input class=\"form\" type=\"submit\" name=\"submit\" value=\"Create Game\" style=\"width: 100px;\">");
		out.println("    </form>");
		out.println("  </div>");
		out.println("</center>");
		out.println("</body>");
		out.println("");
		out.println("</html>");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get data from post
		row = request.getParameterValues("row");
		column = request.getParameterValues("col");
		score = request.getParameterValues("score");

		if (updated) {
			try {
				updateGame();
			} catch (ParserConfigurationException | SAXException e) {
				e.printStackTrace();
			}
		} else {
			try {
				createGame();
			} catch (ParserConfigurationException | SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Calculate Max Rows and Max Cols
		maxrows = 0;
		maxcols = 0;
		for (int i = 0; i < row.length; i++) {
			int rowInt = Integer.parseInt(row[i]);
			if (rowInt > maxrows) {
				maxrows = rowInt;
			}
		}

		for (int i = 0; i < column.length; i++) {
			int colInt = Integer.parseInt(column[i]);
			if (colInt > maxcols) {
				maxcols = colInt;
			}
		}

		// Set Scores Array
		scores = new int[maxrows][maxcols];

		for (int i = 0; i < row.length; i++) {
			int rowNum = Integer.parseInt(row[i]) - 1;
			int colNum = Integer.parseInt(column[i]) - 1;
			int scoreNum = Integer.parseInt(score[i]);
			scores[rowNum][colNum] = scoreNum;
		}

		// Display the Jeopardy Grid
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		printBoard(out);

		if (request.getParameterValues("browse") != null) {
			response.sendRedirect(BrowseServlet);
		}
	}

	public void printBoard(PrintWriter out) {
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Jeopardy</title>");
		out.println("");
		out.println("  <style>");
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
		out.println("input { ");
		out.println("width: 15px; ");
		out.println("}");
		out.println("input.form, a {");
		out.println("  width: 100px;");
		out.println("  display: inline-block;");
		out.println("  background-color: maroon;");
		out.println("  border: 1px solid maroon;");
		out.println("  color: white;");
		out.println("  text-decoration: none;");
		out.println("  font-size: 12px;");
		out.println("  border-radius: 5px;");
		out.println("  padding: 5px;  		");
		out.println("} ");
		out.println("input.form:hover {");
		out.println("  cursor: pointer;");
		out.println("} ");
		out.println("input[type=submit] {");
		out.println("	width: 50px;");
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
		out.println("a:hover {");
		out.println("  cursor: pointer;");
		out.println("} ");
		out.println("#multChoice, #trueFalse, #shortAns { ");
		out.println("  display: none;");
		out.println("} ");
		out.println("button {");
		out.println("  border: 2px solid maroon;");
		out.println("  background-color: maroon;");
		out.println("  font-family: Verdana, Geneva, sans-serif;");
		out.println("  color: white;");
		out.println("  font-size: 15px;");
		out.println("  border-radius: 5px;");
		out.println("  width: 100px;");
		out.println("} ");
		out.println("button:hover {");
		out.println("  cursor: pointer;");
		out.println("  box-shadow: ");
		out.println("}");
		out.println("button:focus {");
		out.println("  outline: 0;");
		out.println("}");

		out.println("    table, th, td {");
		out.println("      border: 1px solid black;  ");
		out.println("      color: white;");
		out.println("      background-color: maroon;");
		out.println("    }");
		out.println("    table {");
		out.println("      table-layout: fixed;");
		out.println("      border-collapse: collapse;");
		out.println("    }");
		out.println("    td {");
		out.println("      width: 100px;");
		out.println("      height: 60px;");
		out.println("      text-align: center;");
		out.println("    }");
		out.println("  </style>");
		out.println("");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("  <div id=\"mainpage\">");
		out.println("    <h1>Jeopardy!</h1>  ");
		out.println("    <h4>Briana Hart and Samantha Pitcher</h4>");
		out.println("        <table>");

		for (int i = 0; i < maxrows; i++) {
			out.println("            <tr>");
			for (int j = 0; j < maxcols; j++) {

				// puts score in the box and makes clickable
				out.println("              <td id=\"" + i + j + "\" onclick=\"alert('you clicked the table at (" + i
						+ ", " + j + ") " + "')\">");
				if (scores[i][j] != 0) {
					out.println(scores[i][j]);
				}
				out.println("              </td>");
			}
			out.println("            </tr>");
		}

		out.println("        </table>");
		out.println("    <br/>");
		out.println("    <br/>");
		out.println(
				"      <input style=\"width: 100px;\" type=\"submit\" value=\"Back\" onclick=\"window.history.back()\">");
		out.println(
				"		<input style=\"width: 100px;\" type=\"submit\" value=\"Browse Games\" onclick=\"location.href='"
						+ BrowseServlet + "';\"></input>");
		out.println("  </div>");
		out.println("  </div>");
		out.println("</center>");
		out.println("</body>");
		out.println("");
		out.println("</html>");
	}

	public void createGame() throws ParserConfigurationException, SAXException {
		try {
			// THIS IS WHERE WE WRITE TO FILE
			if (new File("/Users/brianahart/Documents/submission.txt").isFile())
				existSubmission = true;

			// check to see if last line is "</jeopardy>" and remove
			rmOldJeopardy();

			FileWriter fw = new FileWriter("/Users/brianahart/Documents/submission.txt", true);

			// want this one time, first go around
			if (!existSubmission) {
				fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
				fw.write("<jeopardy>\n");
			} else {
				try {
					parseXML();
				} catch (TransformerException e) {
					e.printStackTrace();
				}
			}
			System.out.println("gameID:  " + gameID);
			System.out.println("user:  " + user);
			System.out.println("questions.size():  " + questions.size());

			fw.write("	<game id=\"" + gameID + "\" user=\"" + user + "\">\n");
			for (int i = 0; i < questions.size(); i++) {
				// System.out.println("questions.get(i) " + questions.get(i));
				fw.write("		<question>\n");
				fw.write("			<q>" + questions.get(i) + "</q>\n");
				fw.write("			<answer>" + answers.get(i) + "</answer>\n");
				fw.write("			<row>" + row[i] + "</row>\n");
				fw.write("			<col>" + column[i] + "</col>\n");
				fw.write("			<score>" + score[i] + "</score>\n");
				fw.write("		</question>\n");
			}
			fw.write("	</game>\n");
			fw.write("</jeopardy>\n");
			fw.close();
			// if last line is </jeopardy>, then remove last line
			rmOldJeopardy();
			// rewrite everything from arraylist_lines to the file
			if (existSubmission) {
				FileWriter fw2 = new FileWriter("/Users/brianahart/Documents/submission.txt", false);
				for (String line : arraylist_lines) {
					// puts every line in arraylist_lines in the file
					fw2.write(line + "\n");
				}
				fw2.write("</jeopardy>\n");

				// fw2.write("\n");
				fw2.close();
			}

			// fw.close();
		} catch (IOException e) {
			System.out.println("Could not write to file");
		}
	}

	public void rmOldJeopardy() throws IOException {
		arraylist_lines.clear();
		if (existSubmission) {
			// read in file
			String fileName = "/Users/brianahart/Documents/submission.txt";

			BufferedReader r = new BufferedReader(new FileReader(fileName));
			String in;
			// add to arraylist_lines
			while ((in = r.readLine()) != null) {
				arraylist_lines.add(in);
			}
			r.close();

			for (int k = 0; k < arraylist_lines.size(); k++) {
				if (arraylist_lines.get(k).equals("</jeopardy>")) {
					arraylist_lines.remove(k);
				}
			}
		}
	}

	public void parseXML() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		// reads and prints xml
		// stuff to read file
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

			// find maxGameId
			String idString = id.toString();
			idString = idString.replace("\"", "");
			idString = idString.replace("i", "");
			idString = idString.replace("d", "");
			idString = idString.replace("=", "");

			if (Integer.parseInt(idString) >= maxGameID) {
				maxGameID = Integer.parseInt(idString) + 1;
				gameID = maxGameID;
			}

			for (int q = 0; q < userGameId.getLength(); q++) {
				Node myNode = userGameId.item(q);
			}

			nodeList_questions = ((Element) nodeList_games.item(i)).getElementsByTagName("question");
			for (int j = 0; j < nodeList_questions.getLength(); j++) {
				Node q = nodeList_questions.item(j);
				NodeList nodeList_q = q.getChildNodes();
			}
		}

	}
	// printDocument(doc, System.out);

	public void updateGame() throws ParserConfigurationException, SAXException {
		try {

			// find game with matching gameNum as id
			ArrayList<String> fileText = new ArrayList<String>();

			fileText.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			fileText.add("<jeopardy>\n");

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
				if (Integer.parseInt(idString) != gameID) {

					fileText.add(" <game id=\"" + idString + "\" user=\"" + userString + "\">\n");
					fileText.add("  <question>\n");
				}

				// gets list of questions
				nodeList_questions = ((Element) nodeList_games.item(i)).getElementsByTagName("question");
				for (int j = 0; j < nodeList_questions.getLength(); j++) {

					// get q, a, row, col, score
					Node q = nodeList_questions.item(j);
					// System.out.println(q.getNodeName());
					NodeList nodeList_q = q.getChildNodes();

					for (int k = 0; k < nodeList_q.getLength(); k++) {
						if (Integer.parseInt(idString) != gameID
								&& nodeList_q.item(k).getNodeType() == Node.ELEMENT_NODE) {

							fileText.add("  <" + nodeList_q.item(k).getNodeName() + ">");
							fileText.add(nodeList_q.item(k).getTextContent());
							fileText.add("</" + nodeList_q.item(k).getNodeName() + ">\n");
						}
						// actual question, answer, row, col, score
						// System.out.println(nodeList_q.item(k).getTextContent());
					}
				}
				if (Integer.parseInt(idString) != gameID) {
					fileText.add("   </question>\n");
					fileText.add("  </game>\n");
				}
			}

			fileText.add(" <game id=\"" + gameID + "\" user=\"" + user + "\">\n");
			for (int i = 0; i < questions.size(); i++) {

				fileText.add("  <question>\n");
				fileText.add("   <q>" + questions.get(i) + "</q>\n");
				fileText.add("   <answer>" + answers.get(i) + "</answer>\n");
				fileText.add("   <row>" + row[i] + "</row>\n");
				fileText.add("   <col>" + column[i] + "</col>\n");
				fileText.add("   <score>" + score[i] + "</score>\n");
				fileText.add("  </question>\n");

			}
			fileText.add(" </game>\n");
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
