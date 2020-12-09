package gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataCollector {
	String response = "{\"status\":\"0\",\"message\":\"something went wrong!!\",\"data\":{}}";
	
	public String checkValue(String val) {
		if(val == null) {
			val = "";
		}
		
		return val;
	}
	
	public ResultSet getGameCategory(Connection conn) {
		ResultSet res = null;
		try {
			String query = "select cat_name,sr_no from tbl_game_cat where status='1'";
			System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			res =  ps.executeQuery();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}
	
	public ResultSet getGames(Connection conn, String cat_id) {
		ResultSet res = null;
		try {
			String query = "select gameid,category,gamename,cat_id,imgurl,gameurl from tbl_portal_game where cat_id='"+cat_id+"' and status='1'";
			System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			res =  ps.executeQuery();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}
	
	public ResultSet getGamesRecent(Connection conn, String type) {
		ResultSet res = null;
		try {
			String query = "";
			if(type.equalsIgnoreCase("slide")) {
				query = "select gameid,category,gamename,cat_id,imgurl,gameurl from tbl_portal_game where status='1' order by rand()";
			}
			else if(type.equalsIgnoreCase("all")) {
				query = "select gameid,category,gamename,cat_id,imgurl,gameurl from tbl_portal_game where status='1'";
			}
			
			System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			res =  ps.executeQuery();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}
	
	public String getStatus(final String ani) {
        String State = "";
        try {
            
            final Statement stmt = Loader.FGRConn.createStatement();
            final String chkqry = "select * from tbl_subscription where ani='" + ani + "' and service_type='games'  ";
            System.out.println(chkqry);
            final ResultSet rs = stmt.executeQuery(chkqry);
            if (rs.next()) {
                State = this.getUserState(ani, "games");
            }
            else {
                State = "2";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return State;
    }
	
	public String getUserState(final String ani, final String service) {
        String State = "0";
        try {
            Statement stmt = null;
            stmt = Loader.FGRConn.createStatement();
            int cnt = 0;
            final String subQry = "select count(1) as cnt from tbl_subscription where ani='" + ani + "' and service_type='" + service + "' " + "and date(next_billed_date)>= date(now())";
            System.out.println("subQry::::" + subQry);
            final ResultSet rssub = stmt.executeQuery(subQry);
            if (rssub.next()) {
                cnt = rssub.getInt(1);
                System.out.println("cnt~~" + cnt);
            }
            if (cnt > 0) {
                State = "1";
            }
            else {
                State = "0";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return State;
    }
	
	public ResultSet getHighlightGames() {
		ResultSet res = null;
		try {
			String query = "select gameid,cat_id,banner_url,gameurl,category from tbl_portal_game where status='1' and vendor='GamePix'";
			System.out.println(query);
			PreparedStatement ps = Loader.FGRConn.prepareStatement(query);
			res =  ps.executeQuery();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}
	
}
