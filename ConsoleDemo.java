import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * A quick and dirty console demo of the park management program.
 * 
 * @author Dennis Kenyon
 * @author Brian Crabtree
 * @author David Anderson
 * @version 10May2015
 */

public class ConsoleDemo {
	
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
		
		AbstractUser currentUser = login(volunteerList, adminList, managerList); //login prompt
		if (currentUser instanceof Administrator) {
			administrator(currentUser.getEmail(), adminList);
		} else if (currentUser instanceof Volunteer) {
			volunteer(currentUser.getEmail(), volunteerList, jobList);
		} else if (currentUser instanceof ParkManager) {
			manager(currentUser.getEmail(), managerList, jobList);
		}
			
	} //end main
	
	/**
	 * A system login that is first prompted for when the program starts.
	 * User logs in by inputting his/her email address.
	 * @param theVolunteerList a prefilled list of volunteers
	 * @param theAdminList a prefilled list of administrators
	 * @param theManagerList a prefilled list of park managers
	 * @return the user that corresponds with the input email
	 */
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

	/**
	 * The administrator menu choice 'hub.'
	 * @param theEmail the admin's email used to log the admin on
	 * @param theAdminList a complete list of admin objects to help instantiate the current user with
	 */
	private static void administrator(String theEmail, ArrayList<Administrator> theAdminList) {
		Administrator currentUser = null;
		for (Administrator admin : theAdminList) {
			if (admin.getEmail().equals(theEmail)) {
				currentUser = admin;
			}
		}
		System.out.println();
		System.out.println("Welcome, Administrator " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
		promptAdminMenu();
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.next();
		while (!userInput.equals("3")) {
			if (userInput.equals("1")) { //user selects menu choice 1
				adminMenu1(currentUser, scanner);
			} else if (userInput.equals("2")) {// user selects menu choice 2
				adminMenu2(currentUser);
			} else { //invalid menu choice
				System.out.println("Not a valid command. Type 1, 2, or 3.");
			}
			userInput = scanner.next();
		}
		System.out.println("Goodbye.");
		System.out.println("---CONSOLE DEMO OFFLINE---");
		scanner.close();
	}

	/**
	 * The park manager menu choice 'hub.'
	 * @param theEmail the park manager's email used to log the park manager on
	 * @param theManagerList a complete list of park manager objects to help instantiate the current user with
	 * @param theJobList a master list of every job that exists
	 * @throws FileNotFoundException if persistent data files do not exist
	 */
	private static void manager(String theEmail, ArrayList<ParkManager> theManagerList, ArrayList<Job> theJobList) throws FileNotFoundException {
		ParkManager currentUser = null;
		for (ParkManager manager : theManagerList) {
			if (manager.getEmail().equals(theEmail)) {
				currentUser = manager;
			}
		}
		System.out.println();
		System.out.println("Welcome, ParkManager " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
		promptManagerMenu();
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.next();
		while (!userInput.equals("5")) {
			if (userInput.equals("1")) {// user selects menu choice 1				
				managerMenu1(currentUser, theJobList, scanner);
			}
			else if (userInput.equals("2")) {// user selects menu choice 2
				managerMenu2(currentUser);
			}
			else if (userInput.equals("3")) {// user selects menu choice 3
				managerMenu3(currentUser);
			}
			else if(userInput.equals("4")) {// user selects menu choice 4
				managerMenu4(currentUser);				
			} else {
				System.out.println("Not a valid command. Type 1, 2, 3, 4, or 5.");
			}
			userInput = scanner.next();
		}
		System.out.println("Goodbye.");
		System.out.println("---CONSOLE DEMO OFFLINE---");
		scanner.close();
	}
	
	/**
	 * The volunteer menu choice 'hub.'
	 * @param theEmail the volunteer's email used to log the volunteer on
	 * @param theVolunteerList a complete list of volunteer objects to help instantiate the current user with
	 * @param theJobList
	 */
	private static void volunteer(String theEmail, ArrayList<Volunteer> theVolunteerList, ArrayList<Job> theJobList) {
		Volunteer currentUser = null;
		for (Volunteer volunteer : theVolunteerList) {
			if (volunteer.getEmail().equals(theEmail)) {
				currentUser = volunteer;
			}
		}
		System.out.println();
		System.out.println("Welcome, Volunteer " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
		promptVolunteerMenu();
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.next();
		while (!userInput.equals("5")) {
			if (userInput.equals("1")) { //menu choice 1
				volunteerMenu1(currentUser, theJobList, scanner);
				
			} else if (userInput.equals("2")) {
				volunteerMenu2(currentUser);
			} else if (userInput.equals("3")) {
				volunteerMenu3(currentUser);
			} else if (userInput.equals("4")) {
				volunteerMenu4(currentUser);
			} else {
				System.out.println("Not a valid command. Type 1, 2, 3, 4 or 5.");
			}
			userInput = scanner.next();
		}
		System.out.println("Goodbye.");
		System.out.println("---CONSOLE DEMO OFFLINE---");
		scanner.close();
	}
	
	
	/**
	 * This method represents User Story 8: As an Administrator, I want to search volunteers by last name.
	 * @param currentUser the current admin user
	 * @param theScanner a passed scanner to take in text in the console
	 */
	private static void adminMenu1(Administrator currentUser, Scanner theScanner) {
		String userInput = null;
		System.out.println("What is the last name of the volunteer you're looking for?");
		userInput = theScanner.next();
		ArrayList<Volunteer> list = (ArrayList<Volunteer>) currentUser.searchVolunteers(userInput); //get list of same name volunteers
		System.out.println("List of volunteers with the last name '" + userInput + "':");
		for (Volunteer volunteer : list) { //iterate through that list of same name volunteers and print them
			System.out.println("    " + volunteer);
		}
		System.out.println();
		System.out.println();
		promptAdminMenu();
	}

	/**
	 * Lists the account details for this admin.
	 * @param currentUser the current admin user
	 */
	private static void adminMenu2(Administrator currentUser) {
		System.out.println("Account details:");
		System.out.println("	Name: " + currentUser.getFirstName() + " " + currentUser.getLastName());
		System.out.println("	Email: " + currentUser.getEmail());
		System.out.println("	Phone number: " + currentUser.getPhoneNumber());
		System.out.println("	Address: " + currentUser.getAddress());
		System.out.println("	Access level: Administrator");
		System.out.println();
		System.out.println();
		promptAdminMenu();
	}
	
	
	/**
	 * This method represents User Story 1: As a Park Manager, I want to submit a new job request.
	 * @param currentUser the current park manager user
	 * @param theJobList a master list of all jobs that exist
	 * @param scanner a passed scanner to take in text in the console
	 */
	private static void managerMenu1(ParkManager currentUser, ArrayList<Job> theJobList, Scanner scanner) {
		String userInput = null;
		Calendar cal = Calendar.getInstance();
	    int currentDay = cal.get(Calendar.DAY_OF_MONTH);
	    int currentMonth = cal.get(Calendar.MONTH) + 1;
	    int currentDayCount = (currentMonth * 30) + currentDay;
	         
		//check to see if 30 total jobs exist
		if (theJobList.size() == 30) {
			System.out.println("--ERROR: Too many pending jobs (30) in the system exist. Try again later.");
			System.out.println();
			promptManagerMenu();
		} else {
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
			jobMonth = scanner.nextInt();
			int leftBound7Day = (jobMonth * 30 + jobDay) - 3; // used for enforcing business rule 2
		    int rightBound7Day = (jobMonth * 30 + jobDay) + 3; // used for enforcing business rule 2
			int businessRule2Counter = 0; //used for business rule 2; if over 5, can't add this job
		    for (Job job : theJobList) { //used to tally businessRule2Counter
		    	int thisJobsDayCount = job.getMonth() * 30 + job.getDay();
		    	if (thisJobsDayCount <= rightBound7Day && thisJobsDayCount >= leftBound7Day) {
		    		businessRule2Counter++;
		    	}
		    }
			//check  for business rule 5
			if (currentDayCount > ((jobMonth * 30) + jobDay)) {
				System.out.println("--ERROR: You can't add a job that is supposed to happen in the past.");
				System.out.println();
				System.out.println();
				promptManagerMenu();
			} else if ((jobMonth * 30) + jobDay - (currentMonth * 30) + currentDay > 90) {
				System.out.println("--ERROR: Job is too far from today's date. Jobs must be no more than three months in the future.");
				System.out.println();
				System.out.println();
				promptManagerMenu();
			} else if (businessRule2Counter > 4) { //check for business rule 2
				System.out.println("--ERROR: There are already five jobs scheduled in this seven day period, so this job can't be added.");
				System.out.println();
				System.out.println();
				promptManagerMenu();
				//end business rule 2 check
			}
			else {
				System.out.print("Park name: ");
				scanner.nextLine();
				jobLocation = scanner.nextLine();
				if (!currentUser.ownsPark(jobLocation)) {
					System.out.println("--ERROR: You don't own this park and therefore can't create a job for it.");
					System.out.println();
					System.out.println();
					promptManagerMenu();
				} else {
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
						currentUser.addJob(job); //not only does this job save in the persistent data, but it is also added in this session's job list
						promptManagerMenu();
					} else if (userInput.equalsIgnoreCase("N")) {
						System.out.println("---Job request cancelled.---\n\n");
						promptManagerMenu();
					} else {
						System.out.println("Type 'Y' for yes or 'N' for no.");
					}
				}
				
			}
			}
	}
	
	
	/**
	 * This method represents User Story 5: As a Park Manager, I want to view upcoming jobs in the parks that I manage.
	 * @param currentUser the current park manager user
	 */
	private static void managerMenu2(ParkManager currentUser) {
		System.out.println("---Upcoming jobs for parks I manage:---");
		for (String park : currentUser.getMyParks()) {
			System.out.println("	" + park);
			for (Job job : currentUser.getMyJobs()) {
				if (job.getLocation().equals(park)) {
					System.out.println("		" + job.getName() + " - " + job.getMonth() + "/" + job.getDay() + "/2015");
				}
			}
		}
		System.out.println("---End of upcoming jobs list.---\n\n");
		promptManagerMenu();
	}

	
	/**
	 * This method represents User Story 6: As a Park Manager, I want to view the volunteers for a job in the parks that I manage.
	 * @param currentUser the current park manager user
	 */
	private static void managerMenu3(ParkManager currentUser) {
		System.out.println("---Volunteers for jobs I manage:---");
		
		for (Job job : currentUser.getMyJobs()) {
			System.out.println("	Job: " + job.getName() + " @ " + job.getLocation());
			if (job.getVolunteers().size() == 0) {
				System.out.println("	-NO VOLUNTEERS POSTED FOR THIS JOB-");
			} else {
				for (Volunteer volunteer : job.getVolunteers()) {
					System.out.println("		" + volunteer);
				}
			}
			
			System.out.println();
			System.out.println();
		}
		promptManagerMenu();
	}
	
	
	/**
	 * Lists the account details for this park manager.
	 * @param currentUser the current park manage user
	 */
	private static void managerMenu4(ParkManager currentUser) {
		System.out.println("Account details:");
		System.out.println("	Name: " + currentUser.getFirstName() + " " + currentUser.getLastName());
		System.out.println("	Email: " + currentUser.getEmail());
		System.out.println("	Phone number: " + currentUser.getPhoneNumber());
		System.out.println("	Address: " + currentUser.getAddress());
		System.out.println("	Access level: Park Manager");
		System.out.println();
		System.out.println();
		promptManagerMenu();
	}
	
	
	/**
	 * This method represents User Story 3: As a Volunteer, I want to volunteer for a job.
	 * @param currentUser the current volunteer user
	 * @param theJobList a master list of all the jobs that exist in the system
	 * @param scanner a passed scanner to take in text in the console
	 */
	private static void volunteerMenu1(Volunteer currentUser, ArrayList<Job> theJobList, Scanner scanner) {
		int potentialJobDay = 0;
		int potentialJobMonth = 0;
		//String jobName= null;
		//String workLoad = null;
		System.out.println("What is the name of the job you want to sign up for?");
		scanner.nextLine();
		String jobName = scanner.nextLine();
		
		//check to see that business rule 7 is not violated
		for (Job job : theJobList) {
			if (job.getName().equalsIgnoreCase(jobName)) {
				potentialJobDay = job.getDay();
				potentialJobMonth = job.getMonth();
			}
		}
		int existingJobDay = 0;
		int existingJobMonth = 0;
		String existingJobName = null;
		for (Job job : currentUser.getJobs()) {
			if (job.getDay() == potentialJobDay && job.getMonth() == potentialJobMonth) {
				existingJobDay = potentialJobDay;
				existingJobMonth = potentialJobMonth;
				existingJobName = job.getName();
			}
		}
		if (existingJobDay == potentialJobDay && existingJobMonth == potentialJobMonth) {
			System.out.println("--ERROR: Because " + jobName + " has the same date as " + existingJobName + ", you aren't able to sign up for it.");
			System.out.println();
			System.out.println();
			promptVolunteerMenu();
			//end business rule 7 check
		} else {
			for (Job job : theJobList) {
				if (job.getName().equals(jobName)) {
					System.out.println();
					System.out.println("Job Description: ");
					System.out.println("	" + job.getInfo());
					System.out.println();
					
					System.out.println("Current workload demands for this job (# of slots filled / # of maximum for that category): ");
					System.out.println("	Light: " + job.getLightVolunteers().size() + "/" + job.getMaxLight()); 
					System.out.println("	Medium: " + job.getMediumVolunteers().size() + "/" + job.getMaxMed());
					System.out.println("	Heavy: " + job.getHeavyVolunteers().size() + "/" + job.getMaxHeavy());
				}
			}
			System.out.println();
			System.out.println("What is the work load you're able to contribute (type either heavy, medium, or light)?");
			String workLoad = scanner.next();
			int intWorkLoad = -1;
			if (workLoad.equals("heavy")) {
				intWorkLoad = 2;
			} else if (workLoad.equals("medium")) {
				intWorkLoad = 1;
			} else if (workLoad.equals("light")) {
				intWorkLoad = 0;
			} else {
				System.out.println("Enter either heavy, medium, or light for the workload you can contribute.");
			}
			// check for business rule 3
			Job thisJob = null;
			for (Job job : theJobList) {
				if (job.getName().equals(jobName)) {
					thisJob = job;
				}
			}
			if (workLoad.equalsIgnoreCase("heavy") && (thisJob.getHeavyVolunteers().size() / thisJob.getMaxHeavy()) == 1) {
				System.out.println("--ERROR: Heavy volunteer slots are already filled!");
				System.out.println();
				System.out.println();
				promptVolunteerMenu();
			} else if (workLoad.equals("medium") && (thisJob.getMediumVolunteers().size() / thisJob.getMaxMed()) == 1) {
				System.out.println("--ERROR: Medium volunteer slots are already filled!");
				System.out.println();
				System.out.println();
				promptVolunteerMenu();
			} else if (workLoad.equals("light") && (thisJob.getLightVolunteers().size() / thisJob.getMaxLight()) == 1) {
				System.out.println("--ERROR: Light volunteer slots are already filled!");
				System.out.println();
				System.out.println();
				promptVolunteerMenu();
				//end check for business rule 3
			} else {
				for (Job job : currentUser.viewJobsCanSignUpFor()) {
					if (jobName.equals(job.getName())) {
						if (currentUser.signUpForJob(job, intWorkLoad) == true) {
							System.out.println("You have succesfully signed up for " + job.getName() 
									+ " - " + job.getDay() + "/" + job.getMonth() + "/2015 @ " + job.getLocation() + "!");
							//following try/catch is from: http://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
							try(FileWriter fileWriter = new FileWriter("supportfiles/volunteersAndJobs.txt", true);
							          BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
							          PrintWriter out = new PrintWriter(bufferWriter)){
							     out.print(currentUser.getEmail() + "," + job.getName() + "," + workLoad + ",");
							  }  
							  catch( IOException e ){
								  System.out.println("failed to write");
							      // File writing/opening failed at some stage.
							  }
							System.out.println();
							System.out.println();
							promptVolunteerMenu();
						} else {
							System.out.println("You were not able to sign up for the job. Review that you're qualified to before signing up.\n\n");
							promptVolunteerMenu();
						};
					}
				}
			}
		}
	}

	
	/**
	 * This represents User Story 2: As a Volunteer, I want to view upcoming jobs I can sign up for.
	 * @param currentUser the current volunteer user
	 */
	private static void volunteerMenu2(Volunteer currentUser) {
		System.out.println("Available jobs I can sign up for: ");
		for (Job job : currentUser.viewJobsCanSignUpFor()) {
			System.out.println("	" + job.getName() + " - " + job.getMonth() + "/" + job.getDay() + "/2015 @ " + job.getLocation());
		}
		System.out.println("-End of available jobs list.-");
		System.out.println();
		System.out.println();
		promptVolunteerMenu();
	}
	
	
	/**
	 * This method represents User Story 4: As a Volunteer, I want to view the jobs I am signed up for.
	 * @param currentUser
	 */
	private static void volunteerMenu3(Volunteer currentUser) {
		System.out.println();
		System.out.println("Jobs currently signed up for:");
		if (currentUser.getJobs().size() == 0) {
			System.out.println("-NOT SIGNED UP FOR ANY JOBS YET-");
			System.out.println();
			System.out.println();
			promptVolunteerMenu();
		} else {
			for (Job job : currentUser.getJobs()) {
				System.out.println("	" + job.getName() + " - " + job.getMonth() + "/" + job.getDay() + "/2015 @ " + job.getLocation());
			}
			System.out.println();
			System.out.println();
			promptVolunteerMenu();
		}
	}
	
	
	/**
	 * Lists the account details for this volunteer.
	 * @param currentUser the current volunteer user
	 */
	private static void volunteerMenu4(Volunteer currentUser) {
		System.out.println("Account details:");
		System.out.println("	Name: " + currentUser.getFirstName() + " " + currentUser.getLastName());
		System.out.println("	Email: " + currentUser.getEmail());
		System.out.println("	Phone number: " + currentUser.getPhoneNumber());
		System.out.println("	Address: " + currentUser.getAddress());
		System.out.println("	Access level: Volunteer");
		System.out.println();
		System.out.println();
		promptVolunteerMenu();
	}
	
	
	/**
	 * Prints out the park manager menu prompt.
	 */
	private static void promptManagerMenu() {
		System.out.println("---PARK MANAGER OPTIONS---");
		System.out.println("    1) Submit a new job request");
		System.out.println("    2) View upcoming jobs in parks I manage");
		System.out.println("    3) View the volunteers for a for a job in the parks I manage");
		System.out.println("    4) View my account information");
		System.out.println("    5) Logout");
	}
	
	
	/**
	 * Prints out the volunteer menu prompt.
	 */
	private static void promptVolunteerMenu() {
		System.out.println("---VOLUNTEER OPTIONS---");
		System.out.println("    1) Sign up for a job");
		System.out.println("    2) View upcoming jobs I can sign up for");
		System.out.println("    3) View jobs currently signed up for");
		System.out.println("    4) View my account information");
		System.out.println("    5) Logout");
	}
	
	
	/**
	 * Prints out the admin menu prompt.
	 */
	private static void promptAdminMenu() {
		System.out.println("---ADMINISTRATOR OPTIONS---");
		System.out.println("    1) Search volunteers by last name");
		System.out.println("    2) View my account information");
		System.out.println("    3) Logout");
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
	private static ArrayList<Volunteer> populateVolunteers(JobHandler theJobHandler, ArrayList<Job> theJobList) throws FileNotFoundException {
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
			Volunteer volunteer = new Volunteer(firstName, lastName, email, phoneNumber, address, theJobHandler);
			list.add(volunteer);
		}
		
		//fill in volunteers' jobs
		File volunteerJobsFile = new File("supportfiles/volunteersAndJobs.txt");
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
