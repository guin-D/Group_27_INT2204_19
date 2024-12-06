import LibraryManagement.commandline.AddDocument;
import LibraryManagement.commandline.TestMan;
import LibraryManagement.commandline.User;
import LibraryManagement.DAO.DocumentDatabase;
import LibraryManagement.commandline.Document;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AddDocumentTest {

  @Test
  void AddDocumentTest() {
    // chuyen huong system.out de ko in ra phuong thuc adddoc
    PrintStream originalOut = System.out;
    ByteArrayOutputStream tempStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(tempStream)); // Chuyển hướng

    boolean documentAdded = false;
    try {
      // thiet lap dau vao
      String title = "power";
      String author = "gd";
      String publisher = "sm";
      String isbn = "2024";
      int qty = 2;
      double price = 3;
      int brwcopiers = 5;
      String newDoc = title + "\n" + author + "\n" + publisher + "\n" + isbn +
          "\n" + qty + "\n" + price + "\n" + brwcopiers;
      System.setIn(new ByteArrayInputStream(newDoc.getBytes()));

      DocumentDatabase documentDatabase = DocumentDatabase.getInstance();
      int initialDocumentCount = documentDatabase.selectAll().size();

      // tao nguoi dung thuc thi va thuc thi lenh them tai lieu
      User user = new TestMan("John Doe", "123456789", "password", "Admin");
      new AddDocument().oper(user);

      // kiem tra trong docdatabase co them tai lieu moi chua
      ArrayList<Document> documents = documentDatabase.selectAll();
      assertEquals(initialDocumentCount + 1, documents.size(),
          "There's already document with this name!");

      // kiem tra chi tiet tai lieu vua them xem dung thong tin khong
      documentAdded = documents.stream().anyMatch(doc ->
          doc.getTitle().equals(title) &&
              doc.getAuthor().equals(author) &&
              doc.getPublisher().equals(publisher) &&
              doc.getIsbn().equals(isbn) &&
              doc.getQty() == qty &&
              doc.getPrice() == price &&
              doc.getBrwcopiers() == brwcopiers
      );

      assertTrue(documentAdded, "The information of the book is not match!");

    } finally {
      // khoi phuc system.out
      System.setOut(originalOut);
    }
  }
}
