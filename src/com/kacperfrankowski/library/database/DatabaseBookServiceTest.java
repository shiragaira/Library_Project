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
            assertFalse(checkIfInDatabaseByTitle(testTitle, testAuthor, statement));

            // If no exist add to the database
            databaseBookService.addBook(new Book(testTitle, testAuthor));

            // Check again if record now exist in the database
            assertTrue(checkIfInDatabaseByTitle(testTitle, testAuthor, statement));
        }
    }

    @Test
    @DisplayName("Test functionality removing book from database")
    public void testRemovingBookFromDatabase() throws SQLException {

        // Testing record (-1,"testedByIdTitle","testedByIdAuthor, false)
        int testId = -1;

        try (Statement statement = connection.createStatement()) {

            // Check if record exist in the database
            assertTrue(checkIfInDatabaseById(testId, statement));

            // If exist try remove it from database
            databaseBookService.deleteBook(testId);

            // Check again if record now doesn't exist in the database
            assertFalse(checkIfInDatabaseById(testId, statement));
        }
    }

    @Test
    @DisplayName("Test functionality getting one book from database")
    public void testGettingOneBookFromDatabase() throws SQLException {

        // Testing book with data from existing record in Database
        Book testingBook = new Book(-1, "testByIdTitle", "testByIdAuthor", false);

        try (Statement statement = connection.createStatement()) {
            Book bookFromDatabase = databaseBookService.getOneBook(testingBook.getId());
            // If book exist check all variables
            if (bookFromDatabase != null) {
                assertEquals(bookFromDatabase, testingBook);
            }
            // If book doesn't exist check if is null
            else {
                assertNull(bookFromDatabase);
            }
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

    public boolean checkIfInDatabaseByTitle(String testTitle, String testAuthor, Statement stmt) throws SQLException {
        String sql = "SELECT * FROM books WHERE title = '" + testTitle + "' AND author = '" + testAuthor + "' AND borrowed = false";
        try (ResultSet resultSet = stmt.executeQuery(sql)) {
            return resultSet.next();
        }
    }

    public boolean checkIfInDatabaseById(int testId, Statement stmt) throws SQLException {
        String sql = "SELECT * FROM books WHERE id = " + testId;
        try (ResultSet resultSet = stmt.executeQuery(sql)) {
            return resultSet.next();
        }
    }


}