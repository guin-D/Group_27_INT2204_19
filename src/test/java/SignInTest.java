import LibraryManagement.Database.UserDatabase;
import LibraryManagement.commandline.TestMan;
import LibraryManagement.commandline.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SignInTest {

  private UserDatabase userDatabase;

  @BeforeEach
  void setUp() {
    userDatabase = UserDatabase.getInstance();

    // tao nguoi dung Test chuc nang nay
    User testUser = new TestMan("Test User", "0123987", "password", "Normal");
    userDatabase.insert(testUser);
  }

  @Test
  void SignInTest() {
    String phoneNumber = "00";
    String password = "adminpassword";

    // kiem tra tai khoan co ton tai trong database khong
    boolean exactAccount = userDatabase.selectAll().stream()
        .anyMatch(user -> user.getPhoneNumber().equals(phoneNumber) &&
            user.getPassword().equals(password));

    assertTrue(exactAccount);
  }
}
