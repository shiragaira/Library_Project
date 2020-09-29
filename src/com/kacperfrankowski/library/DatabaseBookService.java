package com.kacperfrankowski.library;

import java.sql.*;
import java.util.Scanner;


public class DatabaseBookService implements BookService {

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public DatabaseBookService(){
         try {
             connection = DriverManager.getConnection("jdbc:mysql://localhost/library?serverTimezone=UTC", "root", "135elo99");
             System.out.println("Has been connected to database");
         } catch (SQLException ex) {
             System.out.println(ex.getMessage());
         }
    }


        public void addBook(Book book) {

            try {
                String sql = "INSERT INTO books (title,author,borrowed) VALUES (?,?,false)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,book.getTitle());
                preparedStatement.setString(2,book.getAuthor());

                int rows = preparedStatement.executeUpdate();
                if (0 < rows) {
                    System.out.println("Has been added to DB");
                }

                preparedStatement.close();

            } catch (SQLException ex){
                System.out.println(ex.getMessage());
            }

        }

        public void deleteBook(int id){
            try {
                String sql = "DELETE FROM books WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,id);

                int rows = preparedStatement.executeUpdate();
                if (0 < rows) {
                    System.out.println("Has been deleted");
                }

                preparedStatement.close();

            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }

        public void selectBook(int id){
            try {
                String sql = "SELECT id,title,author,borrowed FROM books WHERE id=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,id);
                resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) {
                    int idBook = resultSet.getInt(1);
                    String titleBook = resultSet.getString(2);
                    String authorBook = resultSet.getString(3);
                    Book book = new Book(idBook, titleBook, authorBook);
                    System.out.println("ID: " + book.getId() + " TITLE: " + book.getTitle() + " AUTHOR: " + book.getAuthor());
                } else {
                    System.out.println("No matched books");
                }

            }  catch (SQLException ex){
                System.out.println("ERROR: " + ex.getMessage());
            }
        }

}
