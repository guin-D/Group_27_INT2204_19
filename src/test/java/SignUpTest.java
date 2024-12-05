import LibraryManagement.commandline.NormalUser;
import LibraryManagement.commandline.Admin;
import LibraryManagement.commandline.User;
import LibraryManagement.Database.UserDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SignUpTest {

  private UserDatabase userDatabase;

  @BeforeEach
  void setUp() {
    userDatabase = UserDatabase.getInstance();
  }

  //kiem tra so dien thoai da bi trung chua
  private boolean isPhoneNumberTaken(String phoneNumber) {
    return userDatabase.selectAll().stream()
        .anyMatch(user -> user.getPhoneNumber().equals(phoneNumber));
  }

  //kiem tra cac thuoc tinh user moi tao da dung chua
  private void validateUserCreation(User user) {
    boolean userCreated = userDatabase.selectAll().stream()
        .anyMatch(dbUser -> dbUser.getPhoneNumber().equals(user.getPhoneNumber()));
    assertTrue(userCreated);

    assertEquals(user.getName(), userDatabase.selectAll().stream()
        .filter(dbUser -> dbUser.getPhoneNumber().equals(user.getPhoneNumber()))
        .findFirst().get().getName());

    assertEquals(user.getPhoneNumber(), userDatabase.selectAll().stream()
        .filter(dbUser -> dbUser.getPhoneNumber().equals(user.getPhoneNumber()))
        .findFirst().get().getPhoneNumber());

    assertEquals(user.getAccessLevel(), userDatabase.selectAll().stream()
        .filter(dbUser -> dbUser.getPhoneNumber().equals(user.getPhoneNumber()))
        .findFirst().get().getAccessLevel());
  }

  @Test
  void SignUpNormalAccTest() {
    //test tai khoan normal user
    String name = "Johnny sinshit";
    String phoneNumber = "1234567";
    String password = "password123";
    String accessLevel = "Normal";

    //goi ham kiem tra so dien thoai da ton tai chua
    assertFalse(isPhoneNumberTaken(phoneNumber));

    // tao nguoi dung moi
    User normalUser = new NormalUser(name, phoneNumber, password, accessLevel);

    // them nguoi dung moi vao database
    int result = userDatabase.insert(normalUser);

    // kiem tra thong tin nguoi dung
    validateUserCreation(normalUser);
  }

  @Test
  void SignUpAdminAccTest() {
    // test tai khoan nguoi dung Admin
    String name = "Admin User";
    String phoneNumber = "00";
    String password = "adminpassword";
    String accessLevel = "Admin";

    // kiem tra neu so dien thoai da ton tai
    assertFalse(isPhoneNumberTaken(phoneNumber));

    // tao nguoi dung admin
    User adminUser = new Admin(name, phoneNumber, password, accessLevel);

    // them nguoi dung vao database
    int result = userDatabase.insert(adminUser);

    // kiem tra thong tin nguoi dung xem co dung ko
    validateUserCreation(adminUser);
  }
}
