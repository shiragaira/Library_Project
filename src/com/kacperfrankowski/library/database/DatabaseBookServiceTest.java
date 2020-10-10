package com.kacperfrankowski.library.database;

import com.kacperfrankowski.library.Book;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseBookServiceTest {

    private static Connection connection;
    private static final DatabaseBookService databaseBookService = new DatabaseBookService();

    @BeforeAll
    static void init() throws SQLException {
        connection = databaseBookService.getConnection();
        connection.setAutoCommit(false);
    }


    @Test
    @DisplayName("Test functionality adding book to the database")
    public void testAddingBookToDatabase() throws SQLException {
        try (Statement statement = connection.createStatement()) {

            String testTitle = "TestTitle";
            String testAuthor = "TestAuthor";

            // Check if record exist in the database
            assertFalse(checkIfInDatabase(testTitle, testAuthor, statement));

            // If no exist add to the database
            databaseBookService.addBook(new Book(testTitle, testAuthor));

            // Check again if record now exist in the database
            assertTrue(checkIfInDatabase(testTitle, testAuthor, statement));

        }

    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.rollback();
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        connection.close();
    }

    public boolean checkIfInDatabase(String testTitle, String testAuthor, Statement stmt) throws SQLException {

        String sql = "SELECT * FROM books WHERE title = '" + testTitle + "' AND author = '" + testAuthor + "' AND borrowed = false";
        try (ResultSet resultSet = stmt.executeQuery(sql)) {
            ResultSet rs = stmt.executeQuery(sql);
            return rs.next();
        }

    }


}