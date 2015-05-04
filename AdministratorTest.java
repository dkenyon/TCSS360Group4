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
    
    /**
     * A method to initialize the test fixture before each test.
     */
    @Before
    public void setUp() {
    	myAdmin = new Administrator("John", "Doe", "johndoe@gmail.com", "8675309", "123 Fake St");
    }
    

    /**
     * Test method for {@link Administrator#searchVolunteers(Administrator)}.
     */
    @Test
    public void testSearchVolunteers() {
    	Administrator user1 = new Administrator("Bill", "Clinton", "ididnotdoit@yahoo.com", "9876543210", "234 Spring St");
    	Administrator user2 = new Administrator("George", "Bush", "strategery@hotmail.com", "654987321", "345 Oak Dr");
    	Administrator user3 = new Administrator("George", "Bush Sr", "aaa@gmail.com", "354987321", "345 Oak Dr");
    	myAdmin.addToTestList(user1);
    	myAdmin.addToTestList(user2);
    	myAdmin.addToTestList(user3);
    	
    	ArrayList<Administrator> testList = new ArrayList<Administrator>();
    	testList.add(user2);
        assertEquals("testSearchVolunteers failed!", testList,
                     myAdmin.searchVolunteers("Bush"));
    }

    /**
     * Test method for {@link Administrator#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals("toString() produced an unexpected result!",
                     "Name: John Doe    UserID: 00001", myAdmin.toString());
    }

}
