package LibraryManagement.commandline;

public abstract class User {
    protected String name;
    protected String phonenumber;
    protected String password;
    protected String accessLevel;
    protected IOOperation[] operations;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String phonenumber, String password, String accessLevel) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    public String getName() {
        return name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getPassword() {
        return password;
    }

    abstract public void menu(Database database, User user);

    abstract public String toString();
}
