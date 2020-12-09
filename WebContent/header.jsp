<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="gen.DataCollector"%>
<%@page import="gen.Loader"%>
<%@page import="java.sql.ResultSet"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<%
DataCollector getData = new DataCollector();
String cat_id="",cat_name="";
%>
<body>
<header>
            <nav class="navbar navbar-expand-lg navbar-light">
                <div class="container">
                    <a class="navbar-brand" href="#"><img src="images/logo_gameit.png" alt="logo" class="img-fluid" /></a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav ml-auto">
                            <li class="nav-item active">
                                <a class="nav-link" href="home.jsp">Home</a>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Dropdown
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <%
                                ResultSet res  = null;
                                res = getData.getGameCategory(Loader.FGRConn);
                                while(res.next()){
                                	cat_name = getData.checkValue(res.getString(1));
                                	cat_id = getData.checkValue(res.getString(2));
                                %>
                                    <a class="dropdown-item" href="moregames.jsp?cat_id=<%=cat_id %>&cat_name=<%=cat_name %>"><%=getData.checkValue(res.getString(1)) %></a>
                                    
                                    <%
                                }res.close();
                                    %>
                                </div>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">Contact Us</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
</body>
</html>