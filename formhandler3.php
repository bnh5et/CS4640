<html>
<head>
  <title>Simple form handler</title>
</head>

<body bgcolor="#EEEEEE">
  <center><h2>Display Page</h2></center>
  <p>
    Please confirm values or click 'back'
  </p>

  <table cellSpacing=1 cellPadding=1 width="75%" border=1 bgColor="lavender">
    <tr bgcolor="#FFFFFF">
      <td align="center"><strong>Question Type</strong></td>
      <td align="center"><strong>Input</strong></td>
    </tr>
     <tr>
      <td width="20%">Multiple Choice</td> 
      <td><?php if (isset($_POST['colors'])) { echo $_POST['colors']; } ?></td>      
    </tr>
    <tr>
      <td width="20%">T/F</td> 
      <td><?php if (isset($_POST['tf'])) { echo $_POST['tf']; }?></td>      
    </tr>
     <tr>
      <td width="20%">Question</td> 
      <td><?php if (isset($_POST['question'])) { echo $_POST['question']; }?></td>      
    </tr>
    <tr>
      <td width="20%">Answer</td> 
      <td><?php if (isset($_POST['answer'])) { echo $_POST['answer']; }?></td>      
    </tr>

  </table>
    
  <button onClick='window.history.back();' id="backbtn">Back</button>
  <button onClick='confirm();' id="confirm">Confirm</button>

  <script language="javascript">
    
    function confirm()
    {
        
        <?php
        if (isset($_POST['colors']))
        {
            $colors = $_POST['colors'];
            //echo "alert('$colors')";
        } 
        
        addDataToFile("data.txt", $colors);
        ?>
        
        
    }
      
    //we may not need this function:   
      //add data to array
    function extractData()
      {
          $data = array();
          
          //add everything that exists to data array
          //foreach($_POST as )
      }
      
      <?php
      
      function addDataToFile($filename, $data)
      {
       //read data array and append to file  
          $file = fopen($filename, "a");
          //echo "alert('$data')";
          chmod($filename, 0777);
          fputs($file, $data."\n");
          fclose($file);
      }
      
      ?>
    </script> 
    
    
    
</body>
</html> 
