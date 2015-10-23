<%
Dim url,xmlhttp,dom,node,xmlDOC,userCode,userPass,DesNo,Msg,Channel
userCode="XXX"
userPass="XXXX"
DesNo="XXXX"
Msg="您好，您的校验码是1867【XX】"
	Channel="0"
	SoapRequest="<?xml version="&CHR(34)&"1.0"&CHR(34)&" encoding="&CHR(34)&"utf-8"&CHR(34)&"?>"& _
	"<soap:Envelope xmlns:xsi="&CHR(34)&"http://www.w3.org/2001/XMLSchema-instance"&CHR(34)&" "& _
	"xmlns:xsd="&CHR(34)&"http://www.w3.org/2001/XMLSchema"&CHR(34)&" "& _
	"xmlns:soap="&CHR(34)&"http://schemas.xmlsoap.org/soap/envelope/"&CHR(34)&">"& _
	"<soap:Body>"& _
	"<SendMsg xmlns="&CHR(34)&"http://tempuri.org/"&CHR(34)&">"& _
	"<userCode>"&userCode&"</userCode>"& _
	"<userPass>"&userPass&"</userPass>"& _
	"<DesNo>"&DesNo&"</DesNo>"& _
	"<Msg>"&Msg&"</Msg>"& _
	"<Channel>"&Channel&"</Channel>"& _
	"</SendMsg>"& _
	"</soap:Body>"& _
	"</soap:Envelope>"
url="http://121.199.48.186:1210/services/msgsend.asmx?op=SendMsg"	'发送短信，如果调用其他方法，请修改上面对应的参数
Set xmlDOC =CreateObject("MSXML.DOMDocument")
xmlDOC.loadXML(SoapRequest)
Set xmlhttp = CreateObject("Msxml2.XMLHTTP")
xmlhttp.Open "POST",url,false
xmlhttp.setRequestHeader "Content-Type", "text/xml;charset=utf-8"
xmlhttp.setRequestHeader "SOAPAction", "http://tempuri.org/SendMsg"
xmlhttp.setRequestHeader "Content-Length",LEN(SoapRequest)
xmlhttp.Send(xmlDOC)
response.write  "status = "&xmlhttp.Status&"<br>"
response.write  "statustxt = "&xmlhttp.StatusText&"<br>"
response.write  "responseText = "&xmlhttp.responseText&"<br>"
If xmlhttp.Status = 200 Then 'ok responseXML
 xmlDOC.load(xmlhttp.responseXML)
 response.Write(xmlDOC.xml)
else
 response.write  "failed"
end if
%>


