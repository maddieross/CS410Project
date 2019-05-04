import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * @author mross, hjohnson
 *
 */
public class Project {
	private static ProjectDao dao;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			dao = new ProjectDao();
			String function = args[0];
			switch (function) {
			case "cars":
				cars();
				break;
			case "availableCars":
				availableCars();
				break;
			case "addNewCar":
				try {
					String model = args[1];
					String plateNum = args[2];
					int miles = Integer.parseInt(args[3]);
					addNewCar(model, plateNum, miles);
				} catch (Exception e) {
					System.err.println("usage: java program addNewCar [model] [plate#] [miles]");
					System.exit(0);

				}
				break;
			case "addClient":
				try {
					String name = args[1] + " "+args[2];
					int code = Integer.parseInt(args[3]);
					String licenseNumber = args[4];
					String phoneNum = args[5];
					addClient(name, code, licenseNumber, phoneNum);
				} catch (Exception e) {
					System.err.println("usage: java Program addClient [first and last name] [code] [licenseNumber] [phone]");
					System.exit(0);

				}
				break;
			case "rentCar":
				try {
					int client = Integer.parseInt(args[1]); // client code?
					String car = args[2]; // plate number
					String startDate = args[3];
					String endDate = args[4];
					int miles = Integer.parseInt(args[5]);
					String feeType = args[6];
					rentCar(client, car, startDate, endDate, miles, feeType);
				} catch (Exception e) {
					System.err.println(
							"usage: java Program rentCar [client code] [car plate number] [startDate: YYYY-MM-DD] [endDate: YYYY-MM-DD] [miles] [feeType]");
					System.exit(0);

				}
				break;
			case "rentalDetails":
				try {
					int rentalID = Integer.parseInt(args[1]);
					rentalDetails(rentalID);
				} catch (Exception e) {
					System.err.println("usage: java Program rentalDetails [rentalID]");
					System.exit(0);

				}
				break;
			case "deleteRental":
				try {
					int rentalID = Integer.parseInt(args[1]);
					deleteRental(rentalID);
				} catch (Exception e) {
					System.err.println("usage: java Program deleteRental [rentalID]");
					System.exit(0);
				}
				break;
			case "help":
				help();
				break;
			default:
				help(); 
			}
		} else {
			help();
		}
	}

	/**
	 * Get all cars
	 */
	private static void cars() {
		try {
			System.out.println("Getting cars and status");
			dao.getCars();
		} catch (SQLException e) {
			dao.printSQLException(e);
		} finally {
			dao.shutDown();
		}

	}

	/**
	 * Get all available cars
	 */
	private static void availableCars() {
		try {
			System.out.println("Getting all available cars");
			dao.getAvailableCars();
		} catch (SQLException e) {
			dao.printSQLException(e);
		} finally {
			dao.shutDown();
		}
	}

	/**
	 * Add a new car. If it's a new model then add a new model and then a new car
	 * @param model
	 * @param plateNum
	 * @param miles
	 */
	private static void addNewCar(String model, String plateNum, int miles) {
		try {
			System.out.println("Adding new car. Seeing if it's existing model");
			ArrayList<String> models = dao.getAllModels();
			boolean found = true;

			for (String mdl : models) {
				System.out.println(mdl + " " + model);
				if (mdl.equals(model)) {
					found = true;
					break;
				} else {
					found = false;
				}
			}

			System.out.println(found);
			String carType = "Midsize";
			int fee = 75;
			if (found) {
				int modelNum = dao.getModelNum(model);
				System.out.println("Model: " + modelNum);
				dao.addCar(plateNum, miles, modelNum, carType, fee);
			} else {
				String newModel = "Fusion";
				String make = "Ford";
				int engSize = 1;
				String year = "2019";
				dao.addModel(newModel, make, engSize, year);
				int modelNum = dao.getModelNum(newModel);
				System.out.println("Model: " + modelNum);
				dao.addCar(plateNum, miles, modelNum, carType, fee);
			}

		} catch (SQLException e) {
			dao.printSQLException(e);
		} finally {
			dao.shutDown();
		}

	}

	/**
	 * @param name
	 * @param code
	 * @param licenseNumber
	 * @param phoneNum
	 */
	private static void addClient(String name, int code, String licenseNumber, String phoneNum) {
		try {
			System.out.println("Adding client: "+ name);
			dao.addClient(name, licenseNumber, phoneNum);
		} catch (SQLException e) {
			dao.printSQLException(e);
		} finally {
			dao.shutDown();
		}
	}

	/**
	 * @param client
	 * @param car
	 * @param startDate
	 * @param endDate
	 * @param miles
	 * @param feeType 
	 */
	private static void rentCar(int client, String car, String startDate, String endDate, int miles, String feeType) {
		LocalDate myLocalDate = null;
		//TODO check date is entered in correct format? 
		try {
			myLocalDate = LocalDate.parse(startDate);
			myLocalDate = LocalDate.parse(endDate); 
		}catch(Exception e) {
			System.err.println("incorrect date format. Must be formated in yyyy-MM-dd");
			System.exit(0);
		}
	
		if(feeType.equals("Economy") || feeType.equals("Compact") || feeType.equals("Midsize") || feeType.equals("FullSize") || feeType.equals("Premium")|| feeType.equals("Luxury")) {
			try {
				System.out.println("Renting car to:");
				dao.rentCar(client, car, startDate, endDate, miles, feeType);
			} catch (SQLException e) {
				dao.printSQLException(e);
			} finally {
				dao.shutDown();
			} 
		}else {
			System.err.println("incorrect feeType. feeTypes: Economy, Compact, Midsize, Fullsize, Premium, or Luxury");
			System.exit(0); 
		}
		
	}

	/**
	 * @param rentalID
	 */
	private static void rentalDetails(int rentalID) {
		try {
			System.out.println("Getting rental details for: " + rentalID);
		 	dao.getRentalDetails(rentalID);
		} catch (SQLException e) {
			dao.printSQLException(e);
		} finally {
			dao.shutDown();
		}
	}

	/**
	 * @param rentalID
	 */
	private static void deleteRental(int rentalID) {
		try {
			System.out.println("Deleting rental: " + rentalID);
			dao.deleteRental(rentalID);
		} catch (SQLException e) {
			dao.printSQLException(e);
		} finally {
			dao.shutDown();
		}

	}

	/**
	 * 
	 */
	private static void help() {
		System.out.println("usage: java Program [commands] [args....] ");
		System.out.println("commands:");
		System.out.println(
				"cars                                                             - list all cars, description, and status");
		System.out.println(
				"availableCars                                                    - list all the available cars and car description");
		System.out.println(
				"addNewCar [model] [plate#] [miles]                               - add a new car to the database");
		System.out.println(
				"addClient [name] [code] [licenseNumber] [phone]                  - add a new client to the system");
		System.out.println(
				"rentCar [client car] [startDate] [endDate] [miles] [feeType]     - add a new client to the system");
		System.out.println("rentalDetails [rentalID]                                         - Show details of rental");
		System.out.println(
				"deleteRental [rentalID]                                          - delete/cancel previously added rental");
	}

}
