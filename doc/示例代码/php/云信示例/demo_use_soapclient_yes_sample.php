<?php
//此处仅示例发送短信，其他可类推
header("Content-type: text/html; charset=utf-8");
$client = new SoapClient("http://yes.itissm.com/api/MsgSend.asmx?WSDL");
$param = array("userCode"=>"XXXX","userPass"=>"XXXXX","DesNo"=>"XXX","Msg"=>"test【签名】","Channel"=>"33");//注意channel要根据文档里面说的去平台上获取
$p = $client->__soapCall('SendMes',array('parameters' => $param));
print_r($p);
?>
