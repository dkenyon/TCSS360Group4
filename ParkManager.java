import java.util.ArrayList;
import java.util.List;

public class ParkManager extends AbstractUser {
	
	/**List of parks managed by Park Manager. Stored as a unique integer ID number for each park*/
	private List<Integer> myManagedParks;
	
	/**List of jobs managed by Park Manager. Stored as a unique integer ID number for each job*/
	private List<Integer> myManagedJobs;
	
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
		jobHandler = jHandler;
		
	}
	
	/**
	 * Adds a job to the list of jobs which may be signed up for
	 * @param myNewJob the new nob
	 * @return true if job was posted successfully, false otherwise
	 */
	private boolean submitJob(Job myNewJob){
		return jobHandler.addJob(myNewJob);		
	}
	
	/**
	 * Returns a list of upcoming jobs this park manager managers
	 * @return list of upcoming jobs
	 */
	private List<Job> viewMyUpcomingJobs(){
		List<Job> myUpcoming = new ArrayList<Job>();
		for(int index=0;index<myManagedJobs.size();index++){
			myUpcoming.add(jobHandler.getJobByID(myManagedJobs.get(index)));
		}
		return myUpcoming;
	}
	
	/**
	 * Returns a list of volunteer ID numbers who have signed up for a given job
	 * @param jobID the job to seach into
	 * @return a list of volunteer ID numbers
	 */
	private List<Integer> viewVolunteers(int jobID){
		return jobHandler.getJobByID(jobID).getVolunteers();
	}

}
