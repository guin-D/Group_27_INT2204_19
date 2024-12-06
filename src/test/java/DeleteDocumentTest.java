import LibraryManagement.commandline.DeleteDocument;
import LibraryManagement.commandline.TestMan;
import LibraryManagement.commandline.User;
import LibraryManagement.DAO.DocumentDatabase;
import LibraryManagement.commandline.Document;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteDocumentTest {

  @Test
  void DeleteDocumentByNameTest() {
    // dau vao gia lap
    String simulatedInput = "lusucvatmatnet";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    // tao nguoi dung thuc thi
    User user = new TestMan("John Doe", "123456789", "password", "Admin");

    // lay danh sach tai lieu truoc khi xoa
    DocumentDatabase documentDatabase = DocumentDatabase.getInstance();
    List<Document> documentsBefore = documentDatabase.selectAll();
    assertFalse(documentsBefore.isEmpty());

    // thuc hien xoa tai lieu
    new DeleteDocument().oper(user);

    // lay danh sach tai lieu sau khi xoa
    List<Document> documentsAfter = documentDatabase.selectAll();
    assertNotNull(documentsAfter);
    assertEquals(documentsBefore.size() - 1, documentsAfter.size());

    // kiem tra tai lieu da bi xoa chua
    assertFalse(documentsAfter.stream()
            .anyMatch(doc -> doc.getTitle().equals(simulatedInput)));
  }
}
