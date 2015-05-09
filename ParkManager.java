import java.util.ArrayList;
import java.util.List;


/**
 * Represents a park manager user.
 * 
 * @author David Anderson
 * @author Dennis Kenyon
 * @version 09May2015
 */

public class ParkManager extends AbstractUser {
	
	/**List of parks managed by Park Manager. Stored as a String for each park name.*/
	private List<String> myManagedParks;
	
	/**List of jobs managed by Park Manager. Stored as a unique integer ID number for each job*/
	private List<Job> myManagedJobs;
	
	/**Static reference to the JobHandler class*/
	private static JobHandler jobHandler;
	
	/**
	 * Init the fields of the class
     * @param theFirstName the first name of the user
     * @param theLastName the last name of the user
     * @param theEmail the user's email address
     * @param thePhoneNumber the user's phone number
     * @param theAddress the user's home address
	 * @param jHandler a reference to the job handler object which manages the jobs which this park manager may add/edit jobs to
	 */
	public ParkManager(String theFirstName, String theLastName, String theEmail, String thePhonenumber, String theAddress,JobHandler jHandler){
		super(theFirstName, theLastName,theEmail, thePhonenumber, theAddress);
		myManagedParks = new ArrayList<String>();
		myManagedJobs = new ArrayList<Job>();
		jobHandler = jHandler;
		
	}
	
	/**
	 * Adds a job to the list of jobs which may be signed up for
	 * @param myNewJob the new job
	 * @return true if job was posted successfully, false otherwise
	 */
	public boolean submitJob(Job myNewJob){
		return jobHandler.addJob(myNewJob);		
	}
	
	/**
	 * Returns a list of upcoming jobs this park manager managers
	 * @return list of upcoming jobs
	 */
	public List<Job> viewMyUpcomingJobs(){
		return myManagedJobs;
	}
//	
//	/**
//	 * Returns a list of volunteer ID numbers who have signed up for a given job
//	 * @param jobID the job to seach into
//	 * @return a list of volunteer ID numbers
//	 */
//	public List<Volunteer> viewVolunteers(Job theJob){
//		return jobHandler.getJobByID(jobID).getVolunteers();
//	}
	
	public void addJob(Job theJob) {
		myManagedJobs.add(theJob);
	}
	
	public void addPark(String thePark) {
		myManagedParks.add(thePark);
	}
	
	public List<String> getMyParks() {
		return myManagedParks;
	}
	
	public List<Job> getMyJobs() {
		return myManagedJobs;
	}

}
