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
		newJob1 = new Job("Test Job 1", 2, 8, "Woodland Park Zoo", 2, 3, 4, "This job has lots of info.");
		newJob2 = new Job("Test Job 2", 3, 9, "Woodland Park Zoo", 2, 3, 4, "This job has lots of info.");
		newJob3 = new Job("Test Job 2", 4, 10, "Woodland Park Zoo", 2, 3, 4, "This job has lots of info.");
		newJob4 = new Job("Test Job 2", 5, 11, "Woodland Park Zoo", 2, 3, 4, "This job has lots of info.");
		newJob5 = new Job("Test Job 3", 6, 12, "Woodland Park Zoo", 2, 3, 4, "This job has lots of info.");
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
	
	@Test
	public void testSignUpForLightWhenEmpty() {
		assertTrue(newJob1.signUpForLight(volunteer1));
        }
        @Test
        public void testSignUpForLightWithDuplicate() {
                assertFalse(newJob1.signUpForLight(volunteer1));
        }
        @Test
        public void testSignUpForLightWhenPartial() {
                assertTrue(newJob1.signUpForLight(volunteer2));

        }
        @Test
        public void testSignUpForLightWhenFull() {
		assertFalse(newJob1.signUpForLight(volunteer3));
	}

	@Test
	public void testSignUpForMediumWhenEmpty() {
		assertTrue(newJob2.signUpForMedium(volunteer1));
        }
        
        @Test
        public void testSignUpForMediumWithDuplicate() {
		assertFalse(newJob2.signUpForMedium(volunteer1));
        }
        
        @Test
        public void testSignUpForMediumWhenPartial() {
		assertTrue(newJob2.signUpForMedium(volunteer2));
		assertTrue(newJob2.signUpForMedium(volunteer3));
        }
        
        @Test
        public void testSignUpForMediumWhenFull() {
		assertFalse(newJob2.signUpForMedium(volunteer4));
	}

	@Test
	public void testSignUpForHeavyWhenEmpty() {
		assertTrue(newJob3.signUpForHeavy(volunteer1));
        }
        
        @Test
        public void testSignUpForHeavyWithDuplicate() {
		assertFalse(newJob3.signUpForHeavy(volunteer1));
        }
        
        @Test
        public void testSignUpForHeavyWhenPartial() {
		assertTrue(newJob3.signUpForHeavy(volunteer2));
        }
        
        @Test
        public void testSignUpForHeavyWhenFull() {
		assertTrue(newJob3.signUpForHeavy(volunteer3));
		assertTrue(newJob3.signUpForHeavy(volunteer4));
		assertFalse(newJob3.signUpForHeavy(volunteer5));
	}

	@Test
	public void testContainsVolunteer() {
		assertFalse(newJob1.containsVolunteer(volunteer5));
		newJob4.signUpForLight(volunteer5);
		assertTrue(newJob1.containsVolunteer(volunteer5));
	}

	@Test
	public void testGetVolunteers() {
		List<Volunteer> test = newJob1.getVolunteers();
		assertEquals(0,test.size());
		newJob5.signUpForLight(volunteer1);
		newJob5.signUpForMedium(volunteer2);
		newJob5.signUpForHeavy(volunteer3);
		test = newJob1.getVolunteers();
		assertEquals(3,test.size());
	}

}
