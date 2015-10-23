<?php
header("Content-Type: text/html;charset=utf-8");
$url='http://121.199.48.186:1210/services/msgsend.asmx/SendMsg?userCode=string&userPass=string&DesNo=string&Msg=验证码:XXXX&Channel=0';
$html = file_get_contents($url);
echo $html;
?>