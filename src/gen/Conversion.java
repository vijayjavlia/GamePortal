package gen;

import static gen.Configurator.getInstance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;



import gen.Loader;
import one.xingyi.core.marshelling.JsonObject;

public class Conversion {
	Connection dbconn = Loader.FGRConn;
	String resp = "{status:\"0\",newid;\"0\"}";
	private static Configurator configurator = getInstance();

	private static String get_Time() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(sdf.format(timestamp));

		return sdf.format(timestamp);
	}

	private String get_rand() {
		int check = 0;
		int rand_int1 = 0;
		String rand = "";
		while (check == 0) {
			rand_int1 = Math.abs(ThreadLocalRandom.current().nextInt());
			rand = String.valueOf(rand_int1);
			String get = getClikid(rand, dbconn);
			System.out.println(rand.length());
			System.out.println("getValue" + get);
			if (rand.length() <= 10 && get.equalsIgnoreCase("0")) {

				check++;
			}
		}

		return rand;
	}

	public static void main(String[] args) {
		System.out.println(new Conversion().get_rand());
	}

	public String insertLogs(String clickid, String vendor, String servicename, Connection conn) {
		String newid = "1";
		JSONObject obj = new JSONObject();
		String message = "";

		ResultSet rs = null;
		try {

			// System.out.println(newid);
			String instQry = "insert into tbl_conv_logs(clickid,createddatetime,provider,service) values (?,now(),"
					+ "'"+vendor+"','"+servicename+"')";

			PreparedStatement statement = conn.prepareStatement(instQry, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, clickid);

			statement.executeUpdate();

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					System.out.println("Genereated " + generatedKeys.getLong(1));
					obj.put("status", "1");
					obj.put("newid", generatedKeys.getLong(1));
				} else {
					// throw new SQLException("Creating user failed, no ID obtained.");
					obj.put("status", "0");
					obj.put("newid", "0");
				}
			}

			// System.out.println(instQry);
			resp = obj.toString();
		}

		catch (Exception e) {
			e.printStackTrace();

		}

		return resp;
	}

	public void updateLogs(String id, String ani, String service, String service_status, String url,
			String url_hit_status, Connection conn) {

		try {
			String instQry = "update tbl_conv_logs set svc_name='" + service + "',service_status='" + service_status
					+ "',url_hit_status = '" + url_hit_status + "',modifieddatetime=now(),url='" + url + "',"
					+ "status ='1',ani='" + ani + "' where newid='" + id + "' and status = '0'";
			System.out.println(instQry);

			PreparedStatement statement = conn.prepareStatement(instQry);

			statement.executeUpdate();

			System.out.println(instQry);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public String getClikid(String id, Connection conn) {

		String new_id = null;
		try {
			String Qry = "select clickid from  tbl_conv_logs where newid = '" + id + "'";
			System.out.println(Qry);

			PreparedStatement statement = conn.prepareStatement(Qry);
            ResultSet res = statement.executeQuery();
			if (res.next()) {
				new_id = res.getString(1);
			} else {
				new_id = "0";
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return new_id;
	}
	
	public String getProvider(String id, Connection conn) {

		String provider = null ;
		try {
			String Qry = "select provider from  tbl_conv_logs where newid = '" + id + "'";
			System.out.println(Qry);
			PreparedStatement statement = conn.prepareStatement(Qry);
            ResultSet res = statement.executeQuery();
			if (res.next()) {
				provider = res.getString(1);
			} else {
				provider = "NA";
			}

			
		} catch (Exception e) {
			e.printStackTrace();

		}

		return provider;
	}

	public void updateOptickConv(String id, String ani,  String service, String service_status, Connection conn) {
		String click_id = getClikid(id,conn);
		String provider = getProvider(id,conn);
		System.out.println("click_id:"+click_id);
		System.out.println("provider:"+provider);
		String https_url = "http://track.opticks.io/conversion?key=BB9A442EB351F96FF557185B8DB35A7373A87B388A5B562AF2D647FCC40C7B29&click_id="
				+ click_id;
		System.out.println(https_url);
		URL url;
		try {
			url = new URL(https_url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			System.out.println("****** Content of the URL ********");
			con.setRequestMethod("GET");
			BufferedReader br = null;
			String input;
			String Fininput = "";
			final int result = con.getResponseCode();
			System.out.println("THis is result" + result);
			if (result == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));

			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			updateLogs(id, ani, service, service_status, https_url, Integer.toString(result),conn);
			updateSubscription(ani,provider,id,service,conn);
			updateBilling(ani,provider,service,conn);
			updateOptickTbl(click_id,ani,conn);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void updateBilling(String ani, String provider, String service, Connection conn) {
		try {
			String updtBilling = configurator.getProperty("update_billing_conv");
			updtBilling = updtBilling.replace("<provider>", provider).replace("<ani>", ani).replace("<servicename>", service);
			System.out.println(updtBilling);
			PreparedStatement statement = conn.prepareStatement(updtBilling);
            statement.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void updateOptickTbl(String id, String ani, Connection conn) {
		try {
			String updTbl = configurator.getProperty("update_optick_tbl");
			updTbl = updTbl.replace("<id>", id).replace("<ani>", ani);
			System.out.println(updTbl);
			PreparedStatement statement = conn.prepareStatement(updTbl);
            statement.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void updateSubscription(String ani, String provider, String id, String service, Connection conn) {
		try {
			String updtSub = configurator.getProperty("update_sub_conv");
			updtSub = updtSub.replace("<camp_id>", id).replace("<provider>", provider).replace("<ani>", ani).replace("<servicename>", service);
			System.out.println(updtSub);
			PreparedStatement statement = conn.prepareStatement(updtSub);
            statement.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public String OpticAPIhit(String clickid,String user_agent,String x_forwarded,String servicename, Connection conn) {
		try {
			String Authorization = "ak328ae0ea18782fae01b251838549e5";
			   
			   String URL = "https://api.optickssecurity.com/analysis";
			   DefaultHttpClient httpClient = new DefaultHttpClient();
			   HttpPost postRequest = new HttpPost(URL);
			   postRequest.setHeader("Content-Type", "application/json");
			   postRequest.setHeader("Authorization", ""+Authorization+"");
			   
			   JSONObject obj = new JSONObject();
			   JSONObject obj1 = new JSONObject();
			   obj.put("id", clickid);
			   //obj.put("msisdn", "");
			   obj1.put("User-Agent", user_agent);
			   obj1.put("x-forwarded-for", x_forwarded);
			   obj.put("headers", obj1);
			   
			   StringEntity input = new StringEntity(obj.toString());
			   postRequest.setEntity(input);
			   System.out.println("input~~"+obj.toString());
			   
			   HttpResponse response = httpClient.execute(postRequest);
			   BufferedReader br = new BufferedReader(new InputStreamReader(
			     (response.getEntity().getContent())));
			   String output;
			   boolean isValid = true;
			   System.out.println("Output from Server ....");
			   while ((output = br.readLine()) != null) {
				    System.out.println("output:"+output);
				    final JSONObject jsonObj = new JSONObject(output);
				    isValid = (boolean)jsonObj.get("isValid");
				    System.out.println("isValid:"+isValid);
				    
				    Statement stmtup = Loader.FGRConn.createStatement();
		            String instQry = configurator.getProperty("OpticksLogging");
		            instQry = instQry.replace("<click_id>", clickid).replace("<isValid>", Boolean.toString(isValid))
		            		.replace("<reqJson>", obj.toString()).replace("<respJson>", output).replace("<servicename>", servicename);
		            System.out.println(instQry);
			    	stmtup.executeUpdate(instQry);
			   }
			   resp = Boolean.toString(isValid);
			   //resp = "TRUE";
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

}
