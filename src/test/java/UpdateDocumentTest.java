import LibraryManagement.commandline.*;
import LibraryManagement.DAO.DocumentDatabase;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.List;

public class UpdateDocumentTest {

  @Test
  void UpdateDocumentTest() {
    //gia lap cac dau vao
    String documentTitle = "adumamay";
    String newAuthor = "wow";
    String newPublisher = "wow";
    String newIsbn = "wow";
    int newQty = 6;
    double newPrice = 6;
    int newBrwcopiers = 6;
    System.setIn(new ByteArrayInputStream((documentTitle + "\n" + newAuthor + "\n" + newPublisher + "\n" +
        newIsbn + "\n" + newQty + "\n" + newPrice + "\n" + newBrwcopiers).getBytes()));

    // tao nguoi dung thuc thi va lay du lieu tu database
    User user = new TestMan("John Doe", "123456789", "password", "Admin");
    DocumentDatabase documentDatabase = DocumentDatabase.getInstance();
    List<Document> documentsBefore = documentDatabase.selectAll();
    assertNotNull(documentsBefore, "Document list should not be null");

    // check xem tai lieu can doi co ton tai ko
    assertTrue(documentsBefore.stream()
            .anyMatch(doc -> doc.getTitle().equals(documentTitle)),
        "Document to update not found");

    // thuc hien cap nhat tai lieu
    new UpdateDocument().oper(user);

    // kiem tra danh sach tai lieu vua cap nhat
    List<Document> documentsAfter = documentDatabase.selectAll();
    assertNotNull(documentsAfter, "Document list should not be null after update");
    assertEquals(documentsBefore.size(), documentsAfter.size(), "Document count mismatch");

    // kiem tra tai lieu cap nhat da dung mong muon chua
    assertTrue(documentsAfter.stream()
            .anyMatch(doc -> doc.getTitle().equals(documentTitle) &&
                doc.getAuthor().equals(newAuthor) &&
                doc.getPublisher().equals(newPublisher) &&
                doc.getIsbn().equals(newIsbn) &&
                doc.getQty() == newQty &&
                doc.getPrice() == newPrice &&
                doc.getBrwcopiers() == newBrwcopiers));
  }
}
