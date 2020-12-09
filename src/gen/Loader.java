package gen;

import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class Loader
 */
@WebServlet("/Loader")
public class Loader extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Connection NDOTConn = null;
	public static Connection FGRConn = null;
	public static Connection PartnerConn = null;
	public static Connection FgrHttSeries = null;
	//public static Connection Test = null;
	public static Connection Vodacom = null;

	public static Connection FGRHappyConn = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Loader() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException {
        DBConnection db = new DBConnection();
        FGRConn = db.getDatabse();
        System.out.println("Db Connected in init first time");
    }
}
