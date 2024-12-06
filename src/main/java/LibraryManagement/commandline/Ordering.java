package LibraryManagement.commandline;

public class Ordering {
    private Document document;
    private User user;
    private double price;
    private int qty;
    private String documentTitle;
    private String userName;

    public Ordering(Document document, User user, double price, int qty) {
        this.document = document;
        this.user = user;
        this.price = price;
        this.qty = qty;
    }

    public Ordering(String document, String user, double price, int qty) {
        this.documentTitle = document;
        this.userName = user;
        this.price = price;
        this.qty = qty;
    }

    public Ordering() {
    }

    public Document getBook() {
        return document;
    }

    public void setBook(Document document) {
        this.document = document;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public String getUserName() {
        return userName;
    }

    public String toString() {
        return "Document Title: " + document.getTitle() + "\n" +
                "Username: " + user.getName() + "\n" +
                "Price: " + String.valueOf(price) + "\n" +
                "Qty: " + String.valueOf(qty);
    }
}
