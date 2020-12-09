package gen;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public Connection getDatabse() {

		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/htgames?autoReconnect=true", "root","gloadmin123");
			//conn = DriverManager.getConnection("jdbc:mysql://5.189.169.12:3306/htgames?autoReconnect=true", "root","gloadmin123");
			 System.out.println("HT Games DB connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
