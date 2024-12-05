package LibraryManagement.commandline;

public class Document {

    private String title;      // title
    private String author;     // author
    private String publisher;  // publisher
    private String isbn;       // ISBN
    private int qty;           // copies for sale
    private double price;      // price of 1 document
    private int brwcopiers;    // copies for borrowing
    private String imageLink;  // link of document's image

    public Document(){}

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
