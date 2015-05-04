import java.util.ArrayList;
import java.util.List;
/**
 * Represents an object which manages the list of available jobs for users to interact with
 */
public class JobHandler {
	
	/** The list of jobs*/
	private List<Job> jobList;
	
	/**
	 * Initializing the JobHandler
	 */
	public JobHandler(){
		jobList = new ArrayList<Job>();
	}
	
	/**
	 * Adds the given job object to the job list.
	 * @param jobToAdd the job to add to the list
	 * @return true if success, false otherwise
	 */
	public boolean addJob(Job jobToAdd){
		return jobList.add(jobToAdd);
	}
	
	/**
	 * Deletes the given job object from the job list.
	 * @param jobToDel the job to delete from the list
	 * @return true if success, false otherwise
	 */
	public boolean deleteJob(Job jobToDel){
		return jobList.remove(jobToDel);
	}
	
	/**
	 * Finds a specified job matching the given unique jobID within the joblist and returns it
	 * @param jobID the jobID to search for
	 * @return job object matching coresponding jobID or null if job does not exit
	 */
	public Job getJobByID(int jobID){
		Job ptr = null;
		for(int index=0;index<jobList.size();index++){
			ptr = jobList.get(index);
			if(ptr.getJobID() == jobID){
				return ptr;
			}
		}
		return null;
	}
	
	/**
	 * Looks for jobs which the given volunteer has NOT signed up for
	 * @param volunteerID in the volunteer to search for
	 * @return a list of jobs which the volunteer has NOT signed up for
	 */
	public List<Job> getJobForVol(int volunteerID){
		List<Job> returnList = new ArrayList<Job>();
		Job ptr = null;
		for(int index=0;index<jobList.size();index++){
			ptr = jobList.get(index);
			if(!ptr.containsVolunteer(volunteerID)){
				returnList.add(ptr);
			}
		}
		return returnList;
	}
}
