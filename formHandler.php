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
    <!--<tr>
      <td width="20%">MAILTO</td> 
      <td><?php echo $_POST['MAILTO']?></td>      
    </tr>-->
    <tr>
      <td width="20%">submit</td>
      <td><?php echo $_POST['submit']?></td>      
    </tr>    
  </table>

</body>
</html> 