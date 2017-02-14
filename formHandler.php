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
      <td align="center"><strong>Parameter</strong></td>
      <td align="center"><string>Value</string></td>
    </tr>
    <tr>
      <td width="20%">MAILTO</td> 
      <td><?php echo $_POST['MAILTO']?></td>      
    </tr>
    <tr>
      <td width="20%">FName</td> 
      <td><?php echo $_POST['FName']?></td>      
    </tr>
    <tr>
      <td width="20%">LName</td>
      <td><?php echo $_POST['LName']?></td>      
    </tr>
    <tr>
      <td width="20%">Major</td>
      <td><?php echo $_POST['Major']?></td>      
    </tr>
    <tr>
      <td width="20%">OtherMajor</td>
      <td><?php echo $_POST['OtherMajor']?></td>      
    </tr>
    <tr>
      <td width="20%">EmailAddress</td>
      <td><?php echo $_POST['EmailAddress']?></td>      
    </tr>
    <tr>
      <td width="20%">Phone</td>
      <td><?php echo $_POST['Phone']?></td>      
    </tr>
    <tr>
      <td width="20%">WebSiteURL</td>
      <td><?php echo $_POST['WebSiteURL']?></td>      
    </tr>
    <tr>
      <td width="20%">LevelHTML</td>
      <td><?php echo $_POST['LevelHTML']?></td>      
    </tr>
    <tr>
      <td width="20%">LevelJava</td>
      <td><?php echo $_POST['LevelJava']?></td>      
    </tr>
    <tr>
      <td width="20%">LevelJS</td>
      <td><?php echo $_POST['LevelJS']?></td>      
    </tr>
    <tr>
      <td width="20%">LevelServlets</td>
      <td><?php echo $_POST['LevelServlets']?></td>      
    </tr>
    <tr>
      <td width="20%">LevelJSP</td>
      <td><?php echo $_POST['LevelJSP']?></td>      
    </tr>
    <tr>
      <td width="20%">NewOrUpdate</td>
      <td><?php echo $_POST['NewOrUpdate']?></td>      
    </tr>
    <tr>
      <td width="20%">submit</td>
      <td><?php echo $_POST['submit']?></td>      
    </tr>    
  </table>

</body>
</html> 