
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
	 * Only call startUp if it's the first time the DAO is being called. Otherwise,
	 * just return existing connection
	 */
	public void getConnection() {
		if (!made) {
			startUp();
		}
	}

	public void startUp() {
		try {
			String strRemotHost = "localhost";
			int DBPort = 5980;
			Properties props = new Properties();
			props.setProperty("user", "msandbox");
			props.setProperty("password", "Testpoop1");
			props.setProperty("useSSL", "false");

			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:" + DBPort, props);
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

	public void getCars() throws SQLException {
		PreparedStatement getCars = null;

		try {
			con.setAutoCommit(false);
			getCars = con.prepareStatement("SELECT * FROM car");
			ResultSet resultSet = getCars.executeQuery();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnNumber = rsmd.getColumnCount();

			for (int i = 1; i <= columnNumber; i++) {
				System.out.print("|" + rsmd.getColumnName(i) + "|");
			}

			System.out.println("");

			while (resultSet.next()) {
				for (int i = 1; i <= columnNumber; i++) {
					String columnValue = resultSet.getString(i);
					System.out.print("| " + columnValue + " |");
				}
				System.out.println(" ");
				System.out.println("----------------------------------------------------------");
			}
			resultSet.close();
		} catch (SQLException e) {
			con.rollback();
		} finally {
			if (getCars != null) {
				getCars.close();
			}

		}

	}

	public ArrayList<String> getAllModels() throws SQLException {
		PreparedStatement getAllModels = null;
		ArrayList<String> modelOptions = new ArrayList<>();
		try {
			con.setAutoCommit(false);
			getAllModels = con.prepareStatement("SELECT name from model");
			ResultSet resultSet = getAllModels.executeQuery();
			while (resultSet.next()) {
				modelOptions.add(resultSet.getString("name"));
			}

			resultSet.close();
		} catch (SQLException e) {
			con.rollback();
		} finally {
			if (getAllModels != null) {
				getAllModels.close();
			}
			con.setAutoCommit(true);
		}

		return modelOptions;
	}

	public int getModelNum(String name) throws SQLException {
		PreparedStatement getModel = null;
		int modelNum = 1;
		ArrayList<String> modelOptions = new ArrayList<>();
		try {
			con.setAutoCommit(false);
			getModel = con.prepareStatement("SELECT model_id from model where name = ?");
			getModel.setString(1, name);
			ResultSet resultSet = getModel.executeQuery();

			while (resultSet.next()) {
				modelNum = resultSet.getInt("model_id");
			}

			resultSet.close();
		} catch (SQLException e) {
			con.rollback();
		} finally {
			if (getModel != null) {
				getModel.close();
			}
			con.setAutoCommit(true);
		}

		return modelNum;

	}

	public void addCar(String plate, int miles, int model, String type, int fee) throws SQLException {
		PreparedStatement addCar = null;
		try {
			con.setAutoCommit(false);
			addCar = con.prepareStatement("insert into car values (?, ?, ?, ?, ?, ?)");
			addCar.setString(1, plate);
			addCar.setInt(2, miles);
			addCar.setString(3, "Available");
			addCar.setInt(4, model);
			addCar.setString(5, type);
			addCar.setInt(6, fee);

			int added = addCar.executeUpdate();
			if (added > 0) {
				System.out.println("Car added");
			} else {
				System.out.println("Car not added");
			}

		} catch (SQLException e) {
			con.rollback();
			printSQLException(e);
		} finally {
			if (addCar != null) {
				addCar.close();
			}
			con.setAutoCommit(true);
		}
	}

	public void addModel(String name, String make, int engSize, String year) throws SQLException {
		PreparedStatement addModel = null;
		try {
			con.setAutoCommit(false);
			addModel = con.prepareStatement("insert into model values (?, ?, ?, ?, ?)");
			addModel.setInt(1, 0);
			addModel.setString(2, name);
			addModel.setString(3, make);
			addModel.setInt(4, engSize);
			addModel.setString(5, year);

			int added = addModel.executeUpdate();
			if (added > 0) {
				System.out.println("Model added");
			} else {
				System.out.println("Model not added");
			}

		} catch (SQLException e) {
			con.rollback();
		} finally {
			if (addModel != null) {
				addModel.close();
			}
			con.setAutoCommit(true);
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
			if (getAvCars != null) {
				getAvCars.close();
			}
			con.setAutoCommit(true);

		}
	}

	public void getRentalDetails(int rentalID) throws SQLException {
		PreparedStatement getRentDet = null;

		try {
			con.setAutoCommit(false);
			getRentDet = con.prepareStatement(
					"select rent.plate, rent.start, rent.end, rent.num_miles, rent.fee_type, client.name, client.dl, client.phone"
							+ ", car.*, datediff(rent.end, rent.start) * car.fee as total from rent "
							+ "join client on rent.code = client.code join car on rent.plate = car.plate_number where rental_id = ?");
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
			printSQLException(e);
		} finally {
			if (getRentDet != null) {
				getRentDet.close();
			}
			con.setAutoCommit(true);
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

		// Connection
		try {
			con.setAutoCommit(true);
			con.close();
		} catch (SQLException conE) {
			System.out.println(conE.getMessage());
		}
	}

	public static void main(String[] args) {
		ProjectDao dao = new ProjectDao();
		System.out.println("Dao started");
		dao.shutDown();
		System.out.println("Dao shut down correctly");
	}

	/**
	 * Prints details of an SQLException chain to <code>System.err</code>. Details
	 * included are SQL State, Error code, Exception message.
	 *
	 * @param e
	 *            the SQLException from which to print details.
	 */
	public static void printSQLException(SQLException e) {
		// Unwraps the entire exception chain to unveil the real cause of the
		// Exception.
		while (e != null) {
			System.err.println("\n----- SQLException -----");
			System.err.println("  SQL State:  " + e.getSQLState());
			System.err.println("  Error Code: " + e.getErrorCode());
			System.err.println("  Message:    " + e.getMessage());
			e = e.getNextException();
		}
	}

}
