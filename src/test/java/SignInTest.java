import LibraryManagement.DAO.UserDatabase;
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
  }

  @Test
  void SignInTest() {
    String phoneNumber = "5432";
    String password = "adminpassword";

    // kiem tra tai khoan co ton tai trong database khong
    boolean exactAccount = userDatabase.selectAll().stream()
        .anyMatch(user -> user.getPhoneNumber().equals(phoneNumber) &&
            user.getPassword().equals(password));

    assertTrue(exactAccount, "Incorrect Phone Number or Password ");
  }
}
