import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
/**
 * Tests of the Administrator class.
 * 
 * @author Dennis Kenyon
 * @version 03May2015
 */
public class AdministratorTest {
	
    /** An Administrator to use in the tests. */
    private Administrator myAdmin;
    
    /**Static reference to the JobHandler class*/
	private static JobHandler myJobHandler = new JobHandler();
    
    /**
     * A method to initialize the test fixture before each test.
     */
    @Before
    public void setUp() {
 
    	myAdmin = new Administrator("John", "Doe", "johndoe@gmail.com", "8675309", "123 Fake St", myJobHandler);
    }
    

    /**
     * Test method for {@link Administrator#searchVolunteers(Administrator)}.
     */
    @Test
    public void testSearchVolunteers() {
    	ArrayList<Volunteer> list = new ArrayList<Volunteer>();
    	list.add(new Volunteer("John", "Smith", "johnsmith@gmail.com", "2534445555", "123 Fake Street", myJobHandler));
    	list.add(new Volunteer("Jacob", "Smith", "jacobsmith@gmail.com", "3604448796", "456 Fake Street", myJobHandler));
    	list.add(new Volunteer("Mary", "Shelly", "maryshelly@gmail.com", "2538791150", "789 Fake Street", myJobHandler));
    	myJobHandler.populateVolunteers(list);
    	
    	ArrayList<Volunteer> list2 = (ArrayList<Volunteer>) myJobHandler.getVolunteerByLastName("Smith");
    	ArrayList<Volunteer> list3 = (ArrayList<Volunteer>) myAdmin.searchVolunteers("Smith");
    	System.out.println(list);
    	System.out.println(list2);
        assertEquals("testSearchVolunteers failed!", list2, list3);
    }


}
