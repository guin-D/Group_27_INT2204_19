package LibraryManagement.commandline;

import javafx.scene.shape.StrokeLineCap;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Borrowing {
    LocalDate start;
    LocalDate finish;
    int daysLeft;
    Document document;
    User user;
    String documentTitle;
    String userName;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Borrowing(Document document, User user) {
        start = LocalDate.now();
        finish = start.plusDays(14);
        daysLeft = Period.between(start, finish).getDays();
        this.document = document;
        this.user = user;
    }

    public Borrowing(LocalDate start, LocalDate finish, Document document, User user) {
        this.start = start;
        this.finish = finish;
        this.daysLeft = Period.between(finish, LocalDate.now()).getDays();
        this.document = document;
        this.user = user;
    }

    public Borrowing(LocalDate start, LocalDate finish, String document, String user) {
        this.start = start;
        this.finish = finish;
        this.daysLeft = Period.between(LocalDate.now(), finish).getDays();
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

    public String toString() {
        return "Borrowing time: " + start + "\nExpire date: " + finish + "\nDays left: " + daysLeft;
    }

    public String toString2() {
        return getStart() + "<N/>" + getFinish() + "<N/>" + getDaysLeft() + "<N/>" + document.getTitle() + "<N/>" +
                user.getName() + "<N/>";
    }

}
