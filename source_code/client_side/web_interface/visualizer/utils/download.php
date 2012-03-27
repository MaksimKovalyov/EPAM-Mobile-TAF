<?php
    $data = base64_decode($_POST["imageData"], true);
    
    if (!$data) {
        exit("Invalid data format!");
    }

    $prefix = (empty($_POST["namePrefix"])) ? "screenshot" : $_POST["namePrefix"];
    $format = (empty($_POST["fileFormat"])) ? "png" : $_POST["fileFormat"];
    $timestamp = date("d.m.y_H.i.s");
    

    header("Cache-Control: no-cache"); 
    header("Content-type: image/$format");
    header("Content-Disposition: attachment; filename=$prefix\x5F$timestamp.$format");
    header("Transfer-Encoding: gzip");
    header("Connection: close");

    echo $data;
?>