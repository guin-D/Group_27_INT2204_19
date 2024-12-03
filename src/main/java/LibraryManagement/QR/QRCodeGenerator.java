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
      // Tạo nội dung mã QR từ thông tin sách
      String content = "Title: " + document.getTitle() + "\n" +
          "ISBN: " + document.getIsbn() + "\n" +
          "Author: " + document.getAuthor() + "\n" +
          "Publisher: " + document.getPublisher() + "\n" +
          "Price: " + document.getPrice();

      // Thiết lập cấu hình mã QR
      Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
      hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); // Mức sửa lỗi
      hintMap.put(EncodeHintType.MARGIN, 2); // Kích thước lề

      // Tạo mã QR
      BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 300, 300, hintMap);

      // Tạo hình ảnh từ BitMatrix
      BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
      for (int x = 0; x < 300; x++) {
        for (int y = 0; y < 300; y++) {
          image.setRGB(x, y, (matrix.get(x, y) ? 0x000000 : 0xFFFFFF)); // Đen: 0x000000, Trắng: 0xFFFFFF
        }
      }

      // Lưu hình ảnh thành file
      ImageIO.write(image, "PNG", new File("D:\\QRCODE.png"));
      System.out.println("QR Code has been generated successfully and saved as 'document_qr.png'.");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
