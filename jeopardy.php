<head>
<meta charset="UTF-8">
<title>Jeopardy</title>
<link rel="stylesheet" type="text/css" href="styles.css">

   <script type="text/javascript">
       //this function should take in the selected item from drop down and make the appropriate response input form visible
       function toggleQuesVisibility(slctd)
       {
           if (slctd.value == "mc")
           {
                document.getElementById('multChoice').style.display = "block";
                document.getElementById('trueFalse').style.display = "none";
                document.getElementById('shortAns').style.display = "none";
           }
           else if (slctd.value == "tf")
           {
                document.getElementById('multChoice').style.display = "none";
                document.getElementById('trueFalse').style.display = "block";
                document.getElementById('shortAns').style.display = "none";
           }
           if (slctd.value == "sa")
           {
                document.getElementById('multChoice').style.display = "none";
                document.getElementById('trueFalse').style.display = "none";
                document.getElementById('shortAns').style.display = "block";
           }
       }
       
       
       //this function tests whether or not text is filled
       function isTextFilled(form)
       {
           cnt = 0;
           msg = "";
           if (form.question.value == "") {
              msg += "No question was entered. \n";
              cnt++;
           }
           if (form.answer.value == "") {
              msg += "No answer was entered. \n";
              cnt++;
           }
           if (cnt > 0) {
              alert ("Please correct the following: \n \n" + msg);
              return false;
           }
           else return true;
       }
       
       //extra function--changes background color
       function toggleBgColor() 
       {  
          if (document.body.style.backgroundColor == "maroon") 
            {
              document.body.style.backgroundColor = "SlateGrey";
              document.getElementById("btn").style.backgroundColor = "maroon";
            }
          else 
            {
              document.body.style.backgroundColor = "maroon";
              document.getElementById("btn").style.backgroundColor = "SlateGrey";
              document.getElementById("btn").style.border = "SlateGrey"; 
            }
       }
       
   </script>

</head>
<body>
<center>
	<div id="mainpage">
		<h1>Let's Play Jeopardy!</h1>	
		<h4>Briana Hart and Samantha Pitcher</h4>

		<p>Choose the type of question that you would like to answer from the dropdown menu below.</p>

        
        <!--spin box with options for type of question-->
		<select name="questions" onchange="toggleQuesVisibility(this);">
			<option selected="selected" disabled>Select</option>
			<option value="mc" >Multiple Choice</option>
			<option value="tf">True/False</option>
			<option value="sa">Short Answer</option>
		</select>

    <br/>
    <br/>
    <!-- background color toggle btn-->
    <button onclick='toggleBgColor();' id="btn">Change <br/>Background Color!</button>   

  </div>

  <div id="shortAns">    
    <!--This is the short answer table with question and answer-->
    <form onSubmit="return isTextFilled(this)" method="post" action="confirmation.php">
      
      Question:
      <br/>
      <input type="text" name="question"/> 
      <br/>
      <br/>

      Answer: 
      <br/>
      <textarea rows="8" cols="50" name="answer"></textarea>
      
      <br/>
      <br/>      

		  <input class="form" type="submit" name="submit" value="Submit">
		  <input class="form" type="reset" name="reset" value="Reset">

	  </form>
  </div>

  <div id="multChoice">
    <form method="post" action="confirmation.php">
      Select your favorite color:
      <div style="box-shadow: 0 0 0 white; margin: 0; padding-left: calc(50% - 60px); text-align: left;">
        <input type="radio" name="colors" value="Blue" checked="checked">Blue
        <br>
        <input type="radio" name="colors" value="Red">Red
        <br/>
        <input type="radio" name="colors" value="Green">Green
        <br/>
        <input type="radio" name="colors" value="Yellow">Yellow
        <br/>
      </div>

      <input class="form" type="submit" name="submit" value="Submit">
      <input class="form" type="reset" name="reset" value="Reset">
    </form>
  </div>

  <div id="trueFalse">
    <form method="post" action="confirmation.php">
      TJ founded UVA.
      <select name="tf">
        <option value="True" selected="selected">True</option>
        <option value="False">False</option>
      </select>
      <br>
      <br>

      <input class="form" type="submit" name="submit" value="Submit">
      <input class="form" type="reset" name="reset" value="Reset">
    </form>
  </div>
</center>
</body>

</html>
