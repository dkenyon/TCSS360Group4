import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class JobTest {

	private Job newJob;
	private String jobName = "Test Job";
	private int jobMonth = 2;
	private int jobDate = 8;
	private String jobLocation = "Woodland Park Zoo";
	private int maxLight = 2;
	private int maxMed = 3;
	private int maxHeavy = 4;
	private String theInfo = "This is where we post info about the job!";
	
	@Before
	public void setUp(){
		newJob = new Job(jobName,jobMonth,jobDate,jobLocation,maxLight,maxMed,maxHeavy,theInfo);
	}
	
	@Test
	public void testSignUpForLight() {
		assertTrue(newJob.signUpForLight(1));
		assertFalse(newJob.signUpForLight(1));
		assertTrue(newJob.signUpForLight(2));
		assertFalse(newJob.signUpForLight(3));
	}

	@Test
	public void testSignUpForMedium() {
		assertTrue(newJob.signUpForMedium(1));
		assertFalse(newJob.signUpForMedium(1));
		assertTrue(newJob.signUpForMedium(2));
		assertTrue(newJob.signUpForMedium(4));
		assertFalse(newJob.signUpForMedium(3));
	}

	@Test
	public void testSignUpForHeavy() {
		assertTrue(newJob.signUpForHeavy(1));
		assertFalse(newJob.signUpForHeavy(1));
		assertTrue(newJob.signUpForHeavy(2));
		assertTrue(newJob.signUpForHeavy(3));
		assertTrue(newJob.signUpForHeavy(4));
		assertFalse(newJob.signUpForHeavy(5));
	}

	@Test
	public void testContainsVolunteer() {
		assertFalse(newJob.containsVolunteer(1));
		newJob.signUpForLight(3);
		assertTrue(newJob.containsVolunteer(3));
	}

	@Test
	public void testGetVolunteers() {
		List<Integer> test = newJob.getVolunteers();
		assertEquals(0,test.size());
		newJob.signUpForLight(1);
		newJob.signUpForMedium(2);
		newJob.signUpForHeavy(4);
		test = newJob.getVolunteers();
		assertEquals(3,test.size());
	}

}
