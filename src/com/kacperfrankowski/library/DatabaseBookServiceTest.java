package com.kacperfrankowski.library;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseBookServiceTest {

    private String mySqlUrl = "jdbc:mysql://localhost/library?serverTimezone=UTC";
    private String username = "root";
    private String password = "135elo99";
    private Connection connection;
    private Statement statement;

    @BeforeEach
    public void init() throws SQLException {
        connection = DriverManager.getConnection(mySqlUrl,username,password);
        connection.setAutoCommit(false);
    }


    @Test
    @DisplayName("Test functionality adding book to the database")
    public void testAddingBookToDatabase() throws SQLException {

        statement = connection.createStatement();

        // Check if record exist in the database
        String sql = "SELECT * FROM books WHERE title = 'TestTitle' AND author = 'TestAuthor'";
        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println(resultSet);
        assertFalse(resultSet.next());

        // If no exist add to the database
        sql = "INSERT INTO books (title,author,borrowed) VALUES ('TestTitle','TestAuthor',false)";
        statement.executeUpdate(sql);

        // Check again if record now exist in the database
        sql = "SELECT * FROM books WHERE title = 'TestTitle' AND author = 'TestAuthor'";
        resultSet = statement.executeQuery(sql);
        assertTrue(resultSet.next());

    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.rollback();
        connection.close();
    }


}