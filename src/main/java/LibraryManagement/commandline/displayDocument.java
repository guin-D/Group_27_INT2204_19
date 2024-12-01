package LibraryManagement.commandline;

import LibraryManagement.Database.DocumentDatabase;

import java.util.ArrayList;

public class displayDocument implements IOOperation {

    @Override
    public void oper(User user) {
//        ArrayList<Document> documents = database.getAllDocuments();
        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
        System.out.printf("%-50s %-40s %-30s %-20s %-10s %-10s %-20s\n",
                "Title", "Author", "Publisher", "ISBN", "Qty", "Price", "Brw cps");

        for (Document b : documents) {
            System.out.printf("%-50s %-40s %-30s %-20s %-10d %-10.2f %-20d\n",
                    b.getTitle(), b.getAuthor(), b.getPublisher(),
                    b.getIsbn(), b.getQty(), b.getPrice(), b.getBrwcopiers());
        }

        System.out.println("\n");

        user.menu(user);
        System.out.println("End ... ");

    }


}
