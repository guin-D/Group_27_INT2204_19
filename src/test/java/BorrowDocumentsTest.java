import LibraryManagement.commandline.BorrowDocuments;
import LibraryManagement.commandline.TestMan;
import LibraryManagement.commandline.User;
import LibraryManagement.DAO.DocumentDatabase;
import LibraryManagement.DAO.BorrowingDatabase;
import LibraryManagement.commandline.Document;
import LibraryManagement.commandline.Borrowing;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BorrowDocumentsTest {

  @Test
  void BorrowDocumentTest() {
    String simulatedInput = "cocaidaubuoi";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    // chuan bi tai lieu trong database
    DocumentDatabase documentDatabase = DocumentDatabase.getInstance();

    // tao doi tuong user de thuc thi lenh
    User user = new TestMan("John Doe", "123456789", "password", "Normal");

    // lay so ban sao cho muon truoc khi muon sach
    Document documentBeforeBorrow = documentDatabase.selectAll().stream()
        .filter(doc -> doc.getTitle().equals(simulatedInput))
        .findFirst()
        .orElse(null);
    assertNotNull(documentBeforeBorrow);
    int initialBorrowingCopies = documentBeforeBorrow.getBrwcopiers();

    // thuc hien viec lay tai lieu
    BorrowDocuments borrowDocuments = new BorrowDocuments();
    borrowDocuments.oper(user);

    // kiem tra so luong ban sao cho muon sau khi muon sach
    Document documentAfterFirstBorrow = documentDatabase.selectAll().stream()
        .filter(doc -> doc.getTitle().equals(simulatedInput))
        .findFirst()
        .orElse(null);
    assertNotNull(documentAfterFirstBorrow);
    assertEquals(initialBorrowingCopies - 1, documentAfterFirstBorrow.getBrwcopiers());

    // kiem tra da co ban muon trong borrowingdatabase
    ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
    assertTrue(borrowings.stream().anyMatch(
        b -> b.getDocumentTitle().equals(simulatedInput) &&
            b.getUserName().equals("John Doe")));
  }

  @Test
  void BorrowDocumentAgainNFail() {
    // gia lap dau vao tu ban phim
    String simulatedInput = "cocaidaubuoi";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    // tao doi tuong user de thuc thi
    User user = new TestMan("John Doe", "123456789", "password", "Normal");

    // lay lai tai lieu da muon o tren trong database
    DocumentDatabase documentDatabase = DocumentDatabase.getInstance();
    Document document = documentDatabase.selectAll().stream()
        .filter(doc -> doc.getTitle().equals(simulatedInput))
        .findFirst()
        .orElse(null);

    // kiem tra tai lieu ton tai
    assertNotNull(document);

    // dam bao ban muon dau tien da ton tai
    BorrowingDatabase borrowingDatabase = BorrowingDatabase.getInstance();
    Borrowing existingBorrowing = new Borrowing(document, user);
    borrowingDatabase.insert(existingBorrowing);

    // thuc hien viec muon tai lieu lan 2
    BorrowDocuments borrowDocuments = new BorrowDocuments();
    borrowDocuments.oper(user);

    // kiem tra so ban sao khong thay doi
    Document documentAfterSecondBorrow = documentDatabase.selectAll().stream()
        .filter(doc -> doc.getTitle().equals(simulatedInput))
        .findFirst()
        .orElse(null);
    assertNotNull(documentAfterSecondBorrow);
    assertEquals(document.getBrwcopiers(), documentAfterSecondBorrow.getBrwcopiers());

    // kiem tra khong co ban ghi muon moi nao dc them
    ArrayList<Borrowing> borrowings = borrowingDatabase.selectAll();
    long borrowingCount = borrowings.stream()
        .filter(b -> b.getDocumentTitle().equals(simulatedInput) &&
            b.getUserName().equals("John Doe"))
        .count();
    assertEquals(1, borrowingCount);
  }
}
