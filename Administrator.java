import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Administrator subclasses.
 * 
 * @author Dennis Kenyon
 * @version 03May2015
 */

public class Administrator extends AbstractUser {

	/** List of 'volunteers' (placeholder admins at the moment) to check cases with. */
	private ArrayList<Administrator> myTestList;
	
    /**
     * Initialize the instance fields.
     * @param theFirstName the first name of the admin
     * @param theLastName the last name of the admin
     * @param theEmail the admin's email address
     * @param thePhoneNumber the admin's phone number
     * @param theAddress the admin's home address
     * @param theUserID the admin's User ID
     * 
     */
    public Administrator(final String theFirstName, final String theLastName, final String theEmail, final String thePhoneNumber, 
    		final String theAddress) {
        super(theFirstName, theLastName, theEmail, thePhoneNumber, theAddress);
    }
    
    /**
     * Searches volunteers by last name and returns a list of those that are matches.
     * @param theLastName the last name of the user to search for
     * @return a list of volunteers with that last name
     */
    // ****USING ADMINISTRATORS AS A PLACEHOLDER UNTIL OUR VOLUNTEER CLASS IS FINISHED****
    public List<Administrator> searchVolunteers(final String theLastName) {
    	List<Administrator> tempList = new ArrayList<Administrator>();
    	//List<Administrator> volunteerList = myJobHandler.getVolunteerList();
    	for (Administrator admin : myTestList) {
    		if (admin.getLastName().equals(theLastName)) {
    			tempList.add(admin);
    		}
    	}
    	return tempList;
    }    
    
    //STRICTLY FOR TESTING PURPOSES; DELETED LATER 
    public void addToTestList(Administrator theAdmin) {
    	myTestList.add(theAdmin);
    }
}
