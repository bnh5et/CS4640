import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.*;
import java.util.Scanner;

//*********************************************************************

@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 2L;

	// **** setting for local ****/
	private static String LoginServlet = "http://localhost:8080/Jeopardy/login";
	private static String BrowseServlet = "http://localhost:8080/Jeopardy/browse";

	// a data file containing username and password
	public static String user_info = "/Applications/apache/webapps/cs4640/WEB-INF/data/user-info.txt";

	// Form parameters.
	private static String btn;
	// info for returning users
	private static String userID;
	private static String pwd;
	// info for new users
	private static String newUserID;
	private static String newPwd;
	private static String confirmPwd;
	private static String email;

	// doPost() tells doGet() when the login is invalid.
	private static boolean invalidID = false;

	private int number_attempts = 5;

	/**
	 * HttpServlet's doGet(). prints the login form.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Jeopardy</title>");
		out.println("<style>");
		out.println("body {");
		out.println("	font-family: Verdana, Geneva, sans-serif;");
		out.println("	background-color: SlateGrey;");
		out.println("}");
		out.println("div {");
		out.println("	box-shadow: 0 0 5px;");
		out.println("	background-color: white;");
		out.println("	padding: 20px;");
		out.println("	margin: 40px;;");
		out.println("	border-radius: 15px;");
		out.println("}");
		out.println("input {");
		out.println("	width: 100px;");
		out.println("}");
		out.println("input.btn {");
		out.println("	width: 100px;");
		out.println("	display: inline-block;");
		out.println("	background-color: maroon;");
		out.println("	border: 1px solid maroon;");
		out.println("	color: white;");
		out.println("	text-decoration: none;");
		out.println("	font-size: 12px;");
		out.println("	border-radius: 5px;");
		out.println("	padding: 5px;");
		out.println("}");
		out.println("input.btn:hover {");
		out.println("	cursor: pointer;");
		out.println("}");
		out.println("a:hover {");
		out.println("	cursor: pointer;");
		out.println("}");
		out.println("button {");
		out.println("	border: 2px solid maroon;");
		out.println("	background-color: maroon;");
		out.println("	font-family: Verdana, Geneva, sans-serif;");
		out.println("	color: white;");
		out.println("	font-size: 15px;");
		out.println("	border-radius: 5px;");
		out.println("}");
		out.println("button:hover {");
		out.println("	cursor: pointer;");
		out.println("	box-shadow:");
		out.println("}");
		out.println("button:focus {");
		out.println("	outline: 0;");
		out.println("}");
		out.println("form {");
		out.println("	border-top: 1px solid SlateGrey;");
		out.println("}");
		out.println("table {");
		out.println("	/*border-collapse: collapse;*/");
		out.println("	table-layout: fixed;");
		out.println("}");
		out.println("table, th, td {");
		out.println("	border: none;");
		out.println("}");
		out.println("td {");
		out.println("	margin: 0 10px;");
		out.println("	width: 160px;");
		out.println("}");
		out.println("</style>");
		out.println("</head>");

		out.println("<body onLoad=\"setFocus()\" >");
		out.println("	<center>");
		out.println("		<div>");
		out.println("			<h1>Let's Play Jeopardy!</h1>");
		out.println("			<h4>Briana Hart and Samantha Pitcher</h4>");
		out.println("			<br/>");

		// Returning users
		out.println("<form method=\"post\"");
		out.println("        action=\"" + LoginServlet + "\" id=\"form1\" name=\"form1\">");
		out.println("				<h2>Returning User</h2>");
		out.println("				<table>");
		out.println("					<tr>");
		out.println("						<td>UserID:</td>");
		out.println("						<td><input type=\"text\" name=\"UserID\"></td>");
		out.println("						<td>Password:</td>");
		out.println("						<td><input type=\"password\" name=\"pwd\"></td>");
		out.println("					</tr>");
		out.println("				</table>");
		out.println("				<br/>");

		if (invalidID) {
			invalidID = false;
			out.println("<br><font color=\"red\">Invalid user ID, password pair. Please try again.</font><br><br>");
			number_attempts--;
			out.println("<font color=\"red\">You have " + number_attempts + " attempts left before you're locked out.</font><br><br>");
		}

		out.println("				<input class=\"btn\" type=\"submit\" value=\"Log in\" name=\"btn\" ");
		
		if (number_attempts < 0) {
			out.println("disabled");
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			number_attempts = 5;
		}
				
		out.println(">");
		out.println("			</form>");

		// Register new user
		out.println("<form method=\"post\"");
		out.println("        action=\"" + LoginServlet + "\" id=\"form2\" name=\"form2\">");

		out.println("				<h2>New User</h2>");
		out.println("				<table>");
		out.println("					<tr>");
		out.println("						<td>UserID:</td>");
		out.println("						<td><input type=\"text\" name=\"newUserID\"></td>");
		out.println("						<td>Contact E-mail:</td>");
		out.println("						<td><input type=\"text\" name=\"email\"></td>");
		out.println("					</tr>");
		out.println("					<tr>");
		out.println("						<td>Password:</td>");
		out.println("						<td><input type=\"password\" name=\"newPwd\"></td>");
		out.println("						<td>Confirm Password:</td>");
		out.println("						<td><input type=\"password\" name=\"confirmPwd\"></td>");
		out.println("					</tr>");
		out.println("				</table>");
		out.println("				<br/>");
		out.println("				<input class=\"btn\" type=\"submit\" value=\"Register\" name=\"btn\">");
		out.println("			</form>");

		out.println("		</div>");
		out.println("	</center>");
		out.println("</body>");
		out.println("</html>");

		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		btn = request.getParameter("btn");

		userID = request.getParameter("UserID");
		pwd = request.getParameter("pwd");

		newUserID = request.getParameter("newUserID");
		newPwd = request.getParameter("newPwd");
		confirmPwd = request.getParameter("confirmPwd");
		email = request.getParameter("email");

		if (btn.equals("Register")) {
			if (newPwd.equals(confirmPwd) && newUserID.length() > 0 && newPwd.length() > 0 && email.length() > 0) {
				registerNewUser(newUserID, newPwd, email);
				userID = newUserID;
				pwd = newPwd;
			}
		}

		if (isValid(userID, pwd)) {
			session.setAttribute("isLogin", "Yes");
			session.setAttribute("UserID", userID);

			response.sendRedirect(BrowseServlet);

		} else {
			session.setAttribute("isLogin", "No");
			session.setAttribute("UserID", "");

			invalidID = true;
			doGet(request, response);
		}

	}

	/**
	 * isValid: verify userid and password
	 * 
	 * @param userid
	 * @param password
	 * @return true -- userid/password matches, false -- non-existent userid or
	 *         userid/password does not match
	 */
	private boolean isValid(String userid, String password) {
		boolean is_valid = false;
		if (userid.length() == 0 || password.length() == 0)
			return false;

		try {
			BufferedWriter fileOut = new BufferedWriter(new FileWriter(user_info, true));
			Scanner scanner = new Scanner(new BufferedReader(new FileReader(user_info)));

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String existing_user = line.substring(0, line.indexOf(","));
				String existing_pwd = line.substring(line.indexOf(",") + 1, line.lastIndexOf(","));
				if (userid.equals(existing_user) && password.equals(existing_pwd)) {
					is_valid = true;
					break;
				}
			}

			scanner.close();
			fileOut.close();

		} catch (Exception e) {
			System.out.println("Unable to verify the user information ");
		}

		return is_valid;
	}

	/**
	 * registerNewUser: register a new userid/password/email to user_info.txt
	 * 
	 * @param userid
	 * @param pwd
	 * @param email
	 *            note: need to verify if userid already exists (not
	 *            implemented) need to encrypt the credential (not implemented)
	 */
	private void registerNewUser(String userid, String pwd, String email) {
		FileWriter fw = null;
		PrintWriter pw = null;

		try {
			fw = new FileWriter(user_info, true);
			pw = new PrintWriter(fw);

			pw.println(userid + "," + pwd + "," + email);
		} catch (Exception e) {
			System.out.println("ERROR while registering new users " + e);
		} finally {
			try {
				pw.flush(); // flush output stream to file
				pw.close(); // close print writer
			} catch (Exception ignored) {
			}
			try {
				fw.close();
			} catch (Exception ignored) {
			}
		}
	}

}
