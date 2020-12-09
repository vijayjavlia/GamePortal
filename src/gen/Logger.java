package gen;

import static gen.Configurator.getInstance;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Logger
 */
@WebServlet("/Logger")
public class Logger extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Configurator configurator = getInstance();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logger() {
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
			PrintWriter out = response.getWriter();
			HttpSession session = request.getSession();
			String ani = (String) session. getAttribute( "ani" );
			String gameurl = (String) session. getAttribute( "gameurl" );
			System.out.println("ani in Logger"+ani);
			
			int count = 0;
			Statement stmt = Loader.FGRConn.createStatement();
			String chkUser = configurator.getProperty("chkUser");
	    	chkUser = chkUser.replace("<ani>", ani).replace("<servicename>", "Games");
	    	System.out.println(chkUser);
		    ResultSet rs = stmt.executeQuery(chkUser);
		    if(rs.next()) {
		    	count = rs.getInt(1);
		    }
			if(count!=0) {
				Statement stmtup = Loader.FGRConn.createStatement();
				String instLog = configurator.getProperty("logging");
				instLog = instLog.replace("<ani>", ani).replace("<gameurl>", gameurl);
		    	System.out.println(instLog);
		    	stmtup.executeUpdate(instLog);
		    	
		    	response.sendRedirect(gameurl);

			}
			else {
				System.out.println("Wait for charging");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
