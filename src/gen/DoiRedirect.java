package gen;

import static gen.Configurator.getInstance;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DoiRedirect
 */
@WebServlet("/DoiRedirect")
public class DoiRedirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Configurator configurator = getInstance();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoiRedirect() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String resp = request.getQueryString();
		    System.out.println("*****************RESPONSE FROM Operator******************");
		    System.out.println(resp);
		    
		    //Dummy Response
		    //result=ACTIVE&result_id=2&subscription_id=2249586&msisdn=277018694843&channel=WAP_DOI&ext_ref=27614077981_37_mcm2_tm
		    
		    
		    String result = request.getParameter("result");
		    //String result = "ACTIVE";
		    String result_id = request.getParameter("result_id");
		    String subscription_id = request.getParameter("subscription_id");
		    String ani = request.getParameter("msisdn");
		    //String ani = "9878715559";
		    final String countyCode = "27";
            final int len = countyCode.length();
            if (ani.substring(0, len).equals(countyCode)) {
                ani = ani.substring(len);
            }
		    String WAP_DOI = request.getParameter("WAP_DOI");
		    String ext_ref = request.getParameter("ext_ref");
		    
		    if(result.equalsIgnoreCase("active")) {
		    	int count = 0;
		    	Statement stmt = Loader.FGRConn.createStatement();
		    	String chkUser = configurator.getProperty("chkUser");
		    	chkUser = chkUser.replace("<ani>", ani).replace("<servicename>", "Games");
		    	System.out.println(chkUser);
			    ResultSet rs = stmt.executeQuery(chkUser); 
			    if(rs.next()) {
			    	count = rs.getInt(1);
			    }
			    System.out.println("count:"+count);
			    if(count!=0) {
			    	System.out.println("Sending user to home");
			    	HttpSession session = request.getSession();
					session.setAttribute("ani",ani);
			    	response.sendRedirect("Online");
			    }
			    else {
					/*
					 * System.out.println("insert in tbl_sub"); Statement stmtup =
					 * Loader.FGRConn.createStatement(); String instUser =
					 * configurator.getProperty("insertUser"); instUser = instUser.replace("<ani>",
					 * ani).replace("<service_type>", "Games"); System.out.println(instUser);
					 * stmtup.executeUpdate(instUser); request.setAttribute("ani",ani);
					 */
			    	String SDPURL = "http://optin.telkomsdp.co.za/service/35";
			    	response.sendRedirect(SDPURL);
			    }
		    }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
