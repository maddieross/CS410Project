import java.io.*;
import java.sql.*;
import java.util.*;


public class ProjectDao {
	private static ArrayList<Statement> statements = new ArrayList<Statement>();
	private static Connection con;
	private static boolean made;

	public ProjectDao() {
		getConnection();
	}

	/*
	 *   Only call startUp if it's the first time the DAO is being called.
	 *   Otherwise, just return existing connection
	 */
	public void getConnection() {
		if(!made) {
			startUp();
		}
	}

	public void startUp() {
	    try
	    {
		String strRemotHost = "localhost";
		int DBPort = 5980;
		Properties props = new Properties();
		props.setProperty("user", "msandbox");
		props.setProperty("password", "Testpoop1");
		props.setProperty("useSSL", "false");

		Class.forName("com.mysql.jdbc.Driver");

		con = DriverManager.getConnection("jdbc:mysql://localhost:"+DBPort, props);
		Statement stmt = con.createStatement();
		stmt.execute("use rental");

		stmt.close();
		con.setAutoCommit(false);

		made = true;

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
	}

	public void getAvailableCars() throws SQLException {
	    PreparedStatement getAvCars = null;

	    try {
	        con.setAutoCommit(false);
            getAvCars = con.prepareStatement("SELECT * FROM car WHERE status='Available'");

            ResultSet resultSet = getAvCars.executeQuery();

            ResultSetMetaData rsmd = resultSet.getMetaData();

            int columnsNumber = rsmd.getColumnCount();
	    
                for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print("|" + rsmd.getColumnName(i) + "|");
                }

		System.out.println("");
            while (resultSet.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = resultSet.getString(i);
                    System.out.print("| " + columnValue + " |");
                }
		System.out.println(" ");
		System.out.println("-------------------------------------");

            }

            resultSet.close();
        } catch (SQLException e) {
	        con.rollback();
        } finally {
	        if(getAvCars != null) {
                getAvCars.close();
            }


        }
    }

    public void getRentalDetails(int rentalID) throws SQLException{
		PreparedStatement getRentDet = null;

		try {
			con.setAutoCommit(false);
			getRentDet = con.prepareStatement("select * from rent " +
					"join client on rent.code = client.code" +
					"join car on rent.plate = car.plate_number" +
					"where rental_id = ?");
			getRentDet.setInt(1, rentalID);

			ResultSet resultSet = getRentDet.executeQuery();

			ResultSetMetaData rsmd = resultSet.getMetaData();

			int columnsNumber = rsmd.getColumnCount();

			for (int i = 1; i <= columnsNumber; i++) {
				System.out.print("|" + rsmd.getColumnName(i) + "|");
			}

			System.out.println("");
			while (resultSet.next()) {

				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = resultSet.getString(i);
					System.out.print("| " + columnValue + " |");
				}
				System.out.println(" ");
				System.out.println("-------------------------------------");

			}

			resultSet.close();
		} catch (SQLException e) {
			con.rollback();
		} finally {
			if(getRentDet != null) {
				getRentDet.close();
			}
		}

	}

	public static void shutDown() {
		// Statements and PreparedStatements
		int i = 0;
		while (!statements.isEmpty()) {
			// PreparedStatement extend Statement
			Statement st = (Statement) statements.remove(i);
			try {
				if (st != null) {
					st.close();
					st = null;
				}
			} catch (SQLException sqle) {
				printSQLException(sqle);
			}
		}

		//Connection
		try {
			con.setAutoCommit(true);
			con.close();
		} catch (SQLException conE) {
			System.out.println(conE.getMessage());
		}
	}

	public static void main (String[] args) {
		ProjectDao dao = new ProjectDao();
		System.out.println("Dao started");
		dao.shutDown();
        	System.out.println("Dao shut down correctly");
	}

	/**
	 * Prints details of an SQLException chain to <code>System.err</code>.
	 * Details included are SQL State, Error code, Exception message.
	 *
	 * @param e the SQLException from which to print details.
	 */
	public static void printSQLException(SQLException e)
	{
		// Unwraps the entire exception chain to unveil the real cause of the
		// Exception.
		while (e != null)
		{
			System.err.println("\n----- SQLException -----");
			System.err.println("  SQL State:  " + e.getSQLState());
			System.err.println("  Error Code: " + e.getErrorCode());
			System.err.println("  Message:    " + e.getMessage());
			e = e.getNextException();
		}
	}

}	
