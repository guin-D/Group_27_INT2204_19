package LibraryManagement.example.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDate;

public class Database {
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<String> usernames = new ArrayList<String>();
    private ArrayList<Document> documents = new ArrayList<Document>();
    private ArrayList<String> documentnames = new ArrayList<String>();
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ArrayList<Borrowing> borrowings = new ArrayList<Borrowing>();

    private File folder = new File("D:\\LibraryManagement\\Data");
    private File folder2 = new File("D:\\LibraryManagement");

    private File usersfile = new File("D:\\LibraryManagement\\Data\\Users");
    private File documentsfile = new File("D:\\LibraryManagement\\Data\\Documents");
    private File ordersfile = new File("D:\\LibraryManagement\\Data\\Orders");
    private File borrowingsfile = new File("D:\\LibraryManagement\\Data\\Borrowing");

    public Database() {
        if (!folder.exists()) {
            folder.mkdir();
        }
        if (!folder2.exists()) {
            folder2.mkdir();
        }
        if (!usersfile.exists()) {
            try {
                usersfile.createNewFile();
            } catch (Exception e) {
            }
        }
        if (!documentsfile.exists()) {
            try {
                documentsfile.createNewFile();
            } catch (Exception e) {
            }
        }
        if (!ordersfile.exists()) {
            try {
                ordersfile.createNewFile();
            } catch (Exception e) {
            }

        }
        if (!borrowingsfile.exists()) {
            try {
                borrowingsfile.createNewFile();
            } catch (Exception e) {
            }
        }
        getUser();
        getDocuments();
        getOrders();
        getBorrowing();
    }

    public void AddUser(User s) {
        users.add(s);
        usernames.add(s.getName());
        saveUsers();
    }

    public int login(String phonenumber, String password) {
        int n = -1;
        for (User s : users) {
            if (s.getPhonenumber().matches(phonenumber) && s.getPassword().matches(password)) {
                n = users.indexOf(s);
                break;
            }
        }
        return n;
    }

    public User getUser(int n) {
        return users.get(n);
    }

    public void AddDocument(Document document) {
        documents.add(document);
        documentnames.add(document.getName());
        saveDocuments();
    }

