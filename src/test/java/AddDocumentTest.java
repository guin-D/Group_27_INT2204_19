import LibraryManagement.commandline.AddDocument;
import LibraryManagement.commandline.TestMan;
import LibraryManagement.commandline.User;
import LibraryManagement.DAO.DocumentDatabase;
import LibraryManagement.commandline.Document;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AddDocumentTest {

  @Test
  void AddDocumentTest() {
    // test dau vao: title/author/publisher/isbn/qty/pricebrwcopiers/imagelink
    String title = "";
    String author = "";
    String publisher = "";
    String isbn = "";
    int qty = 2;
    double price = 3;
    int brwcopiers = 5;
    String simulatedInput = title + "\n" + author + "\n" + publisher + "\n" + isbn +
        "\n" + qty + "\n" +price + "\n" + brwcopiers;
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));


    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {

      DocumentDatabase documentDatabase = DocumentDatabase.getInstance();
      int initialDocumentCount = documentDatabase.selectAll().size();

      // tao nguoi dung va thuc hien hanh dong them tai lieu bang tai khoan ay
      User user = new TestMan("John Doe", "123456789", "password", "Admin");
      new AddDocument().oper(user);

      // kiem tra so luong tai lieu sau khi them
      ArrayList<Document> documents = documentDatabase.selectAll();
      assertEquals(initialDocumentCount + 1, documents.size());

      // kiem tra chi tiet tai lieu vua them
      boolean documentAdded = documents.stream().anyMatch(doc ->
          doc.getTitle().equals(title) &&
              doc.getAuthor().equals(author) &&
              doc.getPublisher().equals(publisher) &&
              doc.getIsbn().equals(isbn) &&
              doc.getQty() == qty &&
              doc.getPrice() == price &&
              doc.getBrwcopiers() == brwcopiers
      );

      assertTrue(documentAdded);
    } finally {
      System.setOut(originalOut);
    }
  }
}
