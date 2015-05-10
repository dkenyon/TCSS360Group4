
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Volunteer class
 * 
 * @author Brian Crabtree
 * @author Dennis Kenyon
 */

public class Volunteer extends AbstractUser {
    
    private List<Job> myJobs;  // jobs this volunteer is signed up for
    private static JobHandler myJobHandler; // the job handler (one for all)

    /**
     * initializes instance fields
     * @param theFirstName the first name of the volunteer
     * @param theLastName the last name of the volunteer
     * @param theEmail the volunteer's email address
     * @param thePhoneNumber the volunteer's phone number
     * @param theAddress the volunteer's home address
     */
    public Volunteer(String theFirstName, String theLastName, String theEmail, 
            String thePhoneNumber, String theAddress, JobHandler theJobHandler) {
        super(theFirstName, theLastName, theEmail, thePhoneNumber, theAddress);
        myJobHandler = theJobHandler;
        myJobs = new ArrayList<Job>();
    }
    
    /**
     * Lists jobs available.
     * @return list of jobs
     */
    public List<Job> viewJobsCanSignUpFor() {
    	return myJobHandler.getJobForVol(this);
    }
    
    /**
     * Signs this volunteer up for a job.
     * @param JobID
     * @param WorkCat 
     * @return boolean success or failure
     */
    public boolean signUpForJob(Job theJob, Integer theWorkCategory) {
        
        // find out what day / month it is
        Calendar cal = Calendar.getInstance();
        int myCurDate = cal.get(Calendar.DAY_OF_MONTH);
        int myCurMonth = cal.get(Calendar.MONTH);
        
        Job job = myJobHandler.getJob(theJob);
        
        // check for other jobs on this date
        if (myJobs.stream().noneMatch((aJob)->(aJob.getDay()==job.getDay()))
                && myCurDate <= job.getDay() && myCurMonth <= job.getMonth()) {

            switch(theWorkCategory) {   // sign up for the correct workload

                case 0: if (job.signUpForLight(this)) myJobs.add(job);
                    break;

                case 1: if (job.signUpForMedium(this)) myJobs.add(job);
                    break;

                case 2: if (job.signUpForHeavy(this)) myJobs.add(job);
                    break;

                default: return false; // invalid workload category
            }
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Lists jobs this volunteer is signed up for.
     * @return list of jobs
     */
    public List<Job> getJobs() {
        return myJobs;
    }
    
    public void addJob(Job theJob) {
    	myJobs.add(theJob);
    }
    
} // end class
