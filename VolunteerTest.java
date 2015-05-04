/**
 * tests Volunteer class
 * @author Brian Crabtree
 */

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

public class VolunteerTest {
    
    private Volunteer myVolunteer1, myVolunteer2;
    private Job myJob1, myJob2, myJob3, myJob4, myJob5, myJob6;
    private static JobHandler myJobHandler;
    
    @Before
    public void setUp() {
    	myVolunteer1 = new Volunteer("Abe", "Lincoln", "anonymous@ftp.com", 
                "123-456-7890", "123 Broad St. Nowhere, OK 73038");
        myVolunteer2 = new Volunteer("George", "Washington", 
                "a@b.com", "456-789-0123", "456 Broad St. Nowhere, OK 73038");
        
        Calendar cal = Calendar.getInstance();
        int curDate = cal.get(Calendar.DAY_OF_MONTH);
        int curMonth = cal.get(Calendar.MONTH);
        
        myJob1 = new Job("aJobName1", curMonth + 1, curDate + 1, 1, 3, 3, 3, 
                "This job involves repetitive tasks.");
        myJob2 = new Job("aJobName2", curMonth - 1, curDate + 1, 2, 1, 1, 1, 
                "This job involves redundant tasks.");
        myJob3 = new Job("aJobName3", curMonth, curDate - 1, 3, 1, 1, 1, 
                "This job involves repulsive tasks.");
        myJob4 = new Job("aJobName4", curMonth + 1, curDate + 1, 4, 1, 1, 1, 
                "This job involves reordered tasks.");
        myJob5 = new Job("aJobName5", curMonth + 1, curDate + 2, 4, 1, 1, 1, 
                "This job involves reconditioned tasks.");
        myJob6 = new Job("aJobName6", curMonth + 2, curDate + 3, 4, 1, 1, 1, 
                "This job involves recycled tasks.");
        
        myJobHandler.addJob(myJob1);
        myJobHandler.addJob(myJob2);
        myJobHandler.addJob(myJob3);
        myJobHandler.addJob(myJob4);
        myJobHandler.addJob(myJob5);
        myJobHandler.addJob(myJob6);

    }
    
    /**
     * Test method for {@link Volunteer#toString()}
     */
    @Test
    public void testToString() {
        assertEquals("toString() produced an unexpected result!",
                     "Name: Abe Lincoln    UserID: 00001", myVolunteer1.toString());
    }
    
    /**
     * Test method for {@link getUserID()}
     */
    @Test
    public void testGetUserID() {
        
        assertEquals("getUserID() returned unexpected value",
                1, myVolunteer1.getUserID());
        assertEquals("getUserID() returned unexpected value",
                2, myVolunteer2.getUserID());
        assertTrue("userID failed to increment", 
                myVolunteer1.getUserID() < myVolunteer2.getUserID());
    }
    
    /**
     * Test method for {@link Volunteer#getFirstName()}.
     */
    @Test
    public void testGetFirstName() {
        assertEquals("getFirstName() produced an unexpected result!",
                     "Abe", myVolunteer1.getFirstName());
    }
    
    /**
     * Test method for {@link Volunteer#getLastName()}.
     */
    @Test
    public void testGetLastName() {
        assertEquals("getLastName() produced an unexpected result!",
                     "Lincoln", myVolunteer1.getLastName());
    }
    
    /**
     * Test method for {@link Volunteer#getEmail()}.
     */
    @Test
    public void testGetEmail() {
        assertEquals("getEmail() produced an unexpected result!",
                     "anonymous@ftp.com", myVolunteer1.getEmail());
    }
    
    /**
     * Test method for {@link Volunteer#getPhoneNumber()}.
     */
    @Test
    public void testGetPhoneNumber() {
        assertEquals("You are not cool enough to getPhoneNumber()",
                     "123-456-7890", myVolunteer1.getPhoneNumber());
    }
    
    /**
     * Test method for {@link Volunteer#getAddress()}.
     */
    @Test
    public void testGetAddress() {
        assertEquals("getAddress() produced an unexpected result!",
                     "123 Broad St. Nowhere, OK 73038", myVolunteer1.getAddress());
    }
    
    /**
     * Test method for {@link Volunteer#setFirstName()}.
     */
    @Test
    public void testSetFirstName() {
        myVolunteer2.setFirstName("Billy");
        assertEquals("SetFirstName() produced an unexpected result!",
                     "Billy", myVolunteer2.getFirstName());
    }
    
    /**
     * Test method for {@link Volunteer#setLastName()}.
     */
    @Test
    public void testSetLastName() {
        myVolunteer2.setLastName("Joel");
        assertEquals("setLastName() produced an unexpected result!",
                     "Joel", myVolunteer2.getLastName());
    }
    
    /**
     * Test method for {@link Volunteer#setEmail()}.
     */
    @Test
    public void test() {
        myVolunteer2.setEmail("yowzer@yawn.com");
        assertEquals("setEmail() produced an unexpected result!",
                     "yowzer@yawn.com", myVolunteer2.getEmail());
    }
    
    /**
     * Test method for {@link Volunteer#setPhoneNumber()}.
     */
    @Test
    public void testSetPhoneNumber() {
        myVolunteer2.setPhoneNumber("908-765-4321");
        assertEquals("setPhoneNumber() produced an unexpected result!",
                     "908-765-4321", myVolunteer2.getPhoneNumber());
    }
    
    /**
     * Test method for {@link Volunteer#setAddress()}.
     */
    @Test
    public void testSetAddress() {
        myVolunteer2.setAddress("5 Six St. Seven, AK 54321");
        assertEquals("setAddress() produced an unexpected result!",
                     "5 Six St. Seven, AK 54321", myVolunteer2.getAddress());
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
                myVolunteer1.signUpForJob(myJob1.getJobID(), 1));
        assertTrue("unable to sign up 2 people for the same job",
                myVolunteer2.signUpForJob(myJob1.getJobID(), 2));
        assertFalse("was able to sign up for job from last month",
                myVolunteer1.signUpForJob(myJob2.getJobID(), 1));
        assertFalse("was able to sign up for job from last day",
                myVolunteer1.signUpForJob(myJob3.getJobID(), 1));
        assertFalse("was able to sign up for second job on same day",
                myVolunteer1.signUpForJob(myJob4.getJobID(), 1));
        assertTrue("unable to sign up for job",
                myVolunteer1.signUpForJob(myJob5.getJobID(), 1));
        assertFalse("exceeded medium volunteer limit, still able to volunteer",
                myVolunteer2.signUpForJob(myJob5.getJobID(), 1));
        
    }
    
    /**
     * Test method for {@link Volunteer#viewJobsSignedUpFor()
     */
    @Test
    public void testViewJobsSignedUpFor() {
        myVolunteer1.signUpForJob(myJob6.getJobID(), 1);
        assertTrue("viewJobsSignedUpFor() returned unexpected result",
                myVolunteer1.viewJobsSignedUpFor().contains(myJob1));
        assertFalse("viewJobsSignedUpFor() returned job not signed up for",
                myVolunteer1.viewJobsSignedUpFor().contains(myJob2));
    }
    		
    
} // end test class