package LibraryManagement.commandline;

import LibraryManagement.Database.DocumentDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class displayDocument implements IOOperation {

    @Override
    public void oper(Database database, User user) {
//        ArrayList<Document> documents = database.getAllDocuments();
        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
        System.out.printf("%-50s %-40s %-30s %-20s %-10s %-10s %-20s\n",
                "Title", "Author", "Publisher", "ISBN", "Qty", "Price", "Brw cps");

        for (Document b : documents) {
            System.out.printf("%-50s %-40s %-30s %-20s %-10d %-10.2f %-20d\n",
                    b.getTitle(), b.getAuthor(), b.getPublisher(),
                    b.getISBN(), b.getQty(), b.getPrice(), b.getBrwcopiers());
        }

        System.out.println("\n");

        user.menu(database, user);
        System.out.println("End ... ");

    }


}
