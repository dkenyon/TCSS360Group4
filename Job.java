import java.util.ArrayList;
import java.util.List;

public class Job {
	
	/** A global counter set up so that each job has a unique integer ID given to it. */
	private static int jobIDCounter = 1;
	
	/**The name of the Job*/
	private String jobName;
	
	/**A unique ID number assigned to the Job*/
	private int jobID;
	
	/**A two integer represenation of the date of the Job*/
	private int jobMonth;
	private int jobDate;
	
	/**Location of the Job*/
	private int jobLocation;
	
	/**Max light work volunteers*/
	private int maxLight;
	/**Max medium work volunteers*/
	private int maxMed;
	/**Max heavy work volunteers*/
	private int maxHeavy;
	
	/**List of volunteerIDs who have signed up for the Job*/
	private List<Integer> lightVolunteers;
	private List<Integer> mediumVolunteers;
	private List<Integer> heavyVolunteers;
	
	/**Information about the Job*/
	private String jobInfo;
	
	/**
	 * Init the fields of the Job
	 * @param theName the name of the job
	 * @param theMonth the month when the job takes place
	 * @param theDate the day of the month when the job takes place
	 * @param theLocation the location of the job
	 * @param theMaxLight the maximum number of light workers needed
	 * @param theMaxMed the maximum number of medium workers needed
	 * @param theMaxHeavy the maximum number of heavy workers needed
	 * @param theInfo the information about the job
	 */
	public Job( String theName,int theMonth,int theDate,int theLocation,int theMaxLight,int theMaxMed, int theMaxHeavy, String theInfo){
		jobName = theName;
		jobMonth = theMonth;
		jobDate = theDate;
		jobLocation = theLocation;
		maxLight = theMaxLight;
		maxMed = theMaxMed;
		maxHeavy = theMaxHeavy;
		jobInfo = theInfo;
		
		jobID = jobIDCounter;
		jobIDCounter++;
		
		lightVolunteers = new ArrayList<Integer>();
		mediumVolunteers = new ArrayList<Integer>();
		heavyVolunteers = new ArrayList<Integer>();
	}
	
	/**
	 * Returns the jobs name
	 * @return the job name
	 */
	public String getName(){
		return jobName;
	}
	
	/**
	 * Returns the jobs ID number
	 * @return the jobID number
	 */
	public int getJobID(){
		return jobID;
	}
	
	/**
	 * Returns the month which the job will take place
	 * @return the month
	 */
	public int getMonth(){
		return jobMonth;
	}
	
	/**
	 * Returns the date when the job will take place 
	 * @return the date (int 0-31)
	 */
	public int getDate(){
		return jobDate;
	}
	
	/**
	 * Returns the location of the job
	 * @return the location
	 */
	public int getLocation(){
		return jobLocation;
	}
	
	/**
	 * Returns the maximum number of light volunteers needed
	 * @return max number of light volunteers
	 */
	public int getLight(){
		return maxLight;
	}
	
	/**
	 * Returns the maximum number of medium volunteers needed
	 * @return max number of medium volunteers
	 */
	public int getMed(){
		return maxMed;
	}
	
	/**
	 * Returns the maximum number of heavy volunteers needed
	 * @return max number of heavy volunteers
	 */
	public int getHeavy(){
		return maxHeavy;
	}
	
	/**
	 * Returns the information about the Job
	 * @return the info
	 */
	public String getInfo(){
		return jobInfo;
	}
	
	/**
	 * Adds a volunteer to a light work based spot
	 * @param VolID the volunteersID
	 * @return true if volunteer successfully added to job, false if volunteer is already signed up for job or if category is full
	 */
	public boolean signUpForLight(int VolID){
		if(lightVolunteers.size()<maxLight && !this.containsVolunteer(VolID)){
			lightVolunteers.add(VolID);
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Adds a volunteer to a medium work based spot
	 * @param VolID the volunteersID
	 * @return true if volunteer successfully added to job, false if volunteer is already signed up for job or if category is full
	 */
	public boolean signUpForMedium(int VolID){
		if(mediumVolunteers.size()<maxMed && !this.containsVolunteer(VolID)){
			mediumVolunteers.add(VolID);
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Adds a volunteer to a heavy work based spot
	 * @param VolID the volunteersID
	 * @return true if volunteer successfully added to job, false if volunteer is already signed up for job or if category is full
	 */
	public boolean signUpForHeavy(int VolID){
		if(heavyVolunteers.size()<maxHeavy && !this.containsVolunteer(VolID)){
			heavyVolunteers.add(VolID);
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Checks to see whether a volunteer has already signed up for a job
	 * @param VolID the volunteers ID
	 * @return true if volunteer has already signed up for job, false otherwise
	 */
	public boolean containsVolunteer(int VolID){
		if(lightVolunteers.contains(VolID) || mediumVolunteers.contains(VolID) || heavyVolunteers.contains(VolID)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public List<Integer> getVolunteers(){
		List<Integer> volunteers = new ArrayList<Integer>();
		
		for(int index=0;index<lightVolunteers.size();index++){
			volunteers.add(lightVolunteers.get(index));
		}
		for(int index=0;index<mediumVolunteers.size();index++){
			volunteers.add(mediumVolunteers.get(index));
		}
		for(int index=0;index<heavyVolunteers.size();index++){
			volunteers.add(heavyVolunteers.get(index));
		}
		
		return volunteers;
	}
}
