package LibraryManagement.utils;

public class Book {
    private String title;
    private String isbn;
    private String author;
    private String publisher;
    private String description;
    private String imageLinks;

    public Book() {
        this.title = "";
        this.isbn = "";
        this.author = "";
        this.publisher = "";
        this.description = "";
        this.imageLinks = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.replaceAll("^\"|\"$", "");
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author.replaceAll("^\"|\"$", "");
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher.replaceAll("^\"|\"$", "");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.replaceAll("^\"|\"$", "");
    }

    public String getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(String imageLinks) {
        this.imageLinks = imageLinks.replaceAll("^\"|\"$", "");
    }
}
