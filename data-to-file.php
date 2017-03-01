<?php
  $data = "";

  echo "Hello!";

  if(isset($_POST['colors']))
    $data = $_POST['colors'];
    echo $data;

  if(isset($_POST['tf']))
    $data = $_POST['tf'];
    echo $data;

  if (isset($_POST['question'])) 
    $data = "Question: " . $_POST['question'] . "\n" . "Answer: " . $_POST['answer'];
    echo $data;

  $file = fopen("data.txt", "a+");
  fwrite($file, $data);
  chmod("data.txt", 0777);
  fclose($file);

?>
<!--<meta http-equiv="refresh" content="0; 
url=http://localhost/CS4640/brianahart.html" />