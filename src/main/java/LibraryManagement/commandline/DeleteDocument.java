package LibraryManagement.commandline;

import LibraryManagement.DAO.DocumentDatabase;

import java.util.ArrayList;
import java.util.Scanner;

public class DeleteDocument implements IOOperation {

    @Override
    public void oper(User user) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter document title: ");
        String documentTitle = s.nextLine();

        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();

        int i = -1;
        for(Document d: documents) {
            if(d.getTitle().matches(documentTitle)){
                i = 0;
                break;
            }
        }

        Document find = new Document();
        find.setTitle(documentTitle);

        if(i == 0) {
            DocumentDatabase.getInstance().remove(find);
            System.out.println("Document deleted successfully!\n");
        } else {
            System.out.println("Document doesn't exist!\n");
        }

        user.menu(user);
    }
}
