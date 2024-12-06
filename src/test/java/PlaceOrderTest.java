import LibraryManagement.commandline.*;
import LibraryManagement.DAO.*;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlaceOrderTest {

  @Test
  void PlaceOrderTest() {
    // gia lap dau vao nguoi dung
    String documentTitle = "dungnguvcl";
    int orderQuantity = 15;
    System.setIn(new ByteArrayInputStream((documentTitle + "\n" + orderQuantity).getBytes()));

    // khoi tao doi tuong thuc thi va co so du lieu
    User user = new TestMan("John Doe", "123456789", "password", "Normal");
    DocumentDatabase documentDatabase = DocumentDatabase.getInstance();
    OrderDatabase orderDatabase = OrderDatabase.getInstance();

    // kiem tra cuon sach co ton tai va so luong dap ung du yeu cau
    Document documentBeforeOrder = documentDatabase.selectAll().stream()
        .filter(doc -> doc.getTitle().equals(documentTitle))
        .findFirst()
        .orElseThrow(() -> new AssertionError("Document not found"));
    int initialDocQty = documentBeforeOrder.getQty();
    assertFalse(initialDocQty == 1);
    assertTrue(initialDocQty > orderQuantity);

    // luu so luong don hang ban dau
    int initialOrderCount = orderDatabase.selectAll().size();

    // thuc hien dat hang
    new PlaceOrder().oper(user);

    // kiem tra so luong con lai sau khi dat hang
    Document documentAfterOrder = documentDatabase.selectAll().stream()
        .filter(doc -> doc.getTitle().equals(documentTitle))
        .findFirst()
        .orElseThrow(() -> new AssertionError("Document not found after order"));
    assertEquals(initialDocQty - orderQuantity, documentAfterOrder.getQty(), "Document quantity mismatch");

    // kiem tra don hang da dc luu vao csdl order
    assertTrue(orderDatabase.selectAll().stream()
            .anyMatch(order -> order.getDocumentTitle().equals(documentTitle)));

    // kiem tra so don hang tang len
    assertEquals(initialOrderCount + 1, orderDatabase.selectAll().size());
  }
}
