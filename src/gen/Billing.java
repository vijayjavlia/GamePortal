package gen;

import static gen.Configurator.getInstance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;




/**
 * Servlet implementation class SubscriptionStatus
 */
@WebServlet("/Billing")
public class Billing extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private String CallbackPath = "/home/SDPLOGS/NDOTO_Vodacome/";
	private String CallbackPath = "E:\\Hitesh_genrosys\\VAS\\FGRPortal\\";
	private static Configurator configurator = getInstance();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Billing() {
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
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			
			StringBuffer jb = new StringBuffer();
			String line = null;
			try {
				  	BufferedReader reader = request.getReader();
				  	while ((line = reader.readLine()) != null)
				  		jb.append(line);
			  	} catch (Exception e) {
			  			e.printStackTrace();
			  }
			  	
				/*
				 * CreateFile(jb); String data =
				 * "{\"amount\":300,\"result_name\":\"SUCCESS\",\"result_id\":\"1\",\"billing_type\":\"NORMAL\",\"billing_ref\":137743801,\"subscription\":{\"channel_name\":\"WAP_DOI\",\"billing_rate\":300,\"affiliate_id\":0,\"status_name\":\"ACTIVE\",\"renewal_type\":\"AUTO\",\"created_at\":\"2020-04-21T14:16:03+02:00\",\"next_billing_at\":\"2020-05-18T11:02:12+02:00\",\"bc_id\":188,\"mno_id\":4,\"svc_name\":\"KidZone\",\"svc_id\":70,\"subscription_id\":2732012,\"user_msisdn\":\"9878715559\",\"status_id\":2,\"expires_at\":\"2020-05-18T11:02:12+02:00\",\"updated_at\":\"2020-05-17T11:02:12+02:00\",\"user_id\":1269218,\"billing_cycle\":\"DAILY\",\"last_billed_at\":\"2020-05-17T11:02:12+02:00\",\"subscription_started_at\":\"2020-04-21T14:16:03+02:00\",\"campaign_id\":0,\"ext_ref\":\"27815834678_70_mcm2_tm\"}}";
				 * System.out.println("jb:"+data);
				 * 
				 * final JSONObject jsonObj = new JSONObject(data);
				 */
			  final JSONObject jsonObj = new JSONObject(jb.toString());
			  String result_name = (String)jsonObj.get("result_name");
			  final String insidObj = jsonObj.get("subscription").toString();
			  final JSONObject jsonObjnew = new JSONObject(insidObj);
			  String ani = (String)jsonObjnew.get("user_msisdn");
			  
			  final String countyCode = "27";
	            final int len = countyCode.length();
	            if (ani.substring(0, len).equals(countyCode)) {
	                ani = ani.substring(len);
	            }
	            String last_billed_at = (String)jsonObjnew.get("last_billed_at");
	            String next_billing_at = (String)jsonObjnew.get("next_billing_at");
	            final String channel_name = (String)jsonObjnew.get("channel_name");
	            final String status_name = (String)jsonObjnew.get("status_name");
	            final String svc_name = (String)jsonObjnew.get("svc_name");
	            String subscription_at = (String)jsonObjnew.get("subscription_started_at");
	            int amount = (int)jsonObjnew.get("billing_rate");
	            int campaign_id = (int) jsonObjnew.get("campaign_id");
	            String amt = Integer.toString(amount);
	            String camp_id = Integer.toString(campaign_id);
	            
	            next_billing_at = this.getDateFormat(next_billing_at);
	            last_billed_at = this.getDateFormat(last_billed_at);
	            subscription_at = this.getDateFormat(subscription_at);
	            
	            Statement stmtup = Loader.FGRConn.createStatement();
	            String instQry = configurator.getProperty("insertDLR");
	            instQry = instQry.replace("<ani>", ani).replace("<last_billed_date>", last_billed_at).replace("<next_billed_date>", next_billing_at)
	            		.replace("<channel_name>", channel_name).replace("<status_name>", status_name).replace("<svc_name>", svc_name).replace("<amount>", amt)
	            		.replace("<campaign_id>", camp_id).replace("<type>", "billing").replace("<sub_date_time>", subscription_at)
	            		.replace("<result_name>", result_name);
	            System.out.println(instQry);
		    	stmtup.executeUpdate(instQry);
			  
			  String Content = Response();
			  out.println(Content);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getDateFormat(String date) {
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String dateInString = date;
        final String[] arr = dateInString.split("\\+");
        final String[] newarr = arr[0].split("T");
        System.out.println(newarr[0]);
        final String data = String.valueOf(newarr[0]) + " " + newarr[1];
        System.out.println(data);
        return data;
		
	}

	private void CreateFile(StringBuffer Data) {
		Long FileName = get_Time();
		String Rand = get_rand();
		try {
			File hFile=new File(CallbackPath+FileName+Rand+".txt");
			FileWriter hFileWriter = new FileWriter(hFile, false);
			hFileWriter.write(Data.toString());
			hFileWriter.close();
			System.out.println(Data.toString());
			System.out.println("Text File Created :: "+CallbackPath+FileName+Rand+".txt");
						
						
			File hFilelck=new File(CallbackPath+FileName+Rand+".lck");
			FileWriter hFilelck1 = new FileWriter(hFilelck, false);
			hFilelck1.write("0");
			hFilelck1.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
private static long get_Time() {
		
		long Time1;
		Calendar lCDateTime = Calendar.getInstance();
		Time1=lCDateTime.getTimeInMillis();
		//System.out.println("Time in milliseconds :"+Time1);
	    return Time1;
	}
	
	private static String get_rand(){
		
		
		Random r = new Random();
		int value=r.nextInt(10)+9;
		String rand=String.valueOf(value);
		
		
		return rand;
	}
	
public String Response(){
		
		String response = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
		   		"<soapenv:Body>" +
		   		"<notificationToCPResponse xmlns=\"http://SubscriptionEngine.ibm.com\"/>" +
		   		"</soapenv:Body>" +
		   		"</soapenv:Envelope>";
		return response;
	}

}
