
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Volunteer class
 * 
 * @author Brian Crabtree
 */
public class Volunteer extends AbstractUser {
    
    private ArrayList<Job> myJobs;  // jobs this volunteer is signed up for
    private static JobHandler myJobHandler; // the job handler (one for all)

    /**
     * initializes instance fields
     * @param theFirstName
     * @param theLastName
     * @param theEmail
     * @param thePhoneNumber
     * @param theAddress 
     */
    public Volunteer(String theFirstName, String theLastName, String theEmail, 
            String thePhoneNumber, String theAddress) {
        super(theFirstName, theLastName, theEmail, thePhoneNumber, theAddress);
    }
    
    /**
     * lists jobs available
     * @return list of jobs
     */
    public List<Job> viewJobsCanSignUpFor() {
        return myJobHandler.getJobForVol(getUserID());
    }
    
    /**
     * signs this volunteer up for a job
     * @param JobID
     * @param WorkCat 
     * @return boolean success or failure
     */
    public boolean signUpForJob(Integer JobID, Integer WorkCat) {
        
        // find out what day / month it is
        Calendar cal = Calendar.getInstance();
        int myCurDate = cal.get(Calendar.DAY_OF_MONTH);
        int myCurMonth = cal.get(Calendar.MONTH);
        
        Job job = myJobHandler.getJobByID(JobID);
        
        // check for other jobs on this date
        if (myJobs.stream().noneMatch((aJob)->(aJob.getDate()==job.getDate()))
                && myCurDate >= job.getDate() && myCurMonth >= job.getMonth()) {

            switch(WorkCat) {   // sign up for the correct workload

                case 0: if (job.signUpForLight(getUserID())) myJobs.add(job);
                    break;

                case 1: if (job.signUpForMedium(getUserID())) myJobs.add(job);
                    break;

                case 2: if (job.signUpForHeavy(getUserID())) myJobs.add(job);
                    break;

                default: return false; // invalid workload category
            }
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * lists jobs this volunteer is signed up for
     * @return list of jobs
     */
    public List<Job> viewJobsSignedUpFor() {
        return myJobs;
    }
} // end class
