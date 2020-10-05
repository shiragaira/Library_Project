package com.kacperfrankowski.library.GUI;

import com.kacperfrankowski.library.BookService;
import com.kacperfrankowski.library.Book;

import java.util.List;
import java.util.Scanner;

public class Gui {

    private BookService bookService;

    public Gui(BookService bookService){
        this.bookService = bookService;
    }

    public void printMenu(){
        System.out.println("Welcome in the Library");
        System.out.println("======================");
        System.out.println("1. Add new book");
        System.out.println("2. Remove book");
        System.out.println("3. Select book"); // This will be change to "editBook()"
        System.out.println("4. List of books");
        System.out.println("5. Exit");
        System.out.println("======================");
        System.out.println("What you want to do: ");
        chooseAction();
    }

    public void chooseAction(){
        switch(getUserAction()){
            case "1":
                addBook();
                break;
            case "2":
                deleteBook();
                break;
            case "3":
                getOneBook();
                break;
            case "4":
                getAllBooks();
                break;
            case "5":
                close();
                break;
        }
    }

    public String getUserAction(){
        Scanner sc = new Scanner(System.in);
        while(!sc.hasNext("[12345]")) {
            System.out.println("Invalid action");
            sc.next();
        }
        return sc.next();
    }

    public void addBook(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Book's title: ");
        String bookTitle = sc.nextLine();
        System.out.println("Book's author: ");
        String bookAuthor = sc.nextLine();
        bookService.addBook(new Book(bookTitle, bookAuthor));
        System.out.println("Book has been added");
    }

    public void deleteBook(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Book's ID to delete: > ");
        while(!sc.hasNextInt()) {
            System.out.println("Invalid ID");
            sc.next();
        }
        int bookIdToDelete = sc.nextInt();
        sc.nextLine();
        System.out.println("If you sure type \"yes\"");
        String confirm = sc.nextLine();
        if (confirm.equals("yes")) {
            bookService.deleteBook(bookIdToDelete);
            System.out.println("Book has been deleted");
        }
    }

    public void getOneBook(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Which book do you want to select by ID: > ");
        while(!sc.hasNextInt()){
            System.out.println("Invalid ID");
            sc.next();
        }
        int bookId = sc.nextInt();
        Book book = bookService.getOneBook(bookId);
        if (book != null){
            System.out.println("TITLE: " + book.getTitle() + "\nAUTHOR: " + book.getAuthor() + "\nBORROWED: " + book.isBorrowed());
        }
        else {
            System.out.println("Book has not been found");
        }
    }

    public void getAllBooks(){
        List<Book> listOfAllBooks = bookService.getAllBooks();
        listOfAllBooks.forEach(book -> System.out.println(book.getId() + " | " +  book.getTitle() + " | " + book.getAuthor() + " | " + book.isBorrowed()));
    }

    public void close(){
        bookService.close();
        System.out.println("Goodbye");
    }
}
