package gen;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gen.Loader;
import gen.Parameter;

/**
 * Servlet implementation class ApiServlet
 */
@WebServlet("/ApiServlet")
public class ApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST,GET");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
		}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		setAccessControlHeaders(response);

		String result= "{\"status\":\"0\",\"error\":\"action not defined\"}";

		try {
			PrintWriter out = response.getWriter();
			Parameter objParameter = new Parameter();
				
			DataCollector listener = new DataCollector();

			objParameter.setAction(request.getParameter("action"));

			if(objParameter.getAction().equalsIgnoreCase("1")) {
				
				result = new Conversion().insertLogs(request.getParameter("cid"),request.getParameter("vendor"),
						request.getParameter("servicename"), Loader.FGRConn);
				System.out.println("This is result :: "+result);

			}else if  (objParameter.getAction().equalsIgnoreCase("2")) {
				result = new Conversion().OpticAPIhit(request.getParameter("cid"),request.getParameter("usr_agt"),
						request.getParameter("xff"),request.getParameter("servicename"), Loader.FGRConn);
				System.out.println("This is result :: "+result);
			}
			out.print(result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
