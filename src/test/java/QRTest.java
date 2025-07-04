import LibraryManagement.DAO.DocumentDatabase;
import LibraryManagement.commandline.Document;
import LibraryManagement.QR.QRCodeGenerator;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import com.google.zxing.*;

import java.io.FileInputStream;
import java.io.IOException;

public class QRTest {
  private Document document;

  @BeforeEach
  public void setUp() {
    //lay doi tuong de tao qr bang title
    String title = "power";
    DocumentDatabase documentDatabase = DocumentDatabase.getInstance();
    document = documentDatabase.getDocumentByTitle(title);
  }


  @Test
  public void QRCodeGenerationTest() {
    // chuyen huong dau ra
    PrintStream originalOut = System.out;
    ByteArrayOutputStream tempStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(tempStream));
    try {
      assertNotNull(document, "Doc not exist");

      // tao qr
      QRCodeGenerator.generateQRCode(document);

      // kiem tra xem da tao duoc qr chua
      String filePath =
          "src/main/resources/LibraryManagement/QRImage/" + document.getTitle() + ".png";
      File qrCodeFile = new File(filePath);

      assertTrue(qrCodeFile.exists(), "QRcode did not generated");
    } finally {
      System.setOut(originalOut);
    }
  }

  @Test
  public void QRCodeDecodeTest() throws IOException, NotFoundException {
    // chuyen huong dau ra
    PrintStream originalOut = System.out;
    ByteArrayOutputStream tempStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(tempStream));
    try {
    // kiem tra xem co anh qr cua document do ton tai hay khong
    String filePath = "src/main/resources/LibraryManagement/QRImage/" + document.getTitle() + ".png";
    File qrCodeFile = new File(filePath);
    assertTrue(qrCodeFile.exists(), "QRcode not exist");

    // giai ma qr
    String decodedContent = decodeQRCode(qrCodeFile);

    // kiem tra thong tin giai ma co trung thong tin document khong
    String expectedContent = "Title: " + document.getTitle() + "\n" +
        "ISBN: " + document.getIsbn() + "\n" +
        "Author: " + document.getAuthor() + "\n" +
        "Publisher: " + document.getPublisher() + "\n" +
        "Qty available for purchase: " + document.getQty() +
        " with price: " + document.getPrice() + "VND\n" +
        "Qty available for borrow: " + document.getBrwcopiers();

    assertEquals(expectedContent, decodedContent, "Information mismatch");

    } finally {
      System.setOut(originalOut);
    }
  }
  private String decodeQRCode(File qrCodeFile) {
      try {
        // doc hinh anh tu file
        FileInputStream fileInputStream = new FileInputStream(qrCodeFile);
        BufferedImage bufferedImage = ImageIO.read(fileInputStream);

        // giai ma qr
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Reader reader = new MultiFormatReader();
        Result result = reader.decode(bitmap);

        return result.getText();
      } catch (IOException | NotFoundException | ChecksumException | FormatException e) {
        e.printStackTrace();
        return null;  // tra ve null neu xay ra loi
    }
  }
}