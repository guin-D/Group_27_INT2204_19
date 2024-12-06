package LibraryManagement.commandline;

/**
 * This class represents a document in the library. It contains details such as the title, author, publisher,
 * ISBN, quantity, price, borrowing copies, and an image link.
 */
public class Document {

    // Fields representing the document's details
    private String title;      // Title of the document
    private String author;     // Author of the document
    private String publisher;  // Publisher of the document
    private String isbn;       // ISBN of the document
    private int qty;           // Quantity of copies available for sale
    private double price;      // Price of one copy of the document
    private int brwcopiers;    // Number of copies available for borrowing
    private String imageLink;  // Link to the image of the document

    // Constructor
    public Document() {
    }

    /**
     * Constructor to initialize a Document with the given details.
     *
     * @param title      The title of the document.
     * @param author     The author of the document.
     * @param publisher  The publisher of the document.
     * @param isbn       The ISBN of the document.
     * @param qty        The quantity of copies available for sale.
     * @param price      The price of one copy of the document.
     * @param brwcopiers The number of copies available for borrowing.
     * @param imageLink  The link to the image of the document.
     */
    public Document(String title, String author, String publisher,
                    String isbn, int qty, double price, int brwcopiers, String imageLink) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.qty = qty;
        this.price = price;
        this.brwcopiers = brwcopiers;
        this.imageLink = imageLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBrwcopiers() {
        return brwcopiers;
    }

    public void setBrwcopiers(int brwcopiers) {
        this.brwcopiers = brwcopiers;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
