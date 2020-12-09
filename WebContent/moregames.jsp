<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="gen.DataCollector"%>
<%@page import="gen.Loader"%>
<%@page import="java.sql.ResultSet"%>
	
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Game Portal</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400;500;600;700;800;900&family=Rancho&family=Righteous&family=Sulphur+Point:wght@300;400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/css/all.min.css">
    <!-- Owl CSS -->
    <link rel="stylesheet" href="css/owl.carousel.min.css" />
    <link rel="stylesheet" href="css/owl.theme.default.min.css" />
    <!-- Custom CSS -->
    <link rel="stylesheet" href="css/custom.css" />

</head>
<%
DataCollector getData = new DataCollector();
String cat_id = request.getParameter("cat_id");
String cat_name = request.getParameter("cat_name");
%>
<body>
    <main class="min-vh-100 d-flex flex-column">
        <jsp:include page="header.jsp"></jsp:include>
        <div class="body-wrapper flex-grow-1 pb-5">
            
            
            <div class="container">
    
    <div class="heading d-flex align-items-center">
                        
                            <h1 class="cusheading"><%=cat_name %> Games</h1>
                            
                        </div>
    
     
    <div class="row">
    
    <% 
                      
		              ResultSet rsall=getData.getGames(Loader.FGRConn,cat_id);
					  while(rsall.next())
					   {
						  String gameid = getData.checkValue(rsall.getString(1));
                      	String gamecat = getData.checkValue(rsall.getString(2));
                      	String gamename = getData.checkValue(rsall.getString(3));
                      	String imgurl = getData.checkValue(rsall.getString(5));
                      	String gameurl = getData.checkValue(rsall.getString(6));
					    					   
					    %>
    
    
    	<div class="col-6 col-sm-4 col-md-3 col-lg-2">
    		<a class="single-game text-white text-decoration-none" href="<%=gameurl %>"><img src="<%=imgurl %>" class="img-responsive img-fluid w-100 mb-2" style="height:100px;width:100px">
    		<p><%= gamename %></p>
    		</a>
    	</div>
    	<% 
					   }
					  %> 
    </div>
            
            
        </div>
        </div>
        <footer class="bg-dark py-3">
            <div class="container">
                <div class="row">
                    <div class="col-12 text-center">
                        <p class="mb-0 text-white">&copy; Copyright. All Right Reserved.</p>
                    </div>
                </div>
            </div>
        </footer>
    </main>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <!-- Owl JavaScript -->
    <script src="js/owl.carousel.min.js"></script>
    <!-- Custom JavaScript -->
    <script src="js/custom.js"></script>
</body>
</html>