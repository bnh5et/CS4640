<html>
<head>
<title>Jeopardy</title>
<link rel="stylesheet" type="text/css" href="styles.css">
  <script type="text/javascript">
    submitForm() {
      document.forms['submitForm'].action='confirmation.php';
      document.forms['submitForm'].submit();
      document.forms['submitForm'].action='assign4';
      document.forms['submitForm'].submit();
    }
  </script>

</head>

<body bgcolor="#EEEEEE">
  <div>
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
    
  <form action=confirmation.php method="post" name="submitForm">
    <a href="http://plato.cs.virginia.edu/~bnh5et/HW/jeopardy.php" style="width: 100px;">Back</a>
    
    <!--Needs to navigate to htmlInput-->
    <input class="form" onclick="submitForm();" name="confirm" value="Confirm"></input>
  </form>
  </center>
  </div>
</body>


</html>

<?php
  $data = "";

  if(isset($_POST['colors']))
    $data = "What's your favorite color? " . "\n" . $_POST['colors'] . "\n";

  if(isset($_POST['tf']))
    $data = "Did TJ found UVA?" . "\n". $_POST['tf'] . "\n";

  if (isset($_POST['question'])) 
    $data = $_POST['question'] . "\n" . $_POST['answer'] . "\n";

  $file = fopen("data/data.txt", "a+");
  fwrite($file, $data);
  chmod("data/data.txt", 0777);
  fclose($file);

?>

