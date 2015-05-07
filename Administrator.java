import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Administrator subclass.
 * 
 * @author Dennis Kenyon
 * @version 06May2015
 */

public class Administrator extends AbstractUser {

	/** List of volunteers to check cases with. */
	private ArrayList<Volunteer> myTestList;
	
	/**Static reference to the JobHandler class*/
	private static JobHandler myJobHandler;
	
    /**
     * Initialize the instance fields.
     * @param theFirstName the first name of the admin
     * @param theLastName the last name of the admin
     * @param theEmail the admin's email address
     * @param thePhoneNumber the admin's phone number
     * @param theAddress the admin's home address
     * 
     */
    public Administrator(final String theFirstName, final String theLastName, final String theEmail, final String thePhoneNumber, 
    		final String theAddress, final JobHandler theJobHandler) {
        super(theFirstName, theLastName, theEmail, thePhoneNumber, theAddress);
        myJobHandler = theJobHandler;
    }
    
    /**
     * Searches volunteers by last name and returns a list of those that are matches.
     * @param theLastName the last name of the user to search for
     * @return a list of volunteers with that last name
     */
    public List<Volunteer> searchVolunteers(final String theLastName) {
    	return myJobHandler.getVolunteerByLastName(theLastName);
    }
}
