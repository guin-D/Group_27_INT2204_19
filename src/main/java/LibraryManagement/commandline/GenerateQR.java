package LibraryManagement.commandline;

import LibraryManagement.Database.DocumentDatabase;
import LibraryManagement.QR.QRCodeGenerator;
import java.util.Scanner;

public class GenerateQR {

    public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);

      System.out.println("Enter the ISBN of the book: ");
      String isbn = scanner.nextLine(); // Đọc ISBN từ bàn phím

      // Lấy thông tin sách từ database
      DocumentDatabase documentDatabase = DocumentDatabase.getInstance();
      Document document = documentDatabase.getDocumentByISBN(isbn);

      if (document != null) {
        System.out.println("Book found: " + document.getTitle());
        System.out.println("Generating QR Code...");
        // Gọi hàm tạo mã QR
        QRCodeGenerator.generateQRCode(document);
      } else {
        System.out.println("No book found with ISBN: " + isbn);
      }

      scanner.close(); // Đóng scanner sau khi sử dụng
    }
  }

