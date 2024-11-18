package LibraryManagement.commandline;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Borrowing {
    LocalDate start;
    LocalDate finish;
    int daysleft;
    Document document;
    User user;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Borrowing(Document document, User user) {
        start = LocalDate.now();
        finish = start.plusDays(14);
        daysleft = Period.between(start, finish).getDays();
        this.document = document;
        this.user = user;
    }

    public Borrowing(LocalDate start, LocalDate finish, Document document, User user) {
        this.start = start;
        this.finish = finish;
        this.daysleft = Period.between(finish, LocalDate.now()).getDays();
        this.document = document;
        this.user = user;
    }

    public String getStart() {
        return formatter.format(start);
    }

    public String getFinish() {
        return formatter.format(finish);
    }

    public int getDaysleft() {
        return Period.between(finish, LocalDate.now()).getDays();
    }

    public Document getDocument() {
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

    public String toString() {
        return "Borrowing time: " + start + "\nExpiri date: " + finish + "\nDays left: " + daysleft;

    }

    public String toString2() {
        return getStart() + "<N/>" + getFinish() + "<N/>" + getDaysleft() + "<N/>" + document.getTitle() + "<N/>" +
                user.getName() + "<N/>";
    }

}
