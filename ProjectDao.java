import java.io.*;
import java.sql.*;
import java.util.*;


public class ProjectDao {

	public ProjectDao() {
		Connection con = null;
		Statement stmt = null, stmt2 = null;
	    try
	    {
		String strRemotHost = "localhost";
		int DBPort = 5980;
		String dbUser = "msandbox";
		String dbPassword = "Testpoop1";

		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:"+DBPort, dbUser, dbPassword);

		con.setAutoCommit(false);

		stmt = con.createStatement();
		stmt.execute("use rental");
		ResultSet resultSet = stmt.executeQuery("select * from client");

		ResultSetMetaData rsmd = resultSet.getMetaData();

		int columnsNumber = rsmd.getColumnCount();
			while (resultSet.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) System.out.print(",  ");
					String columnValue = resultSet.getString(i);
					System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
				System.out.println(" ");
			}
	    } catch (SQLException e) {
	    	System.out.println(e.getMessage());
	    	try {
	    	con.rollback();
	    	} catch (SQLException innerE) {
	    		System.out.println(e.getMessage());
	    	}
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    }
	    finally {
	    	try {
	    		if(stmt != null) { stmt.close();}

	    		if(stmt2 !=null) { stmt2.close();}
	    	} catch (SQLException innerE) {
	    		System.out.println(innerE.getMessage());
	    	}

	    	try {
	    		con.setAutoCommit(true);
	    		con.close();
	    	} catch (SQLException conE) {
	    		System.out.println(conE.getMessage());
	    	}
	    }
	}
	
	public static void main (String[] args) {
		ProjectDao dao = new ProjectDao();
		System.out.println("Dao started");
	}

}	
