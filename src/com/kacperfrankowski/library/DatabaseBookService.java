package com.kacperfrankowski.library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseBookService implements BookService {

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private final String mySqlUrl = "jdbc:mysql://localhost/library?serverTimezone=UTC";
    private final String user = "root";
    private final String password = "135elo99";


    public DatabaseBookService(){
         try {
             this.connection = DriverManager.getConnection(mySqlUrl, user, password);
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
    }

        public void addBook(Book book) {
            String sql = "INSERT INTO books (title,author,borrowed) VALUES (?,?,false)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                    preparedStatement.setString(1, book.getTitle());
                    preparedStatement.setString(2, book.getAuthor());
                    preparedStatement.executeUpdate();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        public void deleteBook(int id){
            String sql = "DELETE FROM books WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

                preparedStatement.setInt(1,id);
                preparedStatement.executeUpdate();

            } catch (SQLException ex) {
                ex.getStackTrace();
            }
        }

        public Book selectBook(int id){
            String sql = "SELECT id,title,author,borrowed FROM books WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1,id);
                resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) {
                    int idBook = resultSet.getInt(1);
                    String titleBook = resultSet.getString(2);
                    String authorBook = resultSet.getString(3);
                    return new Book(idBook, titleBook, authorBook);
                }
            }  catch (SQLException ex){
                ex.getStackTrace();
            }
            return Book.NOT_FOUND;
        }

        public List<Book> selectAllBooks(){
            String sql = "SELECT * FROM books";
            List<Book> listOfAllBooks = new ArrayList<>();
            try (Statement statement = connection.createStatement()){
                resultSet = statement.executeQuery(sql);

                while(resultSet.next()){
                    int id = resultSet.getInt(1);
                    String title = resultSet.getString(2);
                    String author = resultSet.getString(3);
                    boolean isBorrowed = resultSet.getBoolean(4);
                    listOfAllBooks.add(new Book(id,title,author,isBorrowed));
                }
                return listOfAllBooks;
            }   catch (SQLException ex) {
                ex.getStackTrace();
            }
            return listOfAllBooks;
        }

        public void close(){
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.getStackTrace();
            }
        }

}
