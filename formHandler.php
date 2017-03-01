<html>
<head>
  <title>Simple form handler</title>
</head>

<body bgcolor="#EEEEEE">
  <center><h2>Simple Form Handler</h2></center>
  <p>
    The following table lists all parameter names and their values that were submitted from your form.
  </p>

  <table cellSpacing=1 cellPadding=1 width="75%" border=1 bgColor="lavender">
    <tr bgcolor="#FFFFFF">
      <td align="center"><strong>Question Type</strong></td>
      <td align="center"><string>Input</string></td>
    </tr>......
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

</body>
</html> 
