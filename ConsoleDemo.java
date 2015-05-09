import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A quick and dirty console demo of the park management program.
 * 
 * @author Dennis Kenyon
 * @author Brian Crabtree
 * @author David Anderson
 * @version 09May2015
 */

public class ConsoleDemo {
	
	public static void main(final String[] theArgs) throws FileNotFoundException {
		
		// initialize variables
		JobHandler jobHandler = new JobHandler();
		ArrayList<Job> jobList = populateJobs(); //read in all jobs
		ArrayList<Volunteer> volunteerList = populateVolunteers(); //read in all volunteers
		ArrayList<Administrator> adminList = populateAdmins(jobHandler); //read in all admins
		
		jobHandler.populateVolunteers(volunteerList);
		jobHandler.populateJobs(jobList);
		ArrayList<ParkManager> managerList = populateManagers(jobHandler, jobList); //read in all park managers
		
		for (Job job : jobList) {
			System.out.println("NAME: " + job.getName());
			System.out.println("DAY: " + job.getDay());
			System.out.println("MONTH: " + job.getMonth());
			System.out.println("LOCATION: " +job.getLocation());
			System.out.println("LIGHT: " + job.getLight());
			System.out.println("MEDIUM: " + job.getMed());
			System.out.println("HEAVY: " + job.getHeavy());
			System.out.println("INFO: " + job.getInfo());
			System.out.println();
		}
		
		AbstractUser currentUser = login(volunteerList, adminList, managerList); //login prompt
		if (currentUser instanceof Administrator) {
			administrator(currentUser.getEmail(), adminList);
		} else if (currentUser instanceof Volunteer) {
			volunteer(currentUser);
		} else if (currentUser instanceof ParkManager) {
			manager(currentUser.getEmail(), managerList);
		}
		
		
	
		
	} //end main
	
	
	
	//print login screen
	private static AbstractUser login(ArrayList<Volunteer> theVolunteerList, ArrayList<Administrator> theAdminList, ArrayList<ParkManager> theManagerList) {
		Scanner consoleScanner = new Scanner(System.in);
		System.out.println("---LOGIN---");
		System.out.println("User email: ");
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
		consoleScanner.close();
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
	private static void manager(String theEmail, ArrayList<ParkManager> theManagerList) throws FileNotFoundException {
		ParkManager currentUser = null;
		for (ParkManager manager : theManagerList) {
			if (manager.getEmail().equals(theEmail)) {
				currentUser = manager;
			}
		}
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
			
			if (userInput.equals("1")) {// user selects menu choice 1
				String jobName = null;
				int jobMonth = 0;
				int jobDay = 0;
				String jobLocation = null;
				int maxLight = 0;
				int maxMed = 0;
				int maxHeavy = 0;
				String otherInfo = null;
				System.out.println("JOB REQUEST FORM");
				System.out.print("Job name: ");
				scanner.nextLine(); //consume line, go to next
				jobName = scanner.nextLine();
				System.out.print("Job day (dd): ");
				jobDay = scanner.nextInt();
				System.out.print("Job month (mm): ");
				jobDay = scanner.nextInt();
				System.out.print("Park name: ");
				scanner.nextLine();
				jobLocation = scanner.nextLine();
				System.out.print("Max number of light-load volunteers: ");
				maxLight = scanner.nextInt();
				System.out.print("Max number of medium-load volunteers: ");
				maxMed = scanner.nextInt();
				System.out.print("Max number of heavy-load volunteers: ");
				maxHeavy = scanner.nextInt();
				System.out.println("Other information about the job: ");
				scanner.nextLine();
				otherInfo = scanner.nextLine();
				System.out.println("Your job looks like this:");
				System.out.println("	Name: " + jobName);
				System.out.println("	Date: " + jobMonth + "/" + jobDay + "/2015");
				System.out.println("	Location: " + jobLocation);
				System.out.println("	Maximum Workers per load:");
				System.out.println("		Light: " + maxLight);
				System.out.println("		Medium: " + maxMed);
				System.out.println("		Heavy: " + maxHeavy);
				System.out.println("	Other information: " + otherInfo);
				System.out.println("Submit job request (Y/N)?: ");
				userInput = scanner.next();
				if (userInput.equalsIgnoreCase("Y")) {
					Job job = new Job(jobName, jobMonth, jobDay, jobLocation, maxLight, maxMed, maxHeavy, otherInfo);
					currentUser.submitJob(job);
					//following try/catch is from: http://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
					try(FileWriter fileWriter = new FileWriter("supportfiles/jobs.txt", true);
					          BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
					          PrintWriter out = new PrintWriter(bufferWriter)){
					     out.print(jobName + "," + jobMonth + "," + jobDay + "," + jobLocation + "," + maxLight + "," + maxMed + "," +
									maxHeavy + "," + otherInfo + ",");
					  }  
					  catch( IOException e ){
					      // File writing/opening failed at some stage.
					  }
					System.out.println("---Your job has been sent in.---\n\n");
					System.out.println("---PARK MANAGER OPTIONS---");
					System.out.println("    1) Submit a new job request");
					System.out.println("    2) View upcoming jobs in parks I manage");
					System.out.println("    3) View the volunteers for a for a job in the parks I manage");
					System.out.println("    4) View my account information");
					System.out.println("    5) Logout");
				} else if (userInput.equalsIgnoreCase("N")) {
					System.out.println("---Job request cancelled.---\n\n");
					System.out.println("---PARK MANAGER OPTIONS---");
					System.out.println("    1) Submit a new job request");
					System.out.println("    2) View upcoming jobs in parks I manage");
					System.out.println("    3) View the volunteers for a for a job in the parks I manage");
					System.out.println("    4) View my account information");
					System.out.println("    5) Logout");
				} else {
					System.out.println("Type 'Y' for yes or 'N' for no.");
				}
			}
			else if (userInput.equals("2")) {// user selects menu choice 2
				System.out.println("---Upcoming jobs for parks I manage:---");
				for (String park : currentUser.getMyParks()) {
					System.out.println("	" + park);
					for (Job job : currentUser.getMyJobs()) {
						if (job.getLocation().equals(park)) {
							System.out.println("		" + job.getName() + " - " + job.getDay() + "/" + job.getMonth() + "/2015 @ " + job.getLocation());
						}
					}
				}
				System.out.println("---End of upcoming jobs list.---\n\n");
				System.out.println("---PARK MANAGER OPTIONS---");
				System.out.println("    1) Submit a new job request");
				System.out.println("    2) View upcoming jobs in parks I manage");
				System.out.println("    3) View the volunteers for a for a job in the parks I manage");
				System.out.println("    4) View my account information");
				System.out.println("    5) Logout");
			}
			else if (userInput.equals("3")) {// user selects menu choice 3
				System.out.println("---Volunteers for job I manage:---");
				System.out.println("Which job do you want to view volunteers for?");
//				for ()
			}
			else if(userInput.equals("4")) {// user selects menu choice 4
				System.out.println("Account details:");
				System.out.println("	Name: " + currentUser.getFirstName() + " " + currentUser.getLastName());
				System.out.println("	Email: " + currentUser.getEmail());
				System.out.println("	Phone number: " + currentUser.getPhoneNumber());
				System.out.println("	Address: " + currentUser.getAddress());
				System.out.println("	Access level: Park Manager");
				System.out.println();
				System.out.println();
				System.out.println("---PARK MANAGER OPTIONS---");
				System.out.println("    1) Submit a new job request");
				System.out.println("    2) View upcoming jobs in parks I manage");
				System.out.println("    3) View the volunteers for a for a job in the parks I manage");
				System.out.println("    4) View my account information");
				System.out.println("    5) Logout");
				
			} else {
				System.out.println("Not a valid command. Type 1, 2, 3, 4, or 5.");
			}
			userInput = scanner.next();
		}
		System.out.println("Goodbye.");
		System.out.println("---CONSOLE DEMO OFFLINE---");
		scanner.close();
	}
	
	//admin menu
	private static void administrator(String theEmail, ArrayList<Administrator> theAdminList) {
		Administrator currentUser = null;
		for (Administrator admin : theAdminList) {
			if (admin.getEmail().equals(theEmail)) {
				currentUser = admin;
			}
		}
		System.out.println();
		System.out.println("Welcome, Administrator " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
		System.out.println("---ADMINISTRATOR OPTIONS---");
		System.out.println("    1) Search volunteers by last name");
		System.out.println("    2) View my account information");
		System.out.println("    3) Logout");
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.next();
		while (!userInput.equals("3")) {
			
			if (userInput.equals("1")) { //user selects menu choice 1
				System.out.println("What is the last name of the volunteer you're looking for?");
				userInput = scanner.next();
				ArrayList<Volunteer> list = (ArrayList<Volunteer>) currentUser.searchVolunteers(userInput); //get list of same name volunteers
				System.out.println("List of volunteers with the last name '" + userInput + "':");
				for (Volunteer volunteer : list) { //iterate through that list of same name volunteers and print them
					System.out.println("    " + volunteer);
				}
				System.out.println();
				System.out.println();
				System.out.println("---ADMINISTRATOR OPTIONS---");
				System.out.println("    1) Search volunteers by last name");
				System.out.println("    2) View my account information");
				System.out.println("    3) Logout");
			} 
			
			else if (userInput.equals("2")) {// user selects menu choice 2
				System.out.println("Account details:");
				System.out.println("	Name: " + currentUser.getFirstName() + " " + currentUser.getLastName());
				System.out.println("	Email: " + currentUser.getEmail());
				System.out.println("	Phone number: " + currentUser.getPhoneNumber());
				System.out.println("	Address: " + currentUser.getAddress());
				System.out.println("	Access level: Administrator");
				System.out.println();
				System.out.println();
				System.out.println("---ADMINISTRATOR OPTIONS---");
				System.out.println("    1) Search volunteers by last name");
				System.out.println("    2) View my account information");
				System.out.println("    3) Logout");
			} 
			
			else { //invalid menu choice
				System.out.println("Not a valid command. Type 1, 2, or 3.");
			}
			userInput = scanner.next();
		}
		System.out.println("Goodbye.");
		System.out.println("---CONSOLE DEMO OFFLINE---");
		scanner.close();
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
		scanner.close();
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
		scanner.close();
		return list;
	}
	
	//populates list of park managers at startup
	private static ArrayList<ParkManager> populateManagers(final JobHandler theJobHandler, ArrayList<Job> theJobList) throws FileNotFoundException {
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
		
		//fill in each manager's park listing
		File parksForManagers = new File("supportfiles/managersparks.txt");
		Scanner scanner2 = new Scanner(parksForManagers);
		scanner2.useDelimiter(",");
		String email2;
		String parkName;
		while (scanner2.hasNext()) {
			email2 = scanner2.next();
			parkName = scanner2.next();
			if (email2.charAt(0) == '\r') {
				email2 = email2.substring(2);
			}
			for (ParkManager manager : list) { //add all parks
				if (manager.getEmail().equals(email2)) {
					manager.addPark(parkName);
				}
			}
		}
		
		//fill in each manager's job listing
		for (ParkManager manager: list) {
			for (String park : manager.getMyParks()) {
				for (Job job : theJobList) {
					if (job.getLocation().equals(park)) {
						manager.addJob(job);
					}
				}
			}
		}
		
		scanner2.close();
		scanner.close();
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
		scanner.close();
		return list;
	}



	private static ArrayList<Job> populateJobs() throws FileNotFoundException {
		File jobsFile = new File("supportfiles/jobs.txt");
		ArrayList<Job> list = new ArrayList<Job>();
		Scanner scanner = new Scanner(jobsFile);
		scanner.useDelimiter(",");
		
		String jobName = null;
		int jobMonth = 0;
		int jobDay = 0;
		String jobLocation;
		int maxLight = 0;
		int maxMed = 0;
		int maxHeavy = 0;
		String otherInfo ;
		
		while (scanner.hasNext() || scanner.hasNextInt()) {
			
			jobName = scanner.next();
			jobMonth = scanner.nextInt();
			jobDay = scanner.nextInt();
			jobLocation = scanner.next();
			maxLight = scanner.nextInt();
			maxMed = scanner.nextInt();
			maxHeavy = scanner.nextInt();
			otherInfo = scanner.next();
			if (jobName.charAt(0) == '\r') {
				jobName = jobName.substring(2);
			}
			Job job = new Job(jobName, jobMonth, jobDay, jobLocation, maxLight, maxMed, maxHeavy, otherInfo);
			list.add(job);
		}
		
		scanner.close();
		return list;
	}
} //end class
