package LibraryManagement.model;

import java.util.ArrayList;

public class displayDocument implements IOOperation {

    @Override
    public void oper(Database database, User user) {
        ArrayList<Document> documents = database.getAllDocuments();
        System.out.println("Name\t\tAuthor\t\tPublisher\tCLA\t\tQty"
                + "\tPrice\tBrw cps");

        for (Document b : documents) {
            System.out.println(b.getName() + "\t\t\t" + b.getAuthor() + "\t\t\t"
                    + b.getPublisher() + "\t\t\t" + b.getGenre() + "\t"
                    + "\t" + b.getQty() + "\t" + b.getPrice()
                    + "\t\t" + b.getBrwcopiers());
        }

        user.menu(database, user);
        System.out.println("End ... ");

    }


}
