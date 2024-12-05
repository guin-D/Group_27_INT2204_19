package LibraryManagement.commandline;

public class TestMan extends User {

  public TestMan(String name, String phoneNumber, String password, String accessLevel) {
    super(name, phoneNumber, password, accessLevel);
  }

  @Override
  public void menu(User user) {
    // Cài đặt các hành động của menu cho RegularUser
    System.out.println(user.getName() + "'s Menu:");
    // Code menu cho người dùng thông thường
  }
}
