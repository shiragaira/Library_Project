package com.kacperfrankowski.library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    @DisplayName("Check if two identical Book objects are equals")
    void checkBookIsEquals() {
        Book book1 = new Book(1, "TestTitle", "TestAuthor");
        Book book2 = new Book(1, "TestTitle", "TestAuthor");
        assertEquals(book2, book1);
    }

    @Test
    @DisplayName("Check if two different Book objects are not equals")
    void checkBookIsNotEquals() {
        Book book1 = new Book(1, "TestTitle", "TestAuthor");
        Book book2 = new Book(2, "TestTitle", "TestAuthor");
        assertNotEquals(book2, book1);
    }

}