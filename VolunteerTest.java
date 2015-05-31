/**
 * tests Volunteer class
 * @author Brian Crabtree
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class VolunteerTest {
    
    private Volunteer myVolunteer1, myVolunteer2;
    private Job myJob1, myJob2, myJob3, myJob4;
    private static JobHandler myJobHandler = new JobHandler();
    private ArrayList<Job> myJobList = new ArrayList<Job>();
    
    @Before
    public void setUp() {
    	myVolunteer1 = new Volunteer("Abe", "Lincoln", "anonymous@ftp.com", 
                "123-456-7890", "123 Broad St. Nowhere, OK 73038", myJobHandler);
        myVolunteer2 = new Volunteer("George", "Washington", 
                "a@b.com", "456-789-0123", "456 Broad St. Nowhere, OK 73038", myJobHandler);
        
        Calendar cal = Calendar.getInstance();
        int curDate = cal.get(Calendar.DAY_OF_MONTH);
        int curMonth = cal.get(Calendar.MONTH);
        
        myJob1 = new Job("aJobName1", curMonth + 1, curDate + 1, "park 1", 3, 3, 3, 
                "This job involves repetitive tasks.");
        myJob2 = new Job("aJobName2", curMonth - 1, curDate + 1, "park 2", 1, 1, 1, 
                "This job involves redundant tasks.");
        myJob3 = new Job("aJobName3", curMonth, curDate - 1, "park 3", 1, 1, 1, 
                "This job involves repulsive tasks.");
        myJob4 = new Job("aJobName4", curMonth + 1, curDate, "Park 4", 1, 1, 1, 
                "This job involves reordered tasks.");
        
        myJobList.add(myJob1);
        myJobList.add(myJob2);
        myJobList.add(myJob3);
        myJobList.add(myJob4);
        
        myJobHandler.populateJobs(myJobList);


    }
    
    /**
     * Test method for {@link Volunteer#viewJobsCanSignUpFor()}.
     */
    @Test
    public void testViewJobsCanSignUpFor() {
        myVolunteer1.viewJobsCanSignUpFor();
        assertTrue("viewJobsCanSignUpFor() produced an unexpected result!",
                myVolunteer1.viewJobsCanSignUpFor().contains(myJob1));
    }
    		
    /**
     * Test method for {@link Volunteer#signUpForJob()}.
     */
    @Test
    public void testSignUpForJob() {
        assertTrue("unable to signUpForJob()",
                myVolunteer1.signUpForJob(myJob1, 1));
    }
    
    @Test
    public void testSignUpForJobinPast(){
        assertFalse("was able to sign up for job from last month",
                myVolunteer1.signUpForJob(myJob2, 1));
    }
    
    @Test
    public void testSignUpForJobYesterday(){
    	assertFalse("was able to sign up for job that took place yesterday",
    			myVolunteer1.signUpForJob(myJob3, 1));
    }
    
    @Test
    public void testSignUpForJobToday(){
    	assertFalse("was able to sign up for job that took place today",
    			myVolunteer1.signUpForJob(myJob4, 1));    	
    }
    
    /**
     * Test method for {@link Volunteer#viewJobsSignedUpFor()
     */
    @Test
    public void testViewJobsSignedUpFor() {
        myVolunteer1.signUpForJob(myJob1, 1);
        assertTrue("viewJobsSignedUpFor() returned unexpected result",
                myVolunteer1.getJobs().contains(myJob1));
        assertFalse("viewJobsSignedUpFor() returned job not signed up for",
                myVolunteer1.getJobs().contains(myJob2));
    }
    		
    
} // end test class
