package com.kacperfrankowski.library.GUI;

import com.kacperfrankowski.library.BookService;
import com.kacperfrankowski.library.Book;

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
        System.out.println("3. Edit book");
        System.out.println("======================");
        System.out.println("What you want to do: ");
        chooseAction();
    }

    public void chooseAction(){
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice){
            case 1:
                addBook();
                break;
            case 2:
                deleteBook();
                break;
            case 3:
                selectBook();
                break;
        }
    }

    public void addBook(){
        Scanner sc = new Scanner(System.in);
        String bookTitle = sc.nextLine();
        String bookAuthor = sc.nextLine();
        sc.close();
        Book book = new Book(bookTitle,bookAuthor);
        bookService.addBook(book);
    }

    public void deleteBook(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Put book's ID which you want delete");
        int bookToDelete = sc.nextInt();
        System.out.println("Are you sure? (yes/no)");
        String confirm = sc.next();
        sc.close();
        if (confirm.equals("yes")) {
            bookService.deleteBook(bookToDelete);
        } else {
            System.out.println("Book wasn't deleted");
        }
    }

    public void selectBook(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Which book do you want to select, put id: ");
        int idBook = sc.nextInt();
        sc.close();
        bookService.selectBook(idBook);
    }
}
