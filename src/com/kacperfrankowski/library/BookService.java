package com.kacperfrankowski.library;

import java.util.List;

public interface BookService {

    void addBook(Book book);

    void deleteBook(int id);

    Book getOneBook(int id);

    List<Book> getAllBooks();

    void editBook(int id, Book book);

    void close();
}
