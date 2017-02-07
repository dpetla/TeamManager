package teammanager;

/**
 *
 * @author dpetla and brunopetla
 */
public class Name {

    // data members for first and last name
    private String firstName;
    private String lastName;

    // default constructor
    public Name() { }

    /**
     * constructor creates player's full name
     *
     * @param firstName of the player
     * @param lastName of the player
     */
    public Name(String firstName, String lastName) {
        this.firstName = firstName.toUpperCase();
        this.lastName = lastName.toUpperCase();
    }

    // getters and setters for first and last names of players
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.toUpperCase();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.toUpperCase();
    }

    /**
     * accept a player full name and will split first and last name and then
     * pass them as argument to set names
     *
     * @param fullName
     */
    public void setName(String fullName) {
        String firstN = fullName.substring(0, fullName.indexOf(" "));
        String lastN = fullName.substring(fullName.lastIndexOf(" ") + 1);
        firstName = firstN;
        lastName = lastN;
        setFirstName(firstName);
        setLastName(lastName);
    }

    /**
     * Output the first and last name.
     * @return 
     */
    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }
}
