package LibraryManagement.QR;

import LibraryManagement.commandline.Document;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

public class QRCodeGenerator {
  public static void generateQRCode(Document document) {
    try {
      String content = "Title: " + document.getTitle() + "\n" +
          "ISBN: " + document.getIsbn() + "\n" +
          "Author: " + document.getAuthor() + "\n" +
          "Publisher: " + document.getPublisher() + "\n" +
          "Qty available for purchase: " + document.getQty() + " with price: " +document.getPrice() + "\n" +
          "Qty available for borrow: " + document.getBrwcopiers() ;

      Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
      hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
      hintMap.put(EncodeHintType.MARGIN, 2);


      BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 300, 300, hintMap);


      BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
      for (int x = 0; x < 300; x++) {
        for (int y = 0; y < 300; y++) {
          image.setRGB(x, y, (matrix.get(x, y) ? 0x000000 : 0xFFFFFF)); // Đen: 0x000000, Trắng: 0xFFFFFF
        }
      }

      ImageIO.write(image, "PNG", new File("D:\\QRCODE.png"));
      System.out.println("QR Code has been generated successfully and saved as " + document.getTitle() + ".png");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
