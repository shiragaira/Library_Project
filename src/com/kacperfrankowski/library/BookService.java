package com.kacperfrankowski.library;

public interface BookService {

    void addBook(Book book);

    void deleteBook(int id);

    void selectBook(int id);

    void close();
}
