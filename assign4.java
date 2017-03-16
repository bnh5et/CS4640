

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
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

		//TODO change file so it's question on one line, corresponding answer on the next
		
		//create question arraylist
		//create answer arraylist
		ArrayList<String> questions = new ArrayList<String>();
		ArrayList<String> answers = new ArrayList<String>();
		
		URL url = new URL("http://plato.cs.virginia.edu/~bnh5et/HW/data/data.txt");
		Scanner scanner = new Scanner(url.openStream());
		
		//count number of lines in the data doc
		int count = 0;
		while (scanner.hasNext())
		{
			count++;
			scanner.next();			
		}
		
		Scanner reader = new Scanner(url.openStream());
		//account for there existing both questions and answers
		count = count / 2;
		//add the questions and answers to question and anser arraylists
		for (int i = 0; i < count; i++)
		{
			questions.add(reader.nextLine());
			answers.add(reader.nextLine());
		}
		
		//now we have all the questions and answers saved
		
		
	
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
		//this is for the submit button
		
		PrintWriter writer = new PrintWriter("submission.txt", "UTF-8");
		//dummy strings for question, answer, row, column, score
		writer.println("Question" + "; " + "Answer" + "; " + "row" + "; " + "column" + "; " + "score" );
		writer.close();
		
		//process form data submission
	    //take the list of questions, answers, rows, columns, scores
		//write these to a text file on your machine
			//can use comma or semicolon to separate each piece of data
		//http://www.cs.virginia.edu/~up3f/cs4640/inclass/simpleform.java
		
		
		
		//create jeopardy game and display it on screen
			//include button that allows user to go back to edit the question
			//just want a grid where each cell shows the score
			//can do a formhandler:  http://www.cs.virginia.edu/~up3f/cs4640/examples/servlet/formHandler.java
		
		
		doGet(request, response);
	}

}
