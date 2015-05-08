import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ConsoleDemo {
	
	public static void main(final String[] theArgs) throws FileNotFoundException {
		
		// initialize variables
		JobHandler jobHandler = new JobHandler();
		ArrayList<Volunteer> volunteerList = populateVolunteers(); //read in all volunteers
		ArrayList<Administrator> adminList = populateAdmins(jobHandler); //read in all admins
		ArrayList<ParkManager> managerList = populateManagers(jobHandler); //read in all park managers
		
		AbstractUser currentUser = login(volunteerList, adminList, managerList); //login prompt
		if (currentUser instanceof Administrator) {
			administrator(currentUser);
		} else if (currentUser instanceof Volunteer) {
			volunteer(currentUser);
		} else if (currentUser instanceof ParkManager) {
			manager(currentUser);
		}

	}
	
	
	
	//print login screen
	private static AbstractUser login(ArrayList<Volunteer> theVolunteerList, ArrayList<Administrator> theAdminList, ArrayList<ParkManager> theManagerList) {
		Scanner consoleScanner = new Scanner(System.in);
		System.out.println("Welcome to the demo client. Enter your username to login:");
		String userInput = consoleScanner.next();
		while (!goodLogin(userInput, theVolunteerList, theManagerList, theAdminList)) {
			System.out.println("Bad login, try again.");
			userInput = consoleScanner.next();
		}
		
		for (Volunteer volunteer : theVolunteerList) {
			if (volunteer.getEmail().equals(userInput)) {
				return volunteer;
			}
		}
		for (ParkManager manager : theManagerList) {
			if (manager.getEmail().equals(userInput)) {
				return manager;
			}
		}
		for (Administrator admin : theAdminList) {
			if (admin.getEmail().equals(userInput)) {
				return admin;
			}
		}
		return null;
	}



	//checks to see that user has valid login entry (just an email match)
	private static boolean goodLogin(String theInput, ArrayList<Volunteer> theVolunteerList, 
			ArrayList<ParkManager> theManagerList, ArrayList<Administrator> theAdminList
			) {
		for (Volunteer volunteer : theVolunteerList) {
			if (volunteer.getEmail().equals(theInput)) {
				return true;
			}
		}
		for (ParkManager manager : theManagerList) {
			if (manager.getEmail().equals(theInput)) {
				return true;
			}
		}
		for (Administrator admin : theAdminList) {
			if (admin.getEmail().equals(theInput)) {
				return true;
			}
		}
		return false;
	}



	//manager menu
	private static void manager(AbstractUser currentUser) {
		System.out.println();
		System.out.println("Welcome, ParkManager " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
		System.out.println("---PARK MANAGER OPTIONS---");
		System.out.println("    1) Submit a new job request");
		System.out.println("    2) View upcoming jobs in parks I manage");
		System.out.println("    3) View the volunteers for a for a job in the parks I manage");
		System.out.println("    4) View my account information");
		System.out.println("    5) Logout");
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.next();
		while (!userInput.equals("5")) {
			System.out.println("SELECT 5 TO LOGOUT");
			userInput = scanner.next();
		}
		System.out.println("Goodbye.");
		System.out.println("---CONSOLE DEMO OFFLINE---");
	}
	
	//admin menu
	private static void administrator(AbstractUser currentUser) {
		System.out.println();
		System.out.println("Welcome, Administrator " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
		System.out.println("---ADMINISTRATOR OPTIONS---");
		System.out.println("    1) Search volunteers by last name");
		System.out.println("    2) View my account information");
		System.out.println("    3) Logout");
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.next();
		while (!userInput.equals("3")) {
			System.out.println("SELECT 3 TO LOGOUT");
			userInput = scanner.next();
		}
		System.out.println("Goodbye.");
		System.out.println("---CONSOLE DEMO OFFLINE---");
	}
	
	//volunteer menu
	private static void volunteer(AbstractUser currentUser) {
		System.out.println();
		System.out.println("Welcome, Volunteer " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
		System.out.println("---VOLUNTEER OPTIONS---");
		System.out.println("    1) Sign up for a job");
		System.out.println("    2) View upcoming jobs I can sign up for");
		System.out.println("    3) View jobs currently signed up for");
		System.out.println("    4) View my account information");
		System.out.println("    5) Logout");
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.next();
		while (!userInput.equals("5")) {
			System.out.println("SELECT 5 TO LOGOUT");
			userInput = scanner.next();
		}
		System.out.println("Goodbye.");
		System.out.println("---CONSOLE DEMO OFFLINE---");
	}

	/*------------------------------------INITIALIZATION METHODS------------------------------------*/
	/*----------------------------------------------------------------------------------------------*/

	//populates list of administrators at startup
	private static ArrayList<Administrator> populateAdmins(final JobHandler theJobHandler) throws FileNotFoundException {
		File adminsFile = new File("supportfiles/admins.txt");
		ArrayList<Administrator> list = new ArrayList<Administrator>();
		Scanner scanner = new Scanner(adminsFile);
		scanner.useDelimiter(",");
		String firstName;
		String lastName;
		String email;
		String phoneNumber;
		String address = "";
		while (scanner.hasNext()) {
			firstName = scanner.next();
			lastName = scanner.next();
			email = scanner.next();
			phoneNumber = scanner.next();
			address = scanner.next();
			if (firstName.charAt(0) == '\r') {
				firstName = firstName.substring(2);
			}
			Administrator admin = new Administrator(firstName, lastName, email, phoneNumber, address, theJobHandler);
			list.add(admin);
		}
		return list;
	}
	
	//populates list of park managers at startup
	private static ArrayList<ParkManager> populateManagers(final JobHandler theJobHandler) throws FileNotFoundException {
		File managersFile = new File("supportfiles/parkmanagers.txt");
		ArrayList<ParkManager> list = new ArrayList<ParkManager>();
		Scanner scanner = new Scanner(managersFile);
		scanner.useDelimiter(",");
		String firstName;
		String lastName;
		String email;
		String phoneNumber;
		String address = "";
		while (scanner.hasNext()) {
			firstName = scanner.next();
			lastName = scanner.next();
			email = scanner.next();
			phoneNumber = scanner.next();
			address = scanner.next();
			if (firstName.charAt(0) == '\r') {
				firstName = firstName.substring(2);
			}
			ParkManager admin = new ParkManager(firstName, lastName, email, phoneNumber, address, theJobHandler);
			list.add(admin);
		}
		return list;
	}

	//populates list of volunteers at startup
	private static ArrayList<Volunteer> populateVolunteers() throws FileNotFoundException {
		File volunteersFile = new File("supportfiles/volunteers.txt");
		ArrayList<Volunteer> list = new ArrayList<Volunteer>();
		Scanner scanner = new Scanner(volunteersFile);
		scanner.useDelimiter(",");
		
		String firstName;
		String lastName;
		String email;
		String phoneNumber;
		String address = "";
		while (scanner.hasNext()) {
			firstName = scanner.next();
			lastName = scanner.next();
			email = scanner.next();
			phoneNumber = scanner.next();
			address = scanner.next();
			if (firstName.charAt(0) == '\r') {
				firstName = firstName.substring(2);
			}
			Volunteer volunteer = new Volunteer(firstName, lastName, email, phoneNumber, address);
			list.add(volunteer);
		}
		return list;
	}
}
