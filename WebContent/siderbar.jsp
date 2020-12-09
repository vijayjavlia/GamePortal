<%@page import="gen.*"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.*"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>

<%
    String ani = (String) session.getAttribute("ani");
	OnlineGames online = new OnlineGames();
%>
<div class="sidebar-menu-scroll">
	
	 <a class="current-menu" href="/Online"><img src="" /><em>Home</em></a>
	 <%
						String Fininput = online.getCategories();
						JSONObject jsonObj = new JSONObject(Fininput);
						String responseCode = jsonObj.get("code").toString();
						List<String> gamesArray = new ArrayList<String>();
						int count = 0;
						if (responseCode.equalsIgnoreCase("200")) {
							JSONArray arr = jsonObj.getJSONArray("data");
							for (int i = 0; i < arr.length(); i++) {
								int catValue=0;
								String catid = arr.getJSONObject(i).getString("id");

								String name = arr.getJSONObject(i).getString("name");
								//System.out.println("Catname -------- "+name+"-----"+catid);
								String getGames = online.getgameByCatid(catid);
										JSONObject jsonObjNew = new JSONObject(getGames);
										String responseCode1 = jsonObjNew.get("code").toString();
										if (responseCode1.equalsIgnoreCase("200")) {
											JSONArray arr1 = jsonObjNew.getJSONArray("data");
											String value = "0";
											System.out.println("arr1:"+arr1.length());
											if(arr1.length() == 0){}
											else {
												%>
												<a href="/showAll.jsp?_id=<%=catid %>"><img src="" /><em><%=name%></em></a>
												<%
											}
										}
							}
						}
					%>
			

</div>