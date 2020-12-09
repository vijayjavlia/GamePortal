<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
try {
	Enumeration headerNames = request.getHeaderNames();
	while(headerNames.hasMoreElements()) {
	   String paramName = (String)headerNames.nextElement();
	   out.print("<tr><td>" + paramName + "</td>\n");
	   String paramValue = request.getHeader(paramName);
	   out.println("<td> " + paramValue + "</td></tr>\n");
	   
	   String Data = paramName+"#"+paramValue;
	   System.out.println(Data);
	   String agent = request.getHeader("user-agent");
	   System.out.println("agent"+agent);
	   
	   }
}
catch (Exception e) {
	e.printStackTrace();
}		

%>
</body>
</html>