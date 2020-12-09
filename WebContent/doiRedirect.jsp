<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<%

%>
<%@page import="gen.CheckUser"%>
<%@page import="gen.*"%>
<%@page import="javax.xml.bind.DatatypeConverter"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%
	try {
		String ani = "";
		ani = request.getParameter("msisdn");

		String status = new DataCollector().getStatus(ani);

		String countyCode = "27";
		int len = countyCode.length();
		if (ani.substring(0, len).equals(countyCode))
			ani = ani.substring(len);
		if (ani.startsWith("0"))
			ani = ani.substring(1);

		System.out.println("This is form query paramete === " + ani);

		//String status = objgetgame.getStatus(ani);
		if (status.equalsIgnoreCase("1")) {
			String reDirectURL = "index.jsp?ani=" + ani + "";
			response.sendRedirect(reDirectURL);
			System.out.println("Status for sub user: " + status);
		} else {
			out.println("<script type=\"text/javascript\">");
			out.println("location='join.jsp';");
			out.println("</script>");
			System.out.println("User not Sub");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
%>

<html>
	<head>
		

		<title>HappyTubeGames</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		<br>
	</body>
</html>
