<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Games</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>
<%
String user_agent = request.getHeader("user-agent");
String x_forwarded_for = request.getHeader("x-forwarded-for");
System.out.println("Header user agent:::"+user_agent);
System.out.println("Header x_forwarded_for:::"+x_forwarded_for);
%>
<script>
check();
	
	
 function getUrlParameter(sParam) {
	var sPageURL = window.location.search.substring(1), sURLVariables = sPageURL
			.split('&'), sParameterName, i;
	console.log(sPageURL);
	for (i = 0; i < sURLVariables.length; i++) {
		sParameterName = sURLVariables[i].split('=');

		if (sParameterName[0] === sParam) {
			return sParameterName[1] === undefined ? true
					: decodeURIComponent(sParameterName[1]);
		}
	}
}  
	
	function check(){
		
		console.log('here')
		
		var cidN = getUrlParameter('cid');
		var svc = getUrlParameter('svc');
		console.log(svc)
		if(svc == 'Battle'){
			svc = 'cashbattle';
		}
		//var cidN = 'track_20201022070234_ea707c5a_150e_4351_807b_259822de7a8b';
		if(cidN === undefined || cidN === null){
			return;
		}
		var ua = '<%=user_agent %>';
		var xff = '<%=x_forwarded_for %>';
		var jsonOPT = {action:'2',cid:cidN,usr_agt:ua,xff:xff,servicename:svc};
		$.ajax({
			type:"POST",
			url:"/ApiServlet",
			data: jsonOPT,
			success:function(resultOPT){
				console.log(resultOPT)
				if(resultOPT == 'true'){
					var jsonR ={action:'1',cid:cidN,vendor:'CPA',servicename:svc};
					$.ajax({
							type: "POST",
					        url: "/ApiServlet",
					        data: jsonR,
					       success:function(result){
					    	   console.log(result);
					    	   	var JsonR = JSON.parse(result);
					    	   	var status = JsonR.status;
					    	   	if(status == '1'){
					    	   		var gameid = "";
					    	   		if(svc == 'Games'){
					    	   			gameid = '35';
					    	   		}
					    	   		else if (svc == 'cashbattle'){
					    	   			gameid = '67';
					    	   		}
					    	   		else if (svc == 'SVOD'){
					    	   			gameid = '37';
					    	   		}
					    	   		window.location = "http://optin.telkomsdp.co.za/service/"+gameid+"?cid="+JsonR.newid;
					    	   	}
								
					       }
					});
				}
				else {
					console.log('isValid in jsp','false');
				}
			}
		});
	}
	
</script>
</body>
</html>,