package gen;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

import gen.Loader;

import static gen.Configurator.getInstance;

/**
 * Servlet implementation class CheckUser
 */

public class CheckUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Configurator configurator = getInstance();
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckUser() {
        
        
    }

	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.getWriter().append("Served at: ").append(request.getContextPath());
			
			
			HttpSession session = request.getSession();
			String ani = (String) session. getAttribute( "ani" );
			//String ani = "9878715559";
			String SDPURL = "http://optin.telkomsdp.co.za/service/35";
			if(isNullOrEmpty(ani)) {
				System.out.println("ANI not in session");
				response.sendRedirect(SDPURL);
			}
			else {
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
			    	session.setAttribute("ani",ani);
			    	response.sendRedirect("Online");
			    }
			    else {
			    	System.out.println("ANI not in DB");
			    	response.sendRedirect(SDPURL);
			    }
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

}
