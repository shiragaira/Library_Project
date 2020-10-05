package com.kacperfrankowski.library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseBookService implements BookService {

    private Connection connection = null;
    private ResultSet resultSet = null;
    private String mySqlUrl = "jdbc:mysql://localhost/library?serverTimezone=UTC";
    private String username = "root";
    private String password = "135elo99";


    public DatabaseBookService(){
         try {
             this.connection = DriverManager.getConnection(mySqlUrl, username, password);
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
                ex.printStackTrace();
            }
        }

        public Book getOneBook(int id){
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
                ex.printStackTrace();
            }
            return null;
        }

        public void editBook(int bookId, Book updatedBook){
            String sql = "UPDATE books SET title = ?, author = ?  WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, updatedBook.getTitle());
                preparedStatement.setString(2, updatedBook.getAuthor());
                preparedStatement.setInt(3,bookId);
                preparedStatement.executeUpdate();
            }
            catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        public List<Book> getAllBooks(){
            String sql = "SELECT * FROM books";
            List<Book> listOfAllBooks = new ArrayList<>();
            try (Statement statement = connection.createStatement()){
                resultSet = statement.executeQuery(sql);
                while(resultSet.next()){
                    int bookId = resultSet.getInt(1);
                    String bookTitle = resultSet.getString(2);
                    String bookAuthor = resultSet.getString(3);
                    boolean bookIsBorrowed = resultSet.getBoolean(4);
                    listOfAllBooks.add(new Book(bookId,bookTitle,bookAuthor,bookIsBorrowed));
                }
                return listOfAllBooks;

            } catch (SQLException ex) {
                ex.printStackTrace();
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
