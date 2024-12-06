import LibraryManagement.commandline.NormalUser;
import LibraryManagement.commandline.Admin;
import LibraryManagement.commandline.User;
import LibraryManagement.DAO.UserDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SignUpTest {

  private UserDatabase userDatabase;

  @BeforeEach
  void setUp() {
    userDatabase = UserDatabase.getInstance();
  }

  // kiem tra sdt trung chua
  private boolean isPhoneNumberTaken(String phoneNumber) {
    return userDatabase.selectAll().stream()
        .anyMatch(user -> user.getPhoneNumber().equals(phoneNumber));
  }

  // kiem tra thong tin nguoi dung moi tao
  private void validateUserCreation(User user) {
    // lay nguoi dung tu database
    User createdUser = userDatabase.selectAll().stream()
        .filter(dbUser -> dbUser.getPhoneNumber().equals(user.getPhoneNumber()))
        .findFirst()
        .orElse(null);

    assertNotNull(createdUser, "User should be created in the database.");

    // kiem tra cac thuoc tinh nguoi dung
    assertEquals(user.getName(), createdUser.getName(), "Name should match.");
    assertEquals(user.getPhoneNumber(), createdUser.getPhoneNumber(), "Phone number should match.");
    assertEquals(user.getAccessLevel(), createdUser.getAccessLevel(), "Access level should match.");
  }

  // phuong thuc dang ky tai khoan
  private void signUpTest(String name, String phoneNumber, String password, String accessLevel) {
    // kiem tra sdt trung
    assertFalse(isPhoneNumberTaken(phoneNumber), "Phone number should not be taken.");

    // tao nguoi dung moi
    User user = accessLevel.equals("Admin") ? new Admin(name, phoneNumber, password, accessLevel)
        : new NormalUser(name, phoneNumber, password, accessLevel);

    // them nguoi dung vao database
    int result = userDatabase.insert(user);
    assertTrue(result > 0, "Failed to insert user into the database.");

    // kiem tra thong tin nguoi dung
    validateUserCreation(user);
  }

  @Test
  void SignUpNormalAccTest() {
    // test tk normal
    signUpTest("Johnny sinshit", "00", "password123", "Normal");
  }

  @Test
  void SignUpAdminAccTest() {
    // test tk admin
    signUpTest("Admin User", "00", "adminpassword", "Admin");
  }
}
