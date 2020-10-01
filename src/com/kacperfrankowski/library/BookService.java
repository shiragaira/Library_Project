package com.kacperfrankowski.library;

import java.util.List;

public interface BookService {

    void addBook(Book book);

    void deleteBook(int id);

    Book selectBook(int id);

    List<Book> selectAllBooks();

    void close();
}
