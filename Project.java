/**
 * @author mross
 *
 */
public class Project {
	
	public static void main(String[] args) {
		if(args.length > 0) {
			ProjectDao dao = new ProjectDao();
			System.out.println("Dao started");
			String function = args[0];
			switch(function) {
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
				}catch (Exception e){
					System.err.println("usage: java program addNewCar [model] [plate#] [miles]");
					System.exit(0);

				}
				break;
			case "addClient":
				try {
					String name = args[1];
					int code = Integer.parseInt(args[2]); 
					String licenseNumber = args[3];
					int phoneNum = Integer.parseInt(args[4]); 
					addClient(name, code, licenseNumber, phoneNum);
				}catch (Exception e){
					System.err.println("usage: java Program addClient [name] [code] [licenseNumber] [phone]");
					System.exit(0);

				}
				break;
			case "rentCar":
				try {
					int client = Integer.parseInt(args[1]); //client code?
					String car = args[2]; //plate number
					String startDate = args[3];
					String endDate = args[4];
					int miles = Integer.parseInt(args[5]);
					String feeType = args[6]; 
					rentCar(client, car, startDate, endDate, miles, feeType);
				}catch (Exception e){
					System.err.println("usage: java Program rentCar [client car] [startDate] [endDate] [miles] [feeType]");
					System.exit(0);

				}
				break;
			case "rentalDetails":
				try {
					int rentalID = Integer.parseInt(args[1]);
					rentalDetails(rentalID);
				}catch (Exception e){
					System.err.println("usage: java Program rentalDetails [rentalID]");
					System.exit(0);

				}
				break; 
			case "deleteRental":
				try {
					int rentalID = Integer.parseInt(args[1]);
					deleteRental(rentalID);	
				}catch (Exception e){
					System.err.println("usage: java Program deleteRental [rentalID]");
					System.exit(0);
				}
				break; 
			case "help":
				help();
				break;
			}
		}else{
			help();	
		}
		help();	
	}

	
	private static void cars() {
		// TODO Auto-generated method stub
		
	}
	
	private static void availableCars() {
		// TODO Auto-generated method stub
		 
		
	}
	
	private static void addNewCar(String model, String plateNum, int miles) {
		// TODO Auto-generated method stub
		
	}
	
	private static void addClient(String name, int code, String licenseNumber, int phoneNum) {
		// TODO Auto-generated method stub
		
	}
	

	private static void rentCar(int client, String car, String startDate, String endDate, int miles, String feeType) {
		// TODO Auto-generated method stub
	}


	private static void rentalDetails(int rentalID) {
		// TODO Auto-generated method stub
		
	}
	
	private static void deleteRental(int rentalID) {
		// TODO Auto-generated method stub
		
	}
	
	
	private static void help() {
		System.out.println("usage: java Program [commands] [args....] ");
		System.out.println("commands:");
		System.out.println("cars                                                             - list all cars, description, and status");
		System.out.println("availableCars                                                    - list all the available cars and car description");
		System.out.println("addNewCar [model] [plate#] [miles]                               - add a new car to the database");
		System.out.println("addClient [name] [code] [licenseNumber] [phone]                  - add a new client to the system");
		System.out.println("rentCar [client car] [startDate] [endDate] [miles] [feeType]     - add a new client to the system");
		System.out.println("rentalDetails [rentalID]                                         - Show details of rental");
		System.out.println("deleteRental [rentalID]                                          - delete/cancel previously added rental");
	}

}
