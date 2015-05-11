import java.util.ArrayList;
import java.util.List;

/**
 * Represents an object which manages the list of available jobs for users to interact with
 * 
 * @author David Anderson, Dennis Kenyon, Brian Crabtree
 * @version 10May2015
 */
//changed getJobForVol to not use userid anymore
public class JobHandler {
	
	/** The list of jobs*/
	private List<Job> myJobList;
        
    /** The list of volunteers*/
    private List<Volunteer> myVolunteerList;
	
	/**
	 * Initializing the JobHandler
	 */
	public JobHandler(){
		myJobList = null;
		myVolunteerList = null;
	}
	
	/**
	 * Adds the volunteer to the volunteer list.
	 * @param theVolunteerToAdd the volunteer
	 * @return true if success, false otherwise
	 */
	public boolean addVolunteer(Volunteer theVolunteerToAdd){
		return myVolunteerList.add(theVolunteerToAdd);
	}
	
	/**
	 * Adds the given job object to the job list.
	 * @param theJobToAdd the job to add to the list
	 * @return true if success, false otherwise
	 */
	public boolean addJob(Job theJobToAdd){
		return myJobList.add(theJobToAdd);
	}
	
	/**
	 * Deletes the given job object from the job list.
	 * @param jobToDel the job to delete from the list
	 * @return true if success, false otherwise
	 */
	public boolean deleteJob(Job theJobToDelete){
		return myJobList.remove(theJobToDelete);
	}
	
	/**
	 * Finds a specified job matching the given unique jobID within the joblist and returns it
	 * @param theJobID the jobID to search for
	 * @return job object matching corresponding jobID or null if job does not exit
	 */
	public Job getJob(Job theJob){
		for (Job job : myJobList) {
			if (theJob.getName().equals(job.getName()) && theJob.getLocation().equals(job.getLocation())) {
				return job;
			}
		}
		return null;
	}
	
	/**
	 * Looks for jobs which the given volunteer has NOT signed up for
	 * @param theVolunteer the volunteer being analyzed
	 * @return a list of jobs which the volunteer has NOT signed up for
	 */
	public List<Job> getJobForVol(Volunteer theVolunteer){
		ArrayList<Job> returnList = new ArrayList<Job>();
		for (Job job : myJobList) {
			if (!theVolunteer.getJobs().contains(job)) {
				returnList.add(job);
			}
		}
		return returnList;
	}
        
    /**
     * Looks for volunteers by last name
     * @param theLastName the last name of the volunteer to be searched for
     * @return a list of volunteers with the specified last name
     */
    public List<Volunteer> getVolunteerByLastName(String theLastName) {
            
        // list of volunteers with required name
        List<Volunteer> matches = new ArrayList<Volunteer>();
        
        // iterating for readability's sake
        for(Volunteer aVolunteer : myVolunteerList) {
            if(aVolunteer.getLastName().equalsIgnoreCase(theLastName))
                matches.add(aVolunteer);
        }
        return matches;
    }
    
    /**
     * Prints a list of all volunteers in the system.
     */
    public void printVolunteers() {
    	for (Volunteer volunteer : myVolunteerList) {
    		System.out.println(volunteer);
    	}
    }
    
    
    /**
     * Populates the master volunteer list.
     * @param theList the list of volunteers
     */
    public void populateVolunteers(ArrayList<Volunteer> theList) {
    	myVolunteerList = theList;
    }
    
    
    /**
     * Populates the master job list.
     * @param theList the list of jobs
     */
    public void populateJobs(ArrayList<Job> theList) {
    	myJobList = theList;
    }
    
    
    /**
     * Gets a list of every job listed in the system.
     * @return a master list of every job.
     */
    public List<Job> getJobs() {
    	return myJobList;
    }
}
