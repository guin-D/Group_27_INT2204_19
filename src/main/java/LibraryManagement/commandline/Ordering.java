package LibraryManagement.commandline;

/**
 * Represents an ordering of a document by a user, including details such as price and quantity.
 */
public class Ordering {

    private Document document;    // The document being ordered
    private User user;            // The user placing the order
    private double price;         // Price of the document
    private int qty;              // Quantity of the document ordered
    private String documentTitle; // Title of the document (only used in one constructor)
    private String userName;      // Username of the user (only used in one constructor)

    /**
     * Constructor to create an order with Document and User objects.
     *
     * @param document The document being ordered.
     * @param user The user placing the order.
     * @param price The price of the document.
     * @param qty The quantity of the document ordered.
     */
    public Ordering(Document document, User user, double price, int qty) {
        this.document = document;
        this.user = user;
        this.price = price;
        this.qty = qty;
    }

    /**
     * Constructor to create an order with document title and user name as strings.
     * This constructor is less recommended because it doesn't use the actual Document and User objects.
     *
     * @param documentTitle The title of the document being ordered.
     * @param userName The name of the user placing the order.
     * @param price The price of the document.
     * @param qty The quantity of the document ordered.
     */
    public Ordering(String documentTitle, String userName, double price, int qty) {
        this.documentTitle = documentTitle;
        this.userName = userName;
        this.price = price;
        this.qty = qty;
    }

    /**
     * Default constructor for an ordering instance.
     */
    public Ordering() {
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
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
        return document != null ? document.getTitle() : documentTitle;
    }

    public String getUserName() {
        return user != null ? user.getName() : userName;
    }

    @Override
    public String toString() {
        return "Document Title: " + getDocumentTitle() + "\n" +
                "Username: " + getUserName() + "\n" +
                "Price: " + price + "\n" +
                "Qty: " + qty;
    }
}
