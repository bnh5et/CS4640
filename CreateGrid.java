import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class createGrid
 */
@WebServlet("/createGrid")
public class CreateGrid extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public CreateGrid() {

	}

	ArrayList<String> questions = new ArrayList<String>();
	ArrayList<String> answers = new ArrayList<String>();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// read text file that stores questions and answers from previous
		// assignment:
		// read text file from CSLAB Ubuntu server
		// /cslab/home/bnh5et/public_html/projectName

		// TODO change file so it's question on one line, corresponding answer
		// on the next

		URL url = new URL("http://plato.cs.virginia.edu/~bnh5et/HW/data/data.txt");
		Scanner scanner = new Scanner(url.openStream());

		// count number of lines in the data doc
		int count = 0;
		while (scanner.hasNext()) {
			count++;
			scanner.next();
		}

		Scanner reader = new Scanner(url.openStream());
		// account for there existing both questions and answers
		count = count / 3;
		// add the questions and answers to question and answer ArrayLists
		for (int i = 0; i < count; i++) {
			if (reader.hasNextLine()) {
				// if (!reader.nextLine().equals("Question: "))
				reader.nextLine();
				questions.add(reader.nextLine());
				answers.add(reader.nextLine());
			}
		}

		// now we have all the questions and answers saved

		// display html form that has:
		// title and team members' names
		// list of questions and answers
		// two fields where user can specify row and column
		// specify score (display this in the given row and column)
		// button that goes back to PHP data entry form from assignment 3 on the
		// CSLAB server
		// "Create Game button"

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

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
		out.println("");
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
		out.println("");
		out.println("");
		out.println("table {");
		out.println("  border-collapse: collapse;");
		out.println("  table-layout: fixed;");
		out.println("");
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
		out.println("</style>");

		out.println("<script type=\"text/javascript\">");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/javascript/functions.js");
		out.println("	function checkFields(form) {");
		out.println("		cnt = 0;");
		out.println("        msg = \"\";");
		out.println("                ");
		out.println("        $(':input').each(function() {");
		out.println("        	if ($(this.val == '')) ");
		out.println("        		msg += \"A field was left blank.\";");
		out.println("                cnt++;");
		out.println("        })");
		out.println("        ");
		out.println("        ");
		out.println("        $(':input').each(function() {");
		out.println("        	if ($(this.val === parseInt(this.val(data, 10))))");
		out.println("        		msg += \"A field contained something other than an integer.\";");
		out.println("                cnt++;");
		out.println("        })");
		out.println("        ");
		out.println("        if (cnt > 0) {");
		out.println("           alert (\"Please correct the following:\" + msg);");
		out.println("           return false;");
		out.println("        }");
		out.println("        else return true;");
		out.println("		");
		out.println("	}");
		out.println("</script>");

		out.println("");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("	<div id=\"mainpage\">");
		out.println("		<h1>Question Selector</h1>	");
		out.println("		<h4>Briana Hart and Samantha Pitcher</h4>");
		out.println("");
		out.println("		<p>List of questions and answers:  </p> ");
		out.println(
				"        <!-- make a table, with 4 columns and number of rows = the number of questions/answers in the data file -->");
		out.println("        <!-- TODO add list of questions and answers -->");
		out.println("        <!-- TODO two fields where user can specify given row and column -->");
		out.println("        <!-- TODO field to specify score (display this in the given row and column) -->");
		out.println("");
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
		out.println("    <!-- TODO one button to go to form from assign3, one to create game-->");
		out.println(
				"      <a class=\"form\" style=\"width: 100px;\" href=\"http://plato.cs.virginia.edu/~bnh5et/HW/jeopardy.php\">Add More Q/A</a>");
		out.println(
				"      <input class=\"form\" type=\"submit\" name=\"submit\" value=\"Create Game\" style=\"width: 100px;\">");
		out.println("    ");
		out.println("    </form>");
		out.println("  </div>");
		out.println("</center>");
		out.println("</body>");
		out.println("");
		out.println("</html>");

		out.close();

		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// this is for the submit button

		String[] row = request.getParameterValues("row");
		String[] column = request.getParameterValues("col");
		String[] score = request.getParameterValues("score");

		/*
		 * for(int i = 0; i < row.length; i++){ System.out.println(row[i]); }
		 */

		try {
			FileWriter fw = new FileWriter("/Users/brianahart/Documents/submission.txt");

			for (int i = 0; i < questions.size(); i++) {
				fw.write(questions.get(i) + "; " + answers.get(i) + "; " + row[i] + "; " + column[i] + "; " + score[i]
						+ "\n");
			}
			fw.close();
		} catch (IOException e) {
			System.out.println("Could not write to file");
		}

		// process form data submission
		// take the list of questions, answers, rows, columns, scores
		// write these to a text file on your machine
		// can use comma or semicolon to separate each piece of data

		// create jeopardy game and display it on screen
		// include button that allows user to go back to edit the question
		// just want a grid where each cell shows the score
		try {

			// figure out how many rows and columns we need
			int maxrows = 0;
			int maxcols = 0;
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
			
			int[][] scores = new int[maxrows][maxcols];
			/*
			 * for (int i = 1; i <= maxrows; i++){ for (int j = 1; j <= maxcols;
			 * j++){ for(int k = 0; k <= column.length; k++){ for(int l = 0; l
			 * <= row.length; l++){ if(Integer.parseInt(row[l]) == i){
			 * if(Integer.parseInt(column[k]) == j){ scores[i][j] =
			 * Integer.parseInt(score[k]); System.out.println(scores[i][j]); } }
			 * } } } }
			 */
			
			// Display the Jeopardy Grid
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset=\"UTF-8\">");
			out.println("<title>Jeopardy</title>");
			out.println("");
			out.println("  <style>");
			out.println("    body {");
			out.println("      font-family: Verdana, Geneva, sans-serif;");
			out.println("      background-color: SlateGrey;");
			out.println("    }");
			out.println("    div {");
			out.println("      box-shadow: 0 0 5px;");
			out.println("      background-color: white;");
			out.println("      padding: 20px;");
			out.println("      margin: 40px;");
			out.println("      border-radius: 15px;");
			out.println("    }");
			out.println("    a:hover {");
			out.println("      background-color: yellow; ");
			out.println("    } ");
			out.println("    input {");
			out.println("      width: 100px;       ");
			out.println("    }  ");
			out.println("    #multChoice, #trueFalse, #shortAns { ");
			out.println("      display: none;");
			out.println("    } ");
			out.println("    button {");
			out.println("      border: 2px solid maroon;");
			out.println("      background-color: maroon;");
			out.println("      font-family: Verdana, Geneva, sans-serif;");
			out.println("      color: white;");
			out.println("      font-size: 15px;");
			out.println("      border-radius: 5px;");
			out.println("    } ");
			out.println("    button:hover {");
			out.println("      cursor: pointer;");
			out.println("      box-shadow: ");
			out.println("    }");
			out.println("    button:focus {");
			out.println("      outline: 0;");
			out.println("    }");
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
			out.println("");
			out.println("        <table>");

			for (int i = 1; i <= maxrows; i++) {
				out.println("            <tr>");
				for (int j = 1; j <= maxcols; j++) {
					out.println("              <td>");
					if (row[i] != null && column[j] != null) {
						out.println(score[j]);
					}

					out.println("              </td>");
				}
				out.println("            </tr>");
			}
			out.println("        </table>");
			out.println("        ");
			out.println("    <br/>");
			out.println("    <br/>");
			out.println("");
			out.println("  </div>");
			out.println("");
			out.println("    <!-- TODO one button to go back to edit selection-->");
			out.println("      <input type=\"reset\" name=\"reset\" value=\"Back\" onclick=\"window.history.back()\">");
			out.println("    ");
			out.println("    </form>");
			out.println("  </div>");
			out.println("</center>");
			out.println("</body>");
			out.println("");
			out.println("</html>");
		} catch (IOException e) {
			System.out.println("Could not create jeopardy grid");
		}
		// doGet(request, response);
	}

}
