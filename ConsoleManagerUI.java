import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Represents an instance of a manager login session.
 * @author Dennis Kenyon
 * @author Brian Crabtree
 * @author David Anderson
 * @version 31May2015
 *
 */

public class ConsoleManagerUI {

	/**
	 * A shared Scanner resource used for any place there is System.in.
	 */
	Scanner myScanner = null;
	
	public ConsoleManagerUI(String theEmail, ArrayList<ParkManager> theManagerList, ArrayList<Job> theJobList, Scanner theScanner) throws FileNotFoundException {
		myScanner = theScanner;
		this.managerMenu(theEmail, theManagerList, theJobList);
	}
	
	/**
	 * The park manager menu choice 'hub.'
	 * @param theEmail the park manager's email used to log the park manager on
	 * @param theManagerList a complete list of park manager objects to help instantiate the current user with
	 * @param theJobList a master list of every job that exists
	 * @throws FileNotFoundException if persistent data files do not exist
	 */
	private void managerMenu(String theEmail, ArrayList<ParkManager> theManagerList, ArrayList<Job> theJobList) throws FileNotFoundException {
		ParkManager currentUser = null;
		for (ParkManager manager : theManagerList) {
			if (manager.getEmail().equals(theEmail)) {
				currentUser = manager;
			}
		}
		System.out.println();
		System.out.println("Welcome, ParkManager " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
		promptManagerMenu();
		String userInput = myScanner.next();
		while (!userInput.equals("5")) {
			if (userInput.equals("1")) {// user selects menu choice 1				
				managerMenu1(currentUser, theJobList, myScanner);
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
			userInput = myScanner.next();
		}
		System.out.println("Goodbye.");
		System.out.println("---CONSOLE DEMO OFFLINE---");
	}
	
	/**
	 * This method represents User Story 1: As a Park Manager, I want to submit a new job request.
	 * @param currentUser the current park manager user
	 * @param theJobList a master list of all jobs that exist
	 * @param scanner a passed scanner to take in text in the console
	 */
	private void managerMenu1(ParkManager currentUser, ArrayList<Job> theJobList, Scanner scanner) {
		String userInput = null;
		Calendar cal = Calendar.getInstance();
	    int currentDay = cal.get(Calendar.DAY_OF_MONTH);
	    int currentMonth = cal.get(Calendar.MONTH) + 1;
	    int currentDayCount = (currentMonth * 30) + currentDay;
	         
		//check to see if 30 total jobs exist business rule 1
		if (businessRuleOneCheck(theJobList)) {
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
			
			if (!businessRuleFiveCheck(currentDayCount, jobMonth, jobDay)) { //IF BUSINESS RULE 5 VIOLATION
				promptManagerMenu();
			} else if (!businessRuleTwoCheck(theJobList, jobMonth, jobDay, currentMonth, currentDay)) { //IF BUSINESS RULE 2 VIOLATION
				promptManagerMenu();
			}
			else {
				System.out.print("Park name: ");
				scanner.nextLine();
				jobLocation = scanner.nextLine();
				if (!businessRuleEightCheck(currentUser, jobLocation)) { //IF BUSINESS RULE 8 VIOLATION
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
						try(FileWriter fileWriter = new FileWriter("src/jobs.txt", true);
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
	private void managerMenu2(ParkManager currentUser) {
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
	private void managerMenu3(ParkManager currentUser) {
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
	private void managerMenu4(ParkManager currentUser) {
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
	 * Prints out the park manager menu prompt.
	 */
	private void promptManagerMenu() {
		System.out.println("---PARK MANAGER OPTIONS---");
		System.out.println("    1) Submit a new job request");
		System.out.println("    2) View upcoming jobs in parks I manage");
		System.out.println("    3) View the volunteers for a for a job in the parks I manage");
		System.out.println("    4) View my account information");
		System.out.println("    5) Logout");
	}
	
	/**
	 * Checks to see if business rule 1 is violated.
	 * Business rule 1 is: A job may not be added if the total number of pending jobs is currently 30.
	 * @param theJobList the master job list
	 * @return true if it is violated, false otherwise
	 */
	private boolean businessRuleOneCheck(ArrayList<Job> theJobList) {
		if (theJobList.size() >= 30) {
			System.out.println("--ERROR: Too many pending jobs (30) in the system exist. Try again later.");
			System.out.println();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks to see if business rule 2 is violated.
	 * Business rule 2: A job may not be added if the total number of pending jobs during that week (3 days on either side of the job days)
	 * is currently 5. In other words, during any consecutive 7 day period there can be no more than 5 jobs.
	 * @theJobList the master job list
	 * @jobMonth the month
	 * @jobDay the day
	 * @currentMonth the current month from today's date
	 * @currentDay the current day from today's date
	 * @return false if this business rule is violated, true otherwise
	 */
	private boolean businessRuleTwoCheck(ArrayList<Job> theJobList, int jobMonth, int jobDay, int currentMonth, int currentDay) {
		int leftBound7Day = (jobMonth * 30 + jobDay) - 3; // used for enforcing business rule 2
	    int rightBound7Day = (jobMonth * 30 + jobDay) + 3; // used for enforcing business rule 2
		int businessRule2Counter = 0; //used for business rule 2; if over 5, can't add this job
	    for (Job job : theJobList) { //used to tally businessRule2Counter
	    	int thisJobsDayCount = job.getMonth() * 30 + job.getDay();
	    	if (thisJobsDayCount <= rightBound7Day && thisJobsDayCount >= leftBound7Day) {
	    		businessRule2Counter++;
	    	}
	    }
	    if ((jobMonth * 30) + jobDay - (currentMonth * 30) + currentDay > 90) {
			System.out.println("--ERROR: Job is too far from today's date. Jobs must be no more than three months in the future.");
			System.out.println();
			System.out.println();
			return false;
		} else if (businessRule2Counter > 4) { //check for business rule 2
			System.out.println("--ERROR: There are already five jobs scheduled in this seven day period, so this job can't be added.");
			System.out.println();
			System.out.println();
			return false;
		}
	    return true;
	}
	/**
	 * Checks to see if business rule 5 is violated.
	 * Business rule 5: A job may not be added that is in the past or more than three months in the future.
	 * @return false if violated, true otherwise
	 */
	private boolean businessRuleFiveCheck(int currentDayCount, int jobMonth, int jobDay) {
		if (currentDayCount > ((jobMonth * 30) + jobDay)) {
			System.out.println("--ERROR: You can't add a job that is supposed to happen in the past.");
			System.out.println();
			System.out.println();
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Checks to see if business rule 8 is violated.
	 * Business rule 8: A Park Manager can create jobs only for those parks that he/she manages.
	 * @param currentUser the current park manager user session
	 * @param jobLocation the park location's name
	 * @return false if a park manager does not own this jobLocation, true otherwise
	 */
	private boolean businessRuleEightCheck(ParkManager currentUser, String jobLocation) {
		if (!currentUser.ownsPark(jobLocation)) {
			System.out.println("--ERROR: You don't own this park and therefore can't create a job for it.");
			System.out.println();
			System.out.println();
			return false;
		} else {
			return true;
		}
	}
}
