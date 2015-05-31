import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class JobTest {

	private Job newJob1, newJob2, newJob3, newJob4, newJob5;
        private Volunteer volunteer1, volunteer2, volunteer3, 
                volunteer4, volunteer5;
        private JobHandler myJobHandler = new JobHandler();
	
	@Before
	public void setUp(){
                volunteer1 = new Volunteer("Abe1", "Lincoln1", "anonymous1@ftp.com", 
                "123-456-7890", "123 Broad St. Nowhere, OK 73038", myJobHandler);
                volunteer2 = new Volunteer("Abe2", "Lincoln2", "anonymous2@ftp.com", 
                "123-456-7890", "123 Broad St. Nowhere, OK 73038", myJobHandler);
                volunteer3 = new Volunteer("Abe3", "Lincoln3", "anonymous3@ftp.com", 
                "123-456-7890", "123 Broad St. Nowhere, OK 73038", myJobHandler);
                volunteer4 = new Volunteer("Abe4", "Lincoln4", "anonymous4@ftp.com", 
                "123-456-7890", "123 Broad St. Nowhere, OK 73038", myJobHandler);
                volunteer5 = new Volunteer("Abe5", "Lincoln5", "anonymous5@ftp.com", 
                "123-456-7890", "123 Broad St. Nowhere, OK 73038", myJobHandler);
	}
	
        
        // Light test cases
	@Test
	public void testSignUpForLightWhenEmpty() {
                Job myJob = new Job("Test Job 1", 1, 10, "Woodland Park Zoo", 5, 5, 5, "This job has lots of info.");
		assertTrue(myJob.signUpForLight(volunteer1));
        }
        @Test
        public void testSignUpForLightWithDuplicate() {                
                Job myJob = new Job("Test Job 1", 1, 12, "Woodland Park Zoo", 5, 5, 5, "This job has lots of info.");
                myJob.signUpForLight(volunteer1);
                assertFalse(myJob.signUpForLight(volunteer1));
        }
        @Test
        public void testSignUpForLightSameDay() {
                Job myJob1 = new Job("Test Job 1", 1, 14, "Woodland Park Zoo", 5, 5, 5, "This job has lots of info.");
                Job myJob2 = new Job("Test Job 2", 1, 14, "Point Defiance", 5, 5, 5, "This job has lots of info.");
                myJob1.signUpForMedium(volunteer1);
                assertFalse(myJob2.signUpForLight(volunteer1));
        }
        @Test
        public void testSignUpForLightWhenPartial() {
                Job myJob = new Job("Test Job 1", 1, 16, "Woodland Park Zoo", 5, 5, 5, "This job has lots of info.");
                myJob.signUpForLight(volunteer1);
                assertTrue(myJob.signUpForLight(volunteer2));

        }
        @Test
        public void testSignUpForLightWhenFull() {
                Job myJob = new Job("Test Job 1", 1, 18, "Woodland Park Zoo", 1, 5, 5, "This job has lots of info.");
                myJob.signUpForLight(volunteer1);
		assertFalse(myJob.signUpForLight(volunteer2));
	}

        // Medium test cases
	@Test
	public void testSignUpForMediumWhenEmpty() {
                Job myJob = new Job("Test Job 1", 2, 10, "Woodland Park Zoo", 5, 5, 5, "This job has lots of info.");
		assertTrue(myJob.signUpForMedium(volunteer1));
        }
        @Test
        public void testSignUpForMediumWithDuplicate() {                
                Job myJob = new Job("Test Job 1", 2, 12, "Woodland Park Zoo", 5, 5, 5, "This job has lots of info.");
                myJob.signUpForMedium(volunteer1);
                assertFalse(myJob.signUpForMedium(volunteer1));
        }
        @Test
        public void testSignUpForMediumSameDay() {
                Job myJob1 = new Job("Test Job 1", 2, 14, "Woodland Park Zoo", 5, 5, 5, "This job has lots of info.");
                Job myJob2 = new Job("Test Job 2", 2, 14, "Point Defiance", 5, 5, 5, "This job has lots of info.");
                myJob1.signUpForHeavy(volunteer1);
                assertFalse(myJob2.signUpForMedium(volunteer1));
        }
        @Test
        public void testSignUpForMediumWhenPartial() {
                Job myJob = new Job("Test Job 1", 2, 16, "Woodland Park Zoo", 5, 5, 5, "This job has lots of info.");
                myJob.signUpForMedium(volunteer1);
                assertTrue(myJob.signUpForMedium(volunteer2));

        }
        @Test
        public void testSignUpForMediumWhenFull() {
                Job myJob = new Job("Test Job 1", 2, 18, "Woodland Park Zoo", 1, 1, 1, "This job has lots of info.");
                myJob.signUpForMedium(volunteer1);
		assertFalse(myJob.signUpForMedium(volunteer2));
	}
        
        // Heavy test cases
        @Test
	public void testSignUpForHeavyWhenEmpty() {
                Job myJob = new Job("Test Job 1", 3, 10, "Woodland Park Zoo", 5, 5, 5, "This job has lots of info.");
		assertTrue(myJob.signUpForHeavy(volunteer1));
        }
        @Test
        public void testSignUpForHeavyWithDuplicate() {                
                Job myJob = new Job("Test Job 1", 3, 12, "Woodland Park Zoo", 5, 5, 5, "This job has lots of info.");
                myJob.signUpForHeavy(volunteer1);
                assertFalse(myJob.signUpForHeavy(volunteer1));
        }
        @Test
        public void testSignUpForHeavyWhenPartial() {
                Job myJob = new Job("Test Job 1", 3, 14, "Woodland Park Zoo", 5, 5, 5, "This job has lots of info.");
                myJob.signUpForHeavy(volunteer1);
                assertTrue(myJob.signUpForHeavy(volunteer2));
        }
        @Test
        public void testSignUpForHeavySameDay() {
                Job myJob1 = new Job("Test Job 1", 3, 16, "Woodland Park Zoo", 5, 5, 5, "This job has lots of info.");
                Job myJob2 = new Job("Test Job 2", 3, 16, "Point Defiance", 5, 5, 5, "This job has lots of info.");
                myJob1.signUpForLight(volunteer1);
                assertFalse(myJob2.signUpForHeavy(volunteer1));
        }
        @Test
        public void testSignUpForHeavyWhenFull() {
                Job myJob = new Job("Test Job 1", 3, 18, "Woodland Park Zoo", 1, 1, 1, "This job has lots of info.");
                myJob.signUpForHeavy(volunteer1);
		assertFalse(myJob.signUpForHeavy(volunteer2));
	}
        
        // Other test cases
	@Test
	public void testContainsVolunteer() {
                Job myJob = new Job("Test Job 1", 4, 14, "Woodland Park Zoo", 5, 5, 5, "This job has lots of info.");
                myJob.signUpForHeavy(volunteer2);
		assertFalse(myJob.containsVolunteer(volunteer1));
		myJob.signUpForLight(volunteer1);
		assertTrue(myJob.containsVolunteer(volunteer1));
	}

	@Test
	public void testGetVolunteers() {
                Job myJob = new Job("Test Job 1", 5, 14, "Woodland Park Zoo", 3, 3, 3, "This job has lots of info.");
		List<Volunteer> test = newJob1.getVolunteers();
		assertEquals(0,test.size());
                myJob.signUpForLight(volunteer1);
                myJob.signUpForMedium(volunteer2);
                myJob.signUpForHeavy(volunteer3);
		test = myJob.getVolunteers();
		assertEquals(3,test.size());
	}
        
        @Test
	public void testGetLightVolunteers() {
                Job myJob = new Job("Test Job 1", 6, 14, "Woodland Park Zoo", 3, 3, 3, "This job has lots of info.");
		List<Volunteer> test = newJob1.getVolunteers();
		assertEquals(0,test.size());
                myJob.signUpForLight(volunteer1);
                myJob.signUpForLight(volunteer2);
                myJob.signUpForLight(volunteer3);
		test = myJob.getVolunteers();
		assertEquals(3,test.size());
	}
        
        @Test
	public void testGetMediumVolunteers() {
                Job myJob = new Job("Test Job 1", 7, 14, "Woodland Park Zoo", 3, 3, 3, "This job has lots of info.");
		List<Volunteer> test = newJob1.getVolunteers();
		assertEquals(0,test.size());
                myJob.signUpForMedium(volunteer1);
                myJob.signUpForMedium(volunteer2);
                myJob.signUpForMedium(volunteer3);
		test = myJob.getVolunteers();
		assertEquals(3,test.size());
	}
        
        @Test
	public void testGetHeavyVolunteers() {
                Job myJob = new Job("Test Job 1", 8, 14, "Woodland Park Zoo", 3, 3, 3, "This job has lots of info.");
		List<Volunteer> test = newJob1.getVolunteers();
		assertEquals(0,test.size());
                myJob.signUpForHeavy(volunteer1);
                myJob.signUpForHeavy(volunteer2);
                myJob.signUpForHeavy(volunteer3);
		test = myJob.getVolunteers();
		assertEquals(3,test.size());
	}
}