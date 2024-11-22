package LibraryManagement.commandline;

public class Document {

    private String title;      //title
    private String author;    //author
    private String publisher; //publisher
    private String ISBN;   //  Genre
    private String status;    //Borrowing Status
    private int qty;          // Copies for sale
    private double price;     //price
    private int brwcopiers;   // Copies for borrowing
    private String description;

    public Document() {
    }

    ;


    public Document(String title, String author, String publisher,
                    String genre, int qty, double price, int brwcopiers, String description) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.ISBN = genre;
        this.qty = qty;
        this.price = price;
        this.brwcopiers = brwcopiers;
        this.description = description;
    }

    public String toString() {
        String text = "Document Title: " + title + "\n" +
                "Document Author: " + author + "\n" +
                "Document Publisher: " + publisher + "\n" +
                "International Standard Book Number: " + ISBN + "\n" +
                "Qty: " + String.valueOf(qty) + "\n" +
                "Price: " + String.valueOf(price) + "\n" +
                "Borrowing Copies: " + String.valueOf(brwcopiers) + "\n" +
                "Description: " + description;
        return text;
    }

    public Document(String title, String author, String publisher,
                    String isbn, String status, int qty, double price, int brwcopiers, String description) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.ISBN = isbn;
        this.status = status;
        this.qty = qty;
        this.price = price;
        this.brwcopiers = brwcopiers;
        this.description = description;
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

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString2() {
        String text = title + "<N/>" + author + "<N/>" + publisher + "<N/>" +
                ISBN + "<N/>" + String.valueOf(qty) + "<N/>" + String.valueOf(price) + "<N/>" + String.valueOf(brwcopiers) + "<N/>" + description + "<N/>";
        return text;
    }

}