    private void getUser() {
        String text1 = "";
        try {
            BufferedReader br1 = new BufferedReader(new FileReader(usersfile));
            String s1;
            while ((s1 = br1.readLine()) != null) {
                text1 = text1 + s1;
            }
            br1.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        if (!text1.matches("") || !text1.isEmpty()) {
            String[] a1 = text1.split("<NewUser/>");
            for (String s : a1) {
                String[] a2 = s.split("<N/>");
                if (a2[4].matches("Admin")) {
                    User user = new Admin(a2[0], a2[1], a2[2], a2[3]);
                    users.add(user);
                    usernames.add(user.getName());
                } else {
                    User user = new NormalUser(a2[0], a2[1], a2[2], a2[3]);
                    users.add(user);
                    usernames.add(user.getName());
                }
            }
        }
    }

    private void saveUsers() {
        String text1 = "";
        for (User user : users) {
            text1 = text1 + user.toString() + "<NewUser/>\n";
        }
        try {
            PrintWriter pw = new PrintWriter(usersfile);
            pw.print(text1);
            pw.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    private void saveDocuments() {
        String text1 = "";
        for (Document document : documents) {
            text1 = text1 + document.toString2() + "<NewDocument/>\n";
        }
        try {
            PrintWriter pw = new PrintWriter(documentsfile);
            pw.print(text1);
            pw.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    private void getDocuments() {
        String text1 = "";
        try {
            BufferedReader br1 = new BufferedReader(new FileReader(documentsfile));
            String s1;
            while ((s1 = br1.readLine()) != null) {
                text1 = text1 + s1;
            }
            br1.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        if (!text1.matches("") || !text1.isEmpty()) {
            String[] a1 = text1.split("<NewDocument/>");
            for (String s : a1) {
                Document document = parseDocument(s);
                documents.add(document);
                documentnames.add(document.getName());
            }
        }
    }

    public Document parseDocument(String s) {
        String[] a = s.split("<N/>");
        Document document = new Document();
        document.setName(a[0]);
        document.setAuthor(a[1]);
        document.setPublisher(a[2]);
        document.setGenre(a[3]);
        document.setQty(Integer.parseInt(a[4]));
        document.setPrice(Double.parseDouble(a[5]));
        document.setBrwcopiers(Integer.parseInt(a[6]));

        return document;
    }

    public ArrayList<Document> getAllDocuments() {
        return documents;
    }

    public int getDocument(String documentname) {
        int i = -1;

        for (Document document : documents) {
            if (document.getName().matches(documentname)) {
                i = documents.indexOf(document);
            }
        }
        return i;
    }

    public Document getDocument(int i) {
        return documents.get(i);
    }

    public void deleteDocument(int i) {
        documents.remove(i);
        documentnames.remove(i);
        saveDocuments();
    }

    public void deleteAllData() {
        if (!usersfile.exists()) {
            try {
                usersfile.delete();
            } catch (Exception e) {
            }
        }
        if (!documentsfile.exists()) {
            try {
                documentsfile.delete();
            } catch (Exception e) {
            }
        }
        if (!ordersfile.exists()) {
            try {
                ordersfile.delete();
            } catch (Exception e) {
            }
        }
    }

    public void addOrder(Order order, Document document, int documentindex) {
        orders.add(order);
        documents.set(documentindex, document);
        saveOrder();
        saveDocuments();
        getBorrowing();
    }

    public void saveOrder() {
        String text1 = "";
        for (Order order : orders) {
            text1 = text1 + order.toString2() + "<NewOrder/>\n";
        }
        try {
            PrintWriter pw = new PrintWriter(ordersfile);
            pw.print(text1);
            pw.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    private void getOrders() {
        String text1 = "";
        try {
            BufferedReader br1 = new BufferedReader(new FileReader(ordersfile));
            String s1;
            while ((s1 = br1.readLine()) != null) {
                text1 = text1 + s1;
            }
            br1.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        if (!text1.matches("") || !text1.isEmpty()) {
            String[] a1 = text1.split("<NewDocument/>");
            for (String s : a1) {
                Order order = parseOrder(s);
                orders.add(order);
            }
        }
    }

    public boolean userExists(String name) {
        boolean f = false;
        for (User user : users) {
            if (user.getName().toLowerCase().matches(name.toLowerCase())) {
                f = true;
                break;
            }
        }
        return f;
    }

    private User getUserByName(String name) {
        User u = new NormalUser("");
        for (User user : users) {
            if (user.getName().matches(name)) {
                u = user;
                break;
            }
        }
        return u;
    }

    private Order parseOrder(String s) {
        String[] a = s.split("<N/>");
        Order order = new Order(documents.get(getDocument(a[0])), getUserByName(a[1]),
                Double.parseDouble(a[2]), Integer.parseInt(a[3]));

        return order;
    }

    public ArrayList<Order> getAllOrder() {
        return orders;
    }

    private void saveBorrowings() {
        String text1 = "";
        for (Borrowing borrowing : borrowings) {
            text1 = text1 + borrowing.toString2() + "<NewBorrowing/>\n";
        }
        try {
            PrintWriter pw = new PrintWriter(borrowingsfile);
            pw.print(text1);
            pw.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    private void getBorrowing() {
        String text1 = "";
        try {
            BufferedReader br1 = new BufferedReader(new FileReader(borrowingsfile));
            String s1;
            while ((s1 = br1.readLine()) != null) {
                text1 = text1 + s1;
            }
            br1.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        if (!text1.matches("") || !text1.isEmpty()) {
            String[] a1 = text1.split("<NewBorrowing/>");
            for (String s : a1) {
                Borrowing borrowing = parseBorrowing(s);
                borrowings.add(borrowing);
            }
        }
    }

    private Borrowing parseBorrowing(String s) {
        String[] a = s.split("<N/>");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(a[0], formatter);
        LocalDate finish = LocalDate.parse(a[1], formatter);
        Document document = documents.get(getDocument(a[3]));
        User user = getUserByName(a[4]);
        Borrowing brw = new Borrowing(start, finish, document, user);
        return brw;
    }

    public void borrowDocument(Borrowing brw, Document document, int documentindex) {
        borrowings.add(brw);
        documents.set(documentindex, document);
        saveBorrowings();
        saveDocuments();
    }

    public ArrayList<Borrowing> getBrws() {
        return borrowings;
    }

    public void returnDocument(Borrowing b, Document document, int documentindex) {
        borrowings.remove(b);
        documents.set(documentindex, document);
        saveDocuments();
    }

    public void updateDocument(int i, Document document) {
        documents.set(i, document);
    }

}
