<html>
<head>
  <title>Jeopardy</title>
</head>

<body bgcolor="#EEEEEE">
  <center><h2>Display Page</h2></center>
  <p>
    Please confirm values or click 'back'
  </p>
  <center>
  <table cellPadding=5 width="75%" border=1 bgColor="lavender">
    <tr bgcolor="#FFFFFF">
      <td align="center"><strong>Question Type</strong></td>
      <td align="center"><strong>Input</strong></td>
    </tr>
     <tr>
      <td width="50%">Multiple Choice</td> 
      <td name="colors">
        <?php 
          $colors = "";
          if (isset($_POST['colors'])) 
          { 
            global $colors;
            $colors = $_POST['colors'];
            echo $colors; 
          } 
        ?>
      </td>    
    </tr>
    <tr>
      <td width="50%">T/F</td> 
      <td>
        <?php
          $tf = ""; 
          if (isset($_POST['tf'])) 
          { 
            global $tf;
            $tf = $_POST['tf'];
            echo $tf; 
          }
        ?>
      </td>      
    </tr>
     <tr>
      <td width="50%">Question</td> 
      <td>
        <?php 
          $question = "";
          if (isset($_POST['question'])) 
          { 
            global $question;
            $question = $_POST['question'];
            echo $question; 
          }
        ?>
      </td>      
    </tr>
    <tr>
      <td width="50%">Answer</td> 
      <td>
        <?php 
          $answer = "";
          if (isset($_POST['answer'])) 
          { 
            global $answer;
            $answer = $_POST['answer'];
            echo $answer; 
          }
        ?>
      </td>     
    </tr>
  </table>
  <p>
    
  <form action=formhandler3.php method="post">
    <button onClick='window.history.back();' id="backbtn">Back</button>
    <input type="submit" name="confirm" value="Confirm"></input>
  </form>
  </center>
</body>
</html>

<?php
  $data = "";

  if(isset($_POST['colors']))
    $data = "Your favorite color is: ". $_POST['colors'] . "\n";

  if(isset($_POST['tf']))
    $data = "TJ founded UVA: " . $_POST['tf'] . "\n";

  if (isset($_POST['question'])) 
    $data = "Question: " . $_POST['question'] . "\n" . "Answer: " . $_POST['answer'] . "\n";

  $file = fopen("data.txt", "a+");
  fwrite($file, $data);
  chmod("data.txt", 0777);
  fclose($file);

?>

