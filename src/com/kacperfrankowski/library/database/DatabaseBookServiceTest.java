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

        String testTitle = "testTitle";
        String testAuthor = "testAuthor";

        try (Statement statement = connection.createStatement()) {

            // Check if record exist in the database
            assertFalse(checkIfInDatabaseByTitleAuthorAndBorrowed(testTitle, testAuthor, statement));

            // If no exist add to the database
            databaseBookService.addBook(new Book(testTitle, testAuthor));

            // Check again if record now exist in the database
            assertTrue(checkIfInDatabaseByTitleAuthorAndBorrowed(testTitle, testAuthor, statement));
        }
    }

    @Test
    @DisplayName("Test functionality removing book from database")
    public void testRemovingBookFromDatabase() throws SQLException {

        int testId = -1;

        try (Statement statement = connection.createStatement()) {

            // Add book to the database
            statement.executeUpdate("INSERT INTO books (id,title,author,borrowed) VALUES (" + testId + ",'testTitle','testAuthor',false)");

            // Check if book exist now
            assertTrue(checkIfInDatabaseById(testId, statement));

            // If exist try remove it from database
            databaseBookService.deleteBook(testId);

            // Check again if record now doesn't exist in the database
            assertFalse(checkIfInDatabaseById(testId, statement));
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

    private boolean checkIfInDatabaseByTitleAuthorAndBorrowed(String testTitle, String testAuthor, Statement stmt) throws SQLException {
        String sql = "SELECT * FROM books WHERE title = '" + testTitle + "' AND author = '" + testAuthor + "' AND borrowed = false";
        try (ResultSet resultSet = stmt.executeQuery(sql)) {
            return resultSet.next();
        }
    }

    private boolean checkIfInDatabaseById(int testId, Statement stmt) throws SQLException {
        String sql = "SELECT * FROM books WHERE id = " + testId;
        try (ResultSet resultSet = stmt.executeQuery(sql)) {
            return resultSet.next();
        }
    }


}