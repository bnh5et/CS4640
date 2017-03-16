

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class assign4
 */
@WebServlet("/assign4")
public class assign4 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public assign4() {
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//read text file that stores questions and answers from previous assignment:
			//read text file from CSLAB Ubuntu server /cslab/home/bnh5et/public_html/projectName

		//create question array
		//create answer array
		
		URL url = new URL("http://plato.cs.virginia.edu/cslab/home/bnh5et/public_html/projectName");
		Scanner scanner = new Scanner(url.openStream());
		
		
		while (scanner.hasNext())
		{
			//save question to question array
			//save answer to answer array
			scanner.next();			
		}
		
		
	
		//display html form that has: 
			//title and team members' names
			//list of questions and answers
			//two fields where user can specify row and column
			//specify score (display this in the given row and column)
			//button that goes back to PHP data entry form from assignment 3 on the CSLAB server
			//"Create Game button"
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//process form data submission
			//list of questions, answers, rows, columns, scores
		
		//write these to a text file on your machine
			//can use comma or semicolon to separate each piece of data
		
		//create jeopardy game and display it on screen
			//include button that allows user to go back to edit the question
			//just want a grid where each cell shows the score
		
		
		
		doGet(request, response);
	}

}
