package LibraryManagement.example.model;

public class Order {
    private Document document;
    private User user;
    private double price;
    private int qty;

    public Order(Document document, User user, double price, int qty) {
        this.document = document;
        this.user = user;
        this.price = price;
        this.qty = qty;
    }

    public Order() {
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

    public String toString() {
        return "Document name: " + document.getName() + "\n" +
                "Username: " + user.getName() + "\n" +
                "Price: " + String.valueOf(price) + "\n" +
                "Qty: " + String.valueOf(qty);
    }

    public String toString2() {
        return document.getName() + "<N/>" +
                user.getName() + "<N/>" +
                String.valueOf(price) + "<N/>" +
                String.valueOf(qty);
    }
}
