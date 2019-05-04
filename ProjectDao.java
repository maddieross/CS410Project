
import java.io.*;
import java.sql.*;
import java.util.*;
/**
 * @author mross, hjohnson
 *
 */
public class ProjectDao {
	private static ArrayList<Statement> statements = new ArrayList<Statement>();
	private static Connection con;
	private static boolean made;

	/**
	 * Everytime a constructor is called get the connection
	 */
	public ProjectDao() {
		getConnection();
	}

	
	/**
	 * Only call startUp if it's the first time the DAO is being called. Otherwise,
	 * just return existing connection
	 */
	public void getConnection() {
		if (!made) {
			startUp();
		}
	}

	/**
	 * Make first connection to the database, set class con variable to connection
	 */
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

			//Tracks whether or not this is the first time start up has been called
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

	/**
	 * prints list all the cars - list the car description and status
	 * @throws SQLException
	 */
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
		con.setAutoCommit(true);
		}

	}
	
	/**
	 * add new client to database
	 * @param name
	 * @param licenseNumber
	 * @param phoneNum
	 * @throws SQLException
	 */
	public void addClient(String name, String licenseNumber, String phoneNum) throws SQLException{
		PreparedStatement addClient = null;
		try {
			con.setAutoCommit(false);
			addClient = con.prepareStatement("insert into client values (?, ?, ?, ?)");
			addClient.setInt(1, 0);
			addClient.setString(2, name);
			addClient.setString(3, licenseNumber);
			addClient.setString(4, phoneNum);
			

			int added = addClient.executeUpdate();
			if (added > 0) {
				System.out.println("Client added");
			} else {
				System.out.println("Clinet not added");
			}

		} catch (SQLException e) {
			con.rollback();
			printSQLException(e);
		} finally {
			if (addClient != null) {
				addClient.close();
			}
			con.setAutoCommit(true);
		}
	}
	/**
	 * returns client name associated with given code
	 * @param clientCode
	 * @return client name 
	 * @throws SQLException
	 */
	public String getclientName(int clientCode) throws SQLException{
		PreparedStatement clientCheck = null;
		String returnStmt = null;
		try {
			con.setAutoCommit(false);
			clientCheck = con.prepareStatement("SELECT name FROM client WHERE code=?");
			clientCheck.setInt(1, clientCode); 
			ResultSet resultSet = clientCheck.executeQuery();
			while (resultSet.next()) {
				returnStmt = resultSet.getString("name");
			}	
			if(returnStmt == null) {
				System.err.println("client does not exist in database"); 
				System.exit(0); 
			
			}
			resultSet.close();
		} catch (SQLException e) {
			con.rollback();
			printSQLException(e);
		} finally {
			if (clientCheck != null) {
				clientCheck.close();
			}
		con.setAutoCommit(true);
		}
		return returnStmt;
	}
	/**
	 * returns status of car associated 
	 * @param plateNum
	 * @return carStatus
	 * @throws SQLException
	 */
	public String getCarStatus(String plateNum)throws SQLException{
		PreparedStatement getCarStatus = null;
		String returnStmt=null;
		try {
			con.setAutoCommit(false);
			getCarStatus = con.prepareStatement("SELECT status FROM car WHERE plate_number=?");
			getCarStatus.setString(1, plateNum); 
			ResultSet resultSet = getCarStatus.executeQuery();
			while (resultSet.next()) {
				returnStmt = resultSet.getString("status");
			}	
			if(returnStmt == null) {
				System.err.println("car does not exist in database"); 
				System.exit(0); 
			}
			resultSet.close();
		} catch (SQLException e) {
			con.rollback();
			printSQLException(e);
		} finally {
			if (getCarStatus != null) {
				getCarStatus.close();
			}
		con.setAutoCommit(true);
		}
		return returnStmt; 
	}
	/**
	 * updates status of car associated with the given plate number
	 * @param status
	 * @param plateNum
	 * @throws SQLException
	 */
	public void updateCarStatus(String status, String plateNum) throws SQLException{
		if(!status.equals("Available")) {
			if(!getCarStatus(plateNum).equals("Available")) {
				System.err.println("Car is not available");
				System.exit(0);
			}
		}
		PreparedStatement updateStatus = null;
		try {
			con.setAutoCommit(false);
			updateStatus = con.prepareStatement("UPDATE car SET status=? WHERE plate_number=?");
			updateStatus.setString(1, status);
			updateStatus.setString(2, plateNum);
			int added = updateStatus.executeUpdate();
			if (added > 0) {
				System.out.println("car status updated to: "+ status);
			} else {
				System.out.println("car status not updated");
			}
		}catch(SQLException e) {
			con.rollback();
			printSQLException(e);
		} finally {
			if (updateStatus != null) {
				updateStatus.close();
			}
			con.setAutoCommit(true);
		}
		
	}
	/**
	 * rent car, add new entry to rent table
	 * @param clientCode
	 * @param plateNum
	 * @param startDate
	 * @param endDate
	 * @param miles
	 * @param feeType
	 * @throws SQLException
	 */
	public void rentCar(int clientCode, String plateNum, String startDate, String endDate, int miles, String feeType) throws SQLException {
		String clientName = getclientName(clientCode); //check client exist 
		System.out.println(clientName); 
		updateCarStatus("Rented", plateNum); //check car is available and update status to Rented
		PreparedStatement rentCar = null;
		try {
			con.setAutoCommit(false);
			rentCar = con.prepareStatement("insert into rent values (?, ?, ?, ?, ?, ?, ?)");
			rentCar.setInt(1, 0);
			rentCar.setString(2, plateNum);
			rentCar.setInt(3, clientCode);
			rentCar.setString(4, startDate);
			rentCar.setString(5, endDate);
			rentCar.setInt(6, miles);
			rentCar.setString(7, feeType);
			

			int added = rentCar.executeUpdate();
			if (added > 0) {
				System.out.println("car rented");
			} else {
				System.out.println("car not rented");
			}

		} catch (SQLException e) {
			con.rollback();
			printSQLException(e);
		} finally {
			if (rentCar != null) {
				rentCar.close();
			}
			con.setAutoCommit(true);
		}
		
		
	}

	/**
	 * @return array list of models in database
	 * @throws SQLException
	 */
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

	/**
	 * returns model number associated with the model name
	 * @param name
	 * @return model number
	 * @throws SQLException
	 */
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

	/**
	 * add new car to database
	 * @param plate
	 * @param miles
	 * @param model
	 * @param type
	 * @param fee
	 * @throws SQLException
	 */
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

	/**
	 * add new model to database
	 * @param name
	 * @param make
	 * @param engSize
	 * @param year
	 * @throws SQLException
	 */
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

	/**
	 * prints list of all the available cars - list the car description
	 * @throws SQLException
	 */
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

	/**
	 * returns rental details associated with rental ID
	 * @param rentalID
	 * @throws SQLException
	 */
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

	public void deleteRental(int rentId) throws SQLException {
		PreparedStatement deleteRental = null;
		PreparedStatement rental = null;
		try {
			con.setAutoCommit(false);
			rental = con.prepareStatement("select plate from rent where rental_id = ?");
			ResultSet rs = rental.executeQuery();


			if(rs != null) {
				String plateNum = "";
				plateNum = rs.getString("plate");


				deleteRental = con.prepareStatement("delete from rent where rental_id = ?");
				deleteRental.setInt(1, rentId);

				int result = deleteRental.executeUpdate();
				if (result <= 0) {
					System.out.println("Rental entry not deleted");
				} else {
					System.out.println("Rental entry deleted");
				}
			} else {
				System.out.println(rentId + "is not a valid rental entry");
			}

		} catch(SQLException e) {
			con.rollback();
			printSQLException(e);
		} finally {
			if (deleteRental != null) {
				deleteRental.close();
			}

			if(rental != null) {
				rental.close();
			}
			con.setAutoCommit(true);
		}
	}
	/**
	 * Properly shut down the database connection after every command
	 */
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