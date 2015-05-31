
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A quick and dirty console demo of the park management program.
 * 
 * @author Dennis Kenyon
 * @author Brian Crabtree
 * @author David Anderson
 * @version 31May2015
 */

public class ConsoleDemo {
	
	/**
	 * A static scanner to be passed to any instantiating class using System.in.
	 */
	static Scanner scanner = new Scanner(System.in);
	
	//MAIN
	public static void main(final String[] theArgs) throws FileNotFoundException {
		
		// initialize variables
		
		JobHandler jobHandler = new JobHandler();
		ArrayList<Job> jobList = populateJobs(); //read in all jobs
		ArrayList<Volunteer> volunteerList = populateVolunteers(jobHandler, jobList); //read in all volunteers
		ArrayList<Administrator> adminList = populateAdmins(jobHandler); //read in all admins
		jobHandler.populateVolunteers(volunteerList);
		jobHandler.populateJobs(jobList);
		ArrayList<ParkManager> managerList = populateManagers(jobHandler, jobList); //read in all park managers
		// end initialize variables
		
		login(volunteerList, adminList, managerList, jobList); //login prompt
			
	} //end main
	
	/**
	 * A system login that is first prompted for when the program starts.
	 * User logs in by inputting his/her email address.
	 * @param theVolunteerList a prefilled list of volunteers
	 * @param theAdminList a prefilled list of administrators
	 * @param theManagerList a prefilled list of park managers
	 * @return the user that corresponds with the input email
	 * @throws FileNotFoundException 
	 */
	private static void login(ArrayList<Volunteer> theVolunteerList, ArrayList<Administrator> theAdminList, ArrayList<ParkManager> theManagerList, ArrayList<Job> theJobList) throws FileNotFoundException {
		
		System.out.println("---LOGIN---");
		System.out.println("User email: ");
		String userInput = scanner.next();
		while (!goodLogin(userInput, theVolunteerList, theManagerList, theAdminList)) {
			System.out.println("Bad login, try again.");
			userInput = scanner.next();
		}
		for (Volunteer volunteer : theVolunteerList) {
			if (volunteer.getEmail().equals(userInput)) {
				ConsoleVolunteerUI volunteerLogin = new ConsoleVolunteerUI(userInput, theVolunteerList, theJobList, scanner);
			}
		}
		for (ParkManager manager : theManagerList) {
			if (manager.getEmail().equals(userInput)) {
				ConsoleManagerUI managerLogin = new ConsoleManagerUI(userInput, theManagerList, theJobList, scanner);
			}
		}
		for (Administrator admin : theAdminList) {
			if (admin.getEmail().equals(userInput)) {
				ConsoleAdminUI adminLogin = new ConsoleAdminUI(userInput, theAdminList, scanner);
			}
		}
		scanner.close();
	}

	
	/**
	 * A helper method that checks to see that the user has a valid login entry (just an email match).
	 * @param theInput the email the user typed in
	 * @param theVolunteerList a list of volunteers to compare emails with
	 * @param theManagerList a list of managers to compare emails with
	 * @param theAdminList a list of admins to compare emails with
	 * @return true if there is an email match, false otherwise
	 */
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

	/*------------------------------------INITIALIZATION METHODS------------------------------------*/
	/*----------------------------------------------------------------------------------------------*/

	//populates list of administrators at startup
	private static ArrayList<Administrator> populateAdmins(final JobHandler theJobHandler) throws FileNotFoundException {
		//File adminsFile = new File("src/admins.txt");
		InputStream adminsFile = ConsoleDemo.class.getResourceAsStream("/admins.txt");
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
//		File managersFile = new File("src/parkmanagers.txt");
		InputStream managersFile = ConsoleDemo.class.getResourceAsStream("/parkmanagers.txt");
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
		//File parksForManagers = new File("src/managersparks.txt");
		InputStream parksForManagers = ConsoleDemo.class.getResourceAsStream("/managersparks.txt");
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
	private static ArrayList<Volunteer> populateVolunteers(JobHandler theJobHandler, ArrayList<Job> theJobList) throws FileNotFoundException {
//		File volunteersFile = new File("src/volunteers.txt");
		InputStream volunteersFile = ConsoleDemo.class.getResourceAsStream("/volunteers.txt");
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
			Volunteer volunteer = new Volunteer(firstName, lastName, email, phoneNumber, address, theJobHandler);
			list.add(volunteer);
		}
		
		//fill in volunteers' jobs
//		File volunteerJobsFile = new File("src/volunteersAndJobs.txt");
		InputStream volunteerJobsFile = ConsoleDemo.class.getResourceAsStream("/volunteersAndJobs.txt");
		Scanner scanner2 = new Scanner(volunteerJobsFile);
		scanner2.useDelimiter(",");
		while (scanner2.hasNext()) {
			String email2 = scanner2.next();
			String jobName = scanner2.next();
			String load = scanner2.next();
			if (email2.charAt(0) == '\r') {
				email2 = email2.substring(2);
			}
			for (Volunteer volunteer2 : list) {
				for (Job job : theJobList) {
					if (volunteer2.getEmail().equals(email2) && job.getName().equals(jobName)) {
						volunteer2.addJob(job);
						if (load.equals("light")) {
							job.signUpForLight(volunteer2);
						} else if (load.equals("medium")) {
							job.signUpForMedium(volunteer2);
						} else if (load.equals("heavy")) {
							job.signUpForHeavy(volunteer2);
						}
					}
				}
			}
		}

		scanner.close();
		scanner2.close();
		return list;
	}


	private static ArrayList<Job> populateJobs() throws FileNotFoundException {
		//File jobsFile = new File("src");
		InputStream jobsFile = ConsoleDemo.class.getResourceAsStream("/jobs.txt");
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
		String otherInfo;

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
