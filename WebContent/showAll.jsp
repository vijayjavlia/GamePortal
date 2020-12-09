<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@page import="gen.*"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.*"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<!DOCTYPE HTML>
<%
   String ani = (String) session.getAttribute("ani");
   String status = new DataCollector().getStatus(ani);
   String catid = request.getParameter("_id");
   /* if(ani== null){
   	response.sendRedirect("/landing");
   }else if(status.equalsIgnoreCase("2")||status.equalsIgnoreCase("0")){
   	response.sendRedirect("/Login?number="+ani+"");
   } */
   %>
<%
   OnlineGames online = new OnlineGames();
   %>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <meta name="viewport"
      content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0 minimal-ui" />
   <meta name="apple-mobile-web-app-capable" content="yes" />
   <meta name="apple-mobile-web-app-status-bar-style" content="black">
   <title>Celebrity Home</title>
   <link rel="stylesheet" type="text/css" href="styles/style.css">
   <link rel="stylesheet" type="text/css" href="styles/skin.css">
   <link rel="stylesheet" type="text/css" href="styles/framework.css">
   <link rel="stylesheet" type="text/css" href="styles/ionicons.min.css">
   <link
      href="https://fonts.googleapis.com/css?family=Quicksand:300,400,500,700"
      rel="stylesheet">
   <script type="text/javascript" src="scripts/jquery.js"></script>
   <script type="text/javascript" src="scripts/plugins.js"></script>
   <script type="text/javascript" src="scripts/custom.js"></script>
   <style>
      
   </style>
</head>
<%
   DataCollector coll = new DataCollector();
   %>
<body>
   <div id="page-transitions">
      <div class="page-preloader page-preloader-dark">
         <div class="spinner"></div>
      </div>
      <jsp:include page="nav.jsp" />
      <!-- Main Small Icon Sidebar -->
      <div class="sidebar-menu sidebar-dark">
         <jsp:include page="siderbar.jsp" />
      </div>
      <div id="page-content" class="header-clear-large">
         <div id="page-content-scroll">
            <!--Enables this element to be scrolled -->
            <div class="bigslides">
               <div class="swiper-container cusslides">
                  <div class="swiper-wrapper">
                     <%
                        ResultSet res3 = coll.getHighlightGames();
                        while (res3.next()) {
                        String gameurl = res3.getString("gameurl");
                        String type = res3.getString("category");
                        String gameid = res3.getString("gameid");
                        String cat_id = res3.getString("cat_id");
                        String imgurl = res3.getString("banner_url");
                        %>
                     <div class="swiper-slide">
                        <a
                           href="<%="/OnlineLogger?ani=" + ani + "&url=" + gameurl + "&type=" + type + "&gameid="
                              + gameid + "&cat_id=" + cat_id + "&imgurl= "+imgurl+" "%>"
                           class="column-center-image"> <img class="col-img-2 img"
                           src="<%=imgurl%>" alt="img">
                        </a>
                        <%-- <p class="video_title"><%=res3.getString("gamename")%></p> --%>
                     </div>
                     <%
                        }
                        res3.close();
                        %>
                  </div>
                  <!-- Add Pagination -->
                  <div class="swiper-pagination"></div>
               </div>
            </div>
            <div class="staff-slider tiles-row">
               <div class="cus-heading">Games</div>
               <div class="swiper-wrapper1">
                  <%
                     String getGames = online.getgameByCatid(catid);
                     		JSONObject jsonObjNew = new JSONObject(getGames);
                     		String responseCode1 = jsonObjNew.get("code").toString();
                     		if (responseCode1.equalsIgnoreCase("200")) {
                     			JSONArray arr1 = jsonObjNew.getJSONArray("data");
                     			String value = "0";
                     			
                     			for (int j = 0; j < arr1.length(); j++) {
                     
                     					String gameurl = arr1.getJSONObject(j).getString("url");
                     					String type = arr1.getJSONObject(j).getString("category");
                     					String thumbnailUrl = arr1.getJSONObject(j).getString("thumbnailUrl");
                     					String title = arr1.getJSONObject(j).getString("title");
                     					String gameid = arr1.getJSONObject(j).getString("id");
                     %>
                  <div class="swiper-slide">
                     <a href="<%="/OnlineLogger?ani=" + ani + "&url=" + gameurl + "&type=" + type + "&gameid="
                        + gameid + "&cat_id=" + catid + "&imgurl= "+thumbnailUrl+""%>"
                        class="column-center-image"> <img class="col-img-2 img"
                        src="<%=thumbnailUrl%>" alt="img">
                     </a>
                     <p class="video_title"><%=title%></p>
                  </div>
                  <%
                     }
                     		}	
                     		
                     		
                     %>
               </div>
            </div>
            <footer>
               <div class="container">
                  <div class="row">
                     <div class="col-md-12">
                        <p>&copy; copyright All Right Reserved.</p>
                     </div>
                  </div>
               </div>
            </footer>
            <div class="clear"></div>
         </div>
      </div>
      <a href="#" class="back-to-top-badge"><i
         class="ion-android-arrow-dropup"></i>Back to Top</a>
   </div>
   <script>
      var swiper = new Swiper('.cusslides', {
      	spaceBetween : 30,
      	autoplay : 4000,
      	slidesPerView : 1,
      	// autoplay: {
      	//     delay: 2500,
      	//     disableOnInteraction: false,
      	// },
      	pagination : {
      		el : '.swiper-pagination',
      		clickable : true,
      	},
      });
   </script>
</body>