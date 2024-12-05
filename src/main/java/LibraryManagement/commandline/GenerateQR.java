package LibraryManagement.commandline;

import LibraryManagement.Database.DocumentDatabase;
import LibraryManagement.QR.QRCodeGenerator;
import java.util.Scanner;

public class GenerateQR {

    public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);

      System.out.println("Enter the title of the book: ");
      String title = scanner.nextLine();

      DocumentDatabase documentDatabase = DocumentDatabase.getInstance();
      Document document = documentDatabase.getDocumentByTitle(title);

      if (document != null) {
        System.out.println("Book found: " + document.getTitle());
        System.out.println("Generating QR Code...");

        QRCodeGenerator.generateQRCode(document);
      } else {
        System.out.println("No book found with ISBN: " + title);
      }

      scanner.close(); // Đóng scanner sau khi sử dụng
    }
  }

