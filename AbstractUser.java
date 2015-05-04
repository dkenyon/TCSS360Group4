/**
 * Represents default behavior for User subclasses.
 * 
 * @author Dennis Kenyon
 * @version 01May2015
 */
public abstract class AbstractUser {   
    
	/** A global counter set up so that each user has a unique integer ID given to him/her. */
	private static int userIDCounter = 1;
	
	/** The User's first name. */
    private String myFirstName;
    
    /** The User's last name. */
    private String myLastName;
    
    /** The User's email address. */
    private String myEmail;
    
    /** The User's phone number. */
    private String myPhoneNumber;
    
    /** The User's home address. */
    private String myAddress;
    
    /** The User's uniquely assigned ID. */
    private int myUserID;
    
    /**
     * Initialize the instance fields.
     * @param theFirstName the first name of the user
     * @param theLastName the last name of the user
     * @param theEmail the user's email address
     * @param thePhoneNumber the user's phone number
     * @param theAddress the user's home address
     * @param theUserID the user's User ID
     * 
     */
    protected AbstractUser(final String theFirstName, final String theLastName, final String theEmail, final String thePhoneNumber, 
    		final String theAddress) {
    	myFirstName = theFirstName;
    	myLastName = theLastName;
    	myEmail = theEmail;
    	myPhoneNumber = thePhoneNumber;
    	myAddress = theAddress;
    	myUserID = userIDCounter;
    	userIDCounter++; //increment global ID counter so that no users created in the future 
    					 //will have the same ID
    }    

    /**
     * Returns the user's first name.
     * @return the first name
     */
    public String getFirstName() {
    	return myFirstName;
    }
    
    /**
     * Returns the user's last name.
     * @return the last name
     */
    public String getLastName() {
    	return myLastName;
    }
    
    /**
     * Returns the user's email.
     * @return the email
     */
    public String getEmail() {
    	return myEmail;
    }
    
    /**
     * Returns the user's phone number.
     * @return the phone number
     */
    public String getPhoneNumber() {
    	return myPhoneNumber;
    }
    
    /**
     * Returns the user's home address.
     * @return the address
     */
    public String getAddress() {
    	return myAddress;    	
    }
    
    /**
     * Returns the user's assigned UserID
     * @return the UserID
     */
    public int getUserID() {
    	return myUserID;
    }
    
    /**
     * Sets the User's first name to a new name.
     * @param theNewName the new name for the user
     */
    public void setFirstName(final String theNewName) {
    	myFirstName = theNewName;
    }
    
    /**
     * Sets the User's last name to a new name.
     * @param theNewName the new name for the user
     */
    public void setLastName(final String theNewName) {
    	myLastName = theNewName;
    }
    
    /**
     * Sets the User's email to a new email.
     * @param theNewEmail the new email for the user
     */
    public void setEmail(final String theNewEmail) {
    	myEmail = theNewEmail;
    }
    
    /**
     * Sets the User's phone number to a new number.
     * @param theNewNumber the new phone number for the user
     */
    public void setPhoneNumber(final String theNewNumber) {
    	myPhoneNumber = theNewNumber;
    }
    
    /**
     * Sets the User's address to a new address.
     * @param theNewAddress the new address for the user
     */
    public void setAddress(final String theNewAddress) {
    	myAddress = theNewAddress;
    }
    
    /**
     * Prints out a String representation of the User.
     * For the sake of keeping things succinct, this only prints out the User's name and UserID.
     * @return the user's name and UserID
     */
    @Override
    public String toString() {
    	//this adds zeroes to the left of the user's UserID only to look nice; a user's UseID might be
    	//55, but the toString() will show it as 00055.
    	final String paddedUserID = String.format("%05d", myUserID); 
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: " + this.getFirstName() + " " + this.getLastName() +  "    ");
        builder.append("UserID: " + paddedUserID);
        return builder.toString();
    }

} //end class
