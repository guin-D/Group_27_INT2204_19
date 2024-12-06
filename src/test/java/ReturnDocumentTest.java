import LibraryManagement.DAO.DocumentDatabase;
import LibraryManagement.commandline.Borrowing;
import LibraryManagement.commandline.ReturnDocument;
import LibraryManagement.commandline.TestMan;
import LibraryManagement.commandline.User;
import LibraryManagement.DAO.BorrowingDatabase;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ReturnDocumentTest {

  @Test
  void ReturnDocumentTest() {
    String borrowedDoc = "sachmoi";
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
    assertNotNull(borrowingDoc);
    int beforeBorrowing = borrowingDatabaseBefore.selectAll().size();
    int theDocBrwcpBefore = documentDatabase.getDocumentByTitle(borrowedDoc).getBrwcopiers();

    //thuc hien hanh dong tra sach
    new ReturnDocument().oper(user);

    BorrowingDatabase borrowingDatabaseAfter = BorrowingDatabase.getInstance();
    int afterBorrowing = borrowingDatabaseAfter.selectAll().size();
    int theDocBrwcpAfter = documentDatabase.getDocumentByTitle(borrowedDoc).getBrwcopiers();

    assertEquals(beforeBorrowing - 1, afterBorrowing);
    assertEquals(theDocBrwcpBefore + 1, theDocBrwcpAfter);
  }
}
