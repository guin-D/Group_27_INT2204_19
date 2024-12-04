package LibraryManagement.Database;

import LibraryManagement.commandline.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class BorrowingDatabase {
    public static BorrowingDatabase getInstance() {
        return new BorrowingDatabase();
    }

    public int insert(Borrowing borrowing) {
        try {
            Connection connection = MySQL.getConnection();

            String sql = "INSERT INTO borrowing (start_day, finish_day, days_left, document, user) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDate(1, java.sql.Date.valueOf(borrowing.getStart()));
            preparedStatement.setDate(2, java.sql.Date.valueOf(borrowing.getFinish()));
            preparedStatement.setInt(3, borrowing.getDaysLeft());
            preparedStatement.setString(4, borrowing.getDocument().getTitle());
            preparedStatement.setString(5, borrowing.getUser().getName());

            int done = preparedStatement.executeUpdate();

            MySQL.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(Borrowing borrowing) {
        try {
            Connection connection = MySQL.getConnection();

            String sql = "UPDATE borrowing "
                    + "SET days_left = ? "
                    + "WHERE document = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, borrowing.getDaysLeft());
            preparedStatement.setString(2, borrowing.getDocumentTitle());

            int done = preparedStatement.executeUpdate();

            MySQL.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public ArrayList<Borrowing> selectAll() {
        ArrayList<Borrowing> borrowings = new ArrayList<Borrowing>();

        try {
            Connection connection = MySQL.getConnection();

            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM borrowing";

            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                LocalDate start = resultSet.getDate("start_day").toLocalDate();
                LocalDate finish = resultSet.getDate("finish_day").toLocalDate();
                int daysleft = resultSet.getInt("days_left");
                String document = resultSet.getString("document");
                String user = resultSet.getString("user");

                Borrowing borrowing = new Borrowing(start, finish, document, user);
                borrowings.add(borrowing);
            }

            MySQL.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowings;
    }

    public int delete(Borrowing borrowing) {
        try {
            Connection connection = MySQL.getConnection();

            Statement statement = connection.createStatement();

            String sql = "DELETE FROM borrowing "
                    + "WHERE document = '" + borrowing.getDocumentTitle() + "' "
                    + "AND user = '" + borrowing.getUserName() + "'";

            int done = statement.executeUpdate(sql);

            MySQL.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
