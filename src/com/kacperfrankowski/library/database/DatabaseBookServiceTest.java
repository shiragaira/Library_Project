package com.kacperfrankowski.library.database;

import com.kacperfrankowski.library.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseBookServiceTest {

    private Connection connection;
    private Statement statement;
    private final DatabaseBookService databaseBookService = new DatabaseBookService();

    @BeforeEach
    public void init() throws SQLException {
        connection = databaseBookService.getConnection();
        connection.setAutoCommit(false);
    }


    @Test
    @DisplayName("Test functionality adding book to the database")
    public void testAddingBookToDatabase() throws SQLException {

        String testTitle = "TestTitle";
        String testAuthor = "TestAuthor";

        statement = connection.createStatement();

        // Check if record exist in the database
        String sql = "SELECT * FROM books WHERE title = '" + testTitle + "' AND author = '" + testAuthor + "' AND borrowed = false";
        ResultSet resultSet = statement.executeQuery(sql);
        assertFalse(resultSet.next());

        // If no exist add to the database
        databaseBookService.addBook(new Book(testTitle, testAuthor));

        // Check again if record now exist in the database
        sql = "SELECT * FROM books WHERE title = '" + testTitle + "' AND author = '" + testAuthor + "' AND borrowed = false";
        resultSet = statement.executeQuery(sql);
        assertTrue(resultSet.next());

        resultSet.close();
        statement.close();

    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.rollback();
        connection.close();
    }


}