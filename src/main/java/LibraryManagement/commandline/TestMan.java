package LibraryManagement.commandline;

/**
 * The TestMan class extends the User class and overrides the menu method.
 */
public class TestMan extends User {

  /**
   * Constructor for initializing a TestMan object with user details.
   *
   * @param name        The name of the user.
   * @param phoneNumber The user's phone number.
   * @param password    The user's password.
   * @param accessLevel The user's access level.
   */
  public TestMan(String name, String phoneNumber, String password, String accessLevel) {
    super(name, phoneNumber, password, accessLevel);
  }

  /**
   * Displays the menu for the user. This method is overridden from the User class.
   *
   * @param user The user for whom the menu is displayed.
   */
  @Override
  public void menu(User user) {
    System.out.println(user.getName() + "'s Menu:");
  }
}
