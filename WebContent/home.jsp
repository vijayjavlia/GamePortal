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
String cat_id="",cat_name="";
System.out.println("ani in home page:"+(String) session. getAttribute( "ani" ));
%>
<body>
    <main class="min-vh-100 d-flex flex-column">
        <jsp:include page="header.jsp"></jsp:include>
        <div class="body-wrapper flex-grow-1 pb-5">
            <section class="big-slide py-4 mb-5">
                <div class="container-fluid">
                    <div class="bigslide-inner">
                        <div class="heading d-flex align-items-center">
                            <h1 class="cusheading">Hightlights</h1>
                            <a href="#" class="btn btn-yellow">More</a>
                        </div>
                        <div class="owl-carousel owl-theme">
                            <div class="item">
                                <a href="#">
                                    <img src="images/BoomBaseball.png" alt="Hightlights Games" class="img-fluid" />
                                </a>
                            </div>
                            <div class="item">
                                <a href="#">
                                    <img src="images/PenaltyShootout.png" alt="Hightlights Games" class="img-fluid" />
                                </a>
                            </div>
                            <div class="item">
                                <a href="#">
                                    <img src="images/ReverseGravity.png" alt="Hightlights Games" class="img-fluid" />
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            
            <section class="small-slide py-3">
                <div class="container-fluid">
                    <div class="small-slideinner">
                        <div class="heading d-flex align-items-center">
                            <h1 class="cusheading">Recently</h1>
                            <a href="recent.jsp" class="btn btn-yellow">More</a>
                        </div>
                        <div class="owl-carousel owl-theme">
                        <%
                        ResultSet rr  = null;
                        rr = getData.getGamesRecent(Loader.FGRConn, "slide");
                        while(rr.next()){
                        	String gameid = getData.checkValue(rr.getString(1));
                        	String gamecat = getData.checkValue(rr.getString(2));
                        	String gamename = getData.checkValue(rr.getString(3));
                        	String imgurl = getData.checkValue(rr.getString(5));
                        	String gameurl = getData.checkValue(rr.getString(6));
                        	session.setAttribute("gameurl",gameurl);
                        	
                        %>
                            <div class="item">
                                <a href="Logger">
                                    <img src="<%=imgurl %>" alt="Hightlights Games" class="img-fluid" />
                                    <h4><%=gamename %></h4>
                                </a>
                            </div>
                            <%
                        }
                            %>
                        </div>
                    </div>
                </div>
            </section>
            
            <%
                                ResultSet rgc  = null;
                                rgc = getData.getGameCategory(Loader.FGRConn);
                                while(rgc.next()){
                                	cat_name = getData.checkValue(rgc.getString(1));
                                	cat_id = getData.checkValue(rgc.getString(2));
                         %>

            <section class="small-slide py-3">
                <div class="container-fluid">
                    <div class="small-slideinner">
                        <div class="heading d-flex align-items-center">
                        
                            <h1 class="cusheading"><%=getData.checkValue(rgc.getString(1)) %> Games</h1>
                            <a href="moregames.jsp?cat_id=<%=cat_id %>&cat_name=<%=cat_name %>" class="btn btn-yellow">More</a>
                        </div>
                        <div class="owl-carousel owl-theme">
                        <%
                        ResultSet rgd  = null;
                        rgd = getData.getGames(Loader.FGRConn, cat_id);
                        while(rgd.next()){
                        	String gameid = getData.checkValue(rgd.getString(1));
                        	String gamecat = getData.checkValue(rgd.getString(2));
                        	String gamename = getData.checkValue(rgd.getString(3));
                        	String imgurl = getData.checkValue(rgd.getString(5));
                        	String gameurl = getData.checkValue(rgd.getString(6));
                        	session.setAttribute("gameurl",gameurl);
                        %>
                            <div class="item">
                                <a href="Logger">
                                    <img src="<%=imgurl %>" alt="Hightlights Games" class="img-fluid" />
                                    <h4><%=gamename %></h4>
                                </a>
                            </div>
                            <%
                        }
                            %>
                        </div>
                        
                    </div>
                </div>
            </section>
            <%
                                }
                        %>
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