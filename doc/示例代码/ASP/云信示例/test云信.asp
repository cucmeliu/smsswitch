<%
Dim url,xmlhttp,dom,node,xmlDOC,userCode,userPass,DesNo,Msg,Channel
	userCode="XXXX"
	userPass="XXX"
	DesNo="13817000008"
	Msg="wwwww¡¾ts¡¿"
	Channel="41"
	SoapRequest="<?xml version="&CHR(34)&"1.0"&CHR(34)&" encoding="&CHR(34)&"utf-8"&CHR(34)&"?>"& _
	"<soap:Envelope xmlns:xsi="&CHR(34)&"http://www.w3.org/2001/XMLSchema-instance"&CHR(34)&" "& _
	"xmlns:xsd="&CHR(34)&"http://www.w3.org/2001/XMLSchema"&CHR(34)&" "& _
	"xmlns:soap="&CHR(34)&"http://schemas.xmlsoap.org/soap/envelope/"&CHR(34)&">"& _
	"<soap:Body>"& _
	"<sendMes xmlns="&CHR(34)&"http://tempuri.org/"&CHR(34)&">"& _
	"<userCode>"&userCode&"</userCode>"& _
	"<userPass>"&userPass&"</userPass>"& _
	"<DesNo>"&DesNo&"</DesNo>"& _
	"<Msg>"&Msg&"</Msg>"& _
	"<Channel>"&Channel&"</Channel>"& _
	"</sendMes>"& _
	"</soap:Body>"& _
	"</soap:Envelope>"
    response.write SoapRequest
url="http://yes.itissm.com/api/MsgSend.asmx?op=sendMes"	
Set xmlDOC =CreateObject("MSXML.DOMDocument")
xmlDOC.loadXML(SoapRequest)
Set xmlhttp = CreateObject("Msxml2.XMLHTTP")
xmlhttp.Open "POST",url,false
xmlhttp.setRequestHeader "Content-Type", "text/xml;charset=utf-8"
xmlhttp.setRequestHeader "SOAPAction", "http://tempuri.org/sendMes"
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


