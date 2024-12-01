package LibraryManagement.commandline;

public class Document {

    private String title;      //title
    private String author;    //author
    private String publisher; //publisher
    private String isbn;   //  Genre
    private String status;    //Borrowing Status
    private int qty;          // Copies for sale
    private double price;     //price
    private int brwcopiers;   // Copies for borrowing
    private String imageLink;

    public Document() {
    }

    public Document(String title, String author, String publisher,
                    String genre, int qty, double price, int brwcopiers, String description) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = genre;
        this.qty = qty;
        this.price = price;
        this.brwcopiers = brwcopiers;
    }

    public Document(String title, String author, String publisher,
                    String isbn, int qty, double price, int brwcopiers, String description, String imageLink) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.qty = qty;
        this.price = price;
        this.brwcopiers = brwcopiers;
        this.imageLink = imageLink;
    }


    public String toString() {
        String text = "Document Title: " + title + "\n" +
                "Document Author: " + author + "\n" +
                "Document Publisher: " + publisher + "\n" +
                "International Standard Book Number: " + isbn + "\n" +
                "Qty: " + String.valueOf(qty) + "\n" +
                "Price: " + String.valueOf(price) + "\n" +
                "Borrowing Copies: " + String.valueOf(brwcopiers) + "\n";
        return text;
    }

    public Document(String title, String author, String publisher,
                    String isbn, String status, int qty, double price, int brwcopiers, String description) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.status = status;
        this.qty = qty;
        this.price = price;
        this.brwcopiers = brwcopiers;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String toString2() {
        String text = title + "<N/>" + author + "<N/>" + publisher + "<N/>" +
                isbn + "<N/>" + String.valueOf(qty) + "<N/>" + String.valueOf(price) + "<N/>" + String.valueOf(brwcopiers) + "<N/>";
        return text;
    }

}
