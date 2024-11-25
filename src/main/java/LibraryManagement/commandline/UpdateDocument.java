package LibraryManagement.commandline;

import LibraryManagement.Database.DocumentDatabase;

import java.util.Scanner;

public class UpdateDocument implements IOOperation {
    @Override
    public void oper(User user) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the document title: ");
        String documentTitle = s.nextLine();
//        int i = database.getDocument(documentTitle);
        Document find = new Document();
        find.setTitle(documentTitle);
        Document document = DocumentDatabase.getInstance().selectBy(find);

        try {
            System.out.println("\n" + document.toString() + "\n");
            System.out.println("Author after update: ");
            document.setAuthor(s.nextLine());
            System.out.println("Publisher after update: ");
            document.setPublisher(s.nextLine());
            System.out.println("ISBN after update: ");
            document.setISBN(s.nextLine());
            System.out.println("Qty after update: ");
            document.setQty(s.nextInt());
            System.out.println("Price after update: ");
            document.setPrice(s.nextDouble());
            System.out.println("Copies for borrowing after update: ");
            document.setBrwcopiers(s.nextInt());

//            database.updateDocument(i, document);
            DocumentDatabase.getInstance().update(document);
        } catch (Exception e){
            System.out.println("Document doesn't exist!\n");
        }
        user.menu(user);
    }
}
