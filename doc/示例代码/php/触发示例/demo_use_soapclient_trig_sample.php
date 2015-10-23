<?php
//此处仅示例发送短信，其他可类推
header("Content-type: text/html; charset=utf-8");
$client = new SoapClient("http://121.199.48.186:1210/Services/MsgSend.asmx?WSDL");
$param = array("userCode"=>"XXXX","userPass"=>"XXXXX","DesNo"=>"XXX","Msg"=>"test【签名】","Channel"=>"1");
$p = $client->__soapCall('SendMsg',array('parameters' => $param));
print_r($p);
?>
