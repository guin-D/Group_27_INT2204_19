package LibraryManagement.commandline;

/**
 * The abstract User class serves as the base class for different types of users in the library management system.
 * It contains common properties and methods shared by all users, such as name, phone number, password, and access level.
 */
public abstract class User {
    protected String name;
    protected String phoneNumber;
    protected String password;
    protected String accessLevel;
    protected IOOperation[] operations; // Operations available to the user (menu actions)

    /**
     * Default constructor for User.
     */
    public User() {
    }

    /**
     * Constructor to initialize the User with just a name.
     *
     * @param name The name of the user.
     */
    public User(String name) {
        this.name = name;
    }

    /**
     * Constructor to initialize the User with name, phone number, password, and access level.
     *
     * @param name        The name of the user.
     * @param phoneNumber The phone number of the user.
     * @param password    The password for the user.
     * @param accessLevel The access level of the user (e.g., admin, normal user).
     */
    public User(String name, String phoneNumber, String password, String accessLevel) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    abstract public void menu(User user);
}
