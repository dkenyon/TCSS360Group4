import java.util.ArrayList;
import java.util.List;

/**
 * Represents a job.
 * 
 * @author David Anderson
 * @author Dennis Kenyon
 * @version 10May2015
 */

public class Job {
	
	/** A global counter set up so that each job has a unique integer ID given to it. */
	private static int jobIDCounter = 1;
	
	/**The name of the Job*/
	private String jobName;
	
	/**A unique ID number assigned to the Job*/
	private int jobID;
	
	/**A two integer representation of the date of the Job*/
	private int jobMonth;
	private int jobDate;
	
	/**Location of the Job*/
	private String jobLocation;
	
	/**Max light work volunteers*/
	private int maxLight;
	/**Max medium work volunteers*/
	private int maxMed;
	/**Max heavy work volunteers*/
	private int maxHeavy;
	
	/**List of volunteerIDs who have signed up for the Job*/
	private List<Volunteer> lightVolunteers;
	private List<Volunteer> mediumVolunteers;
	private List<Volunteer> heavyVolunteers;
	
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
	public Job( String theName, int theMonth, int theDate, String theLocation, int theMaxLight, int theMaxMed, int theMaxHeavy, String theInfo){
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
		
		lightVolunteers = new ArrayList<Volunteer>();
		mediumVolunteers = new ArrayList<Volunteer>();
		heavyVolunteers = new ArrayList<Volunteer>();
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
	public int getDay(){
		return jobDate;
	}
	
	/**
	 * Returns the location of the job
	 * @return the location
	 */
	public String getLocation(){
		return jobLocation;
	}
	
	/**
	 * Returns the maximum number of light volunteers needed
	 * @return max number of light volunteers
	 */
	public int getMaxLight(){
		return maxLight;
	}
	
	/**
	 * Returns the maximum number of medium volunteers needed
	 * @return max number of medium volunteers
	 */
	public int getMaxMed(){
		return maxMed;
	}
	
	/**
	 * Returns the maximum number of heavy volunteers needed
	 * @return max number of heavy volunteers
	 */
	public int getMaxHeavy(){
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
	public boolean signUpForLight(Volunteer theVolunteer){
		if(lightVolunteers.size()<maxLight && !this.containsVolunteer(theVolunteer)){
			lightVolunteers.add(theVolunteer);
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
	public boolean signUpForMedium(Volunteer theVolunteer){
		if(mediumVolunteers.size()<maxMed && !this.containsVolunteer(theVolunteer)){
			mediumVolunteers.add(theVolunteer);
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
	public boolean signUpForHeavy(Volunteer theVolunteer){
		if(heavyVolunteers.size()<maxHeavy && !this.containsVolunteer(theVolunteer)){
			heavyVolunteers.add(theVolunteer);
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
	public boolean containsVolunteer(Volunteer theVolunteer){
		if(lightVolunteers.contains(theVolunteer) || mediumVolunteers.contains(theVolunteer) || heavyVolunteers.contains(theVolunteer)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public List<Volunteer> getVolunteers(){
		List<Volunteer> volunteers = new ArrayList<Volunteer>();
		
		for(Volunteer volunteer : lightVolunteers){
			volunteers.add(volunteer);
		}
		for(Volunteer volunteer : mediumVolunteers){
			volunteers.add(volunteer);
		}
		for(Volunteer volunteer : heavyVolunteers){
			volunteers.add(volunteer);
		}
		
		return volunteers;
	}
	
	public List<Volunteer> getLightVolunteers() {
		List<Volunteer> volunteers = new ArrayList<Volunteer>();
		for (Volunteer volunteer : lightVolunteers) {
			volunteers.add(volunteer);
		}
		return volunteers;
	}
	
	public List<Volunteer> getMediumVolunteers() {
		List<Volunteer> volunteers = new ArrayList<Volunteer>();
		for (Volunteer volunteer : mediumVolunteers) {
			volunteers.add(volunteer);
		}
		return volunteers;
	}
	
	public List<Volunteer> getHeavyVolunteers() {
		List<Volunteer> volunteers = new ArrayList<Volunteer>();
		for (Volunteer volunteer : heavyVolunteers) {
			volunteers.add(volunteer);
		}
		return volunteers;
	}
}
