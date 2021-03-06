package com.kacperfrankowski.library.database;

import com.kacperfrankowski.library.Book;
import com.kacperfrankowski.library.BookService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseBookService implements BookService {

    private Connection connection;
    private ResultSet resultSet = null;
    private String mySqlUrl = "jdbc:mysql://localhost/library?serverTimezone=UTC";
    private String username = "root";
    private String password = "135elo99";


    public DatabaseBookService() throws RuntimeException {
        try {
            this.connection = DriverManager.getConnection(mySqlUrl, username, password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected Connection getConnection() {
        return connection;
    }

    public boolean addBook(Book book) throws RuntimeException {
        String sql = "INSERT INTO books (title,author,borrowed) VALUES (?,?,false)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteBook(int id) throws RuntimeException {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Book getOneBook(int id) throws RuntimeException {
        String sql = "SELECT id,title,author,borrowed FROM books WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int idBook = resultSet.getInt(1);
                String titleBook = resultSet.getString(2);
                String authorBook = resultSet.getString(3);
                return new Book(idBook, titleBook, authorBook);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public void editBook(int bookId, Book updatedBook) throws RuntimeException {
        String sql = "UPDATE books SET title = ?, author = ?  WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, updatedBook.getTitle());
            preparedStatement.setString(2, updatedBook.getAuthor());
            preparedStatement.setInt(3, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Book> getAllBooks() throws RuntimeException{
        String sql = "SELECT * FROM books";
        List<Book> listOfAllBooks = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getInt(1) > 0) {
                    int bookId = resultSet.getInt(1);
                    String bookTitle = resultSet.getString(2);
                    String bookAuthor = resultSet.getString(3);
                    boolean bookIsBorrowed = resultSet.getBoolean(4);
                    listOfAllBooks.add(new Book(bookId, bookTitle, bookAuthor, bookIsBorrowed));
                }
            }
            return listOfAllBooks;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    public void close() throws RuntimeException {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
