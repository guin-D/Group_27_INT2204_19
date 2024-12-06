package LibraryManagement.commandline;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * This class represents a borrowing transaction for a document in the library system.
 * It tracks the borrowing start and finish dates, the remaining days to return the document,
 * and the user who borrowed the document.
 */
public class Borrowing {
    private LocalDate start;
    private LocalDate finish;
    private int daysLeft;
    private Document document;
    private User user;
    private String documentTitle;
    private String userName;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructor for creating a borrowing record with a 14-day loan period from the current date.
     *
     * @param document The document being borrowed.
     * @param user The user who is borrowing the document.
     */
    public Borrowing(Document document, User user) {
        this.start = LocalDate.now();
        this.finish = start.plusDays(14);
        this.daysLeft = Period.between(start, finish).getDays();
        this.document = document;
        this.user = user;
    }

    /**
     * Constructor for creating a borrowing record with specific start and finish dates.
     *
     * @param start The start date of the borrowing.
     * @param finish The finish (due) date of the borrowing.
     * @param document The document being borrowed.
     * @param user The user who is borrowing the document.
     */
    public Borrowing(LocalDate start, LocalDate finish, Document document, User user) {
        this.start = start;
        this.finish = finish;
        this.daysLeft = Period.between(finish, LocalDate.now()).getDays();  // Negative if overdue
        this.document = document;
        this.user = user;
    }

    /**
     * Constructor for creating a borrowing record with specific start and finish dates and document/user information as strings.
     *
     * @param start The start date of the borrowing.
     * @param finish The finish (due) date of the borrowing.
     * @param document The title of the document being borrowed.
     * @param user The name of the user borrowing the document.
     */
    public Borrowing(LocalDate start, LocalDate finish, String document, String user) {
        this.start = start;
        this.finish = finish;
        this.daysLeft = Period.between(LocalDate.now(), finish).getDays();  // Remaining days until return
        this.documentTitle = document;
        this.userName = user;
    }

    public String getStart() {
        return formatter.format(start);
    }

    public String getFinish() {
        return formatter.format(finish);
    }

    public int getDaysLeft() {
        return Period.between(LocalDate.now(), finish).getDays();
    }

    public Document getDocument() {
        return document;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public String getUserName() {
        return userName;
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

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    @Override
    public String toString() {
        return "Borrowing time: " + start + "\nExpire date: " + finish + "\nDays left: " + daysLeft;
    }
}
