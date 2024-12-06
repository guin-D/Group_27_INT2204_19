import LibraryManagement.DAO.DocumentDatabase;
import LibraryManagement.commandline.Borrowing;
import LibraryManagement.commandline.ReturnDocument;
import LibraryManagement.commandline.TestMan;
import LibraryManagement.commandline.User;
import LibraryManagement.DAO.BorrowingDatabase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ReturnDocumentTest {

  @Test
  void ReturnDocumentTest() {
    // chuyen huong dau ra
    PrintStream originalOut = System.out;
    ByteArrayOutputStream tempStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(tempStream));
    try {
    String borrowedDoc = "conyeume";
    System.setIn(new ByteArrayInputStream(borrowedDoc.getBytes()));

    User user = new TestMan("John Doe", "123456789", "password", "normaluser");
    DocumentDatabase documentDatabase = DocumentDatabase.getInstance();
    BorrowingDatabase borrowingDatabaseBefore = BorrowingDatabase.getInstance();

    Borrowing borrowingDoc = null;

//  duyet qua danh sach borrowingdatabase va tim tai lieu da muon
    for (Borrowing borrowing : BorrowingDatabase.getInstance().selectAll()) {
      if (borrowing.getDocumentTitle().equals(borrowedDoc)) {
        borrowingDoc = borrowing;
        break;
      }
    }
    assertNotNull(borrowingDoc, "Doc did not borrowed");
    int beforeBorrowing = borrowingDatabaseBefore.selectAll().size();
    int theDocBrwcpBefore = documentDatabase.getDocumentByTitle(borrowedDoc).getBrwcopiers();

    //thuc hien hanh dong tra sach
    new ReturnDocument().oper(user);

    BorrowingDatabase borrowingDatabaseAfter = BorrowingDatabase.getInstance();
    int afterBorrowing = borrowingDatabaseAfter.selectAll().size();
    int theDocBrwcpAfter = documentDatabase.getDocumentByTitle(borrowedDoc).getBrwcopiers();

    assertEquals(beforeBorrowing - 1, afterBorrowing, "Borrow not decrease");
    assertEquals(theDocBrwcpBefore + 1, theDocBrwcpAfter, "Brwcopiers not increase");
    } finally {
      System.setOut(originalOut);
    }
  }
}
