import LibraryManagement.commandline.BorrowDocuments;
import LibraryManagement.commandline.TestMan;
import LibraryManagement.commandline.User;
import LibraryManagement.DAO.DocumentDatabase;
import LibraryManagement.DAO.BorrowingDatabase;
import LibraryManagement.commandline.Document;
import LibraryManagement.commandline.Borrowing;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BorrowDocumentsTest {

  User user = new TestMan("John Doe", "123456789", "password", "Normal");

  @Test
  void BorrowDocumentTest() {
    // chuyen huong dau ra
    PrintStream originalOut = System.out;
    ByteArrayOutputStream tempStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(tempStream));
    try {
      // thiet lap dau vao test
      String borrowDoc = "adumamay";
      System.setIn(new ByteArrayInputStream(borrowDoc.getBytes()));

      // chuan bi tai lieu trong database
      DocumentDatabase documentDatabase = DocumentDatabase.getInstance();

      // tao doi tuong user de thuc thi lenh

      // lay so ban sao cho muon truoc khi muon sach
      Document documentBeforeBorrow = documentDatabase.selectAll().stream()
          .filter(doc -> doc.getTitle().equals(borrowDoc))
          .findFirst()
          .orElse(null);
      assertNotNull(documentBeforeBorrow, "Document not exist!");
      int initialBorrowingCopies = documentBeforeBorrow.getBrwcopiers();

      // thuc hien viec lay tai lieu
      BorrowDocuments borrowDocuments = new BorrowDocuments();
      borrowDocuments.oper(user);

      // kiem tra so luong ban sao cho muon sau khi muon sach
      Document documentAfterFirstBorrow = documentDatabase.selectAll().stream()
          .filter(doc -> doc.getTitle().equals(borrowDoc))
          .findFirst()
          .orElse(null);
      assertNotNull(documentAfterFirstBorrow, "Document should not have 0 brwcopiers!");
      assertEquals(initialBorrowingCopies - 1, documentAfterFirstBorrow.getBrwcopiers(),
          "Brwcopiers not decrease!");

      // kiem tra da co ban muon trong borrowingdatabase
      ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
      assertTrue(borrowings.stream().anyMatch(
              b -> b.getDocumentTitle().equals(borrowDoc) &&
                  b.getUserName().equals("John Doe")),
          "There's no borrow record in the BorrowingDatabase!");
    } finally {
      System.setOut(originalOut);
    }
  }
}
