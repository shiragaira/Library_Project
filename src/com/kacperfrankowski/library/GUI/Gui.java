package com.kacperfrankowski.library.GUI;

import com.kacperfrankowski.library.BookService;
import com.kacperfrankowski.library.Book;

import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
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
        chooseAction(getUserAction());
    }

    public void chooseAction(String userChoice){
        switch(userChoice){
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
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Book's title: ");
            String bookTitle = bufferedReader.readLine();
            System.out.println("Book's author: ");
            String bookAuthor = bufferedReader.readLine();
            Book book = new Book(bookTitle, bookAuthor);
            bookService.addBook(book);
            System.out.println("Book has been added");
        } catch (IOException ex){
            ex.getStackTrace();
        }
    }

    public void deleteBook() {
        boolean inputIsValid = false;
        while (!inputIsValid) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Book's id to delete: ");
                int bookIdToDelete = Integer.parseInt(bufferedReader.readLine());
                System.out.println("Are you sure? (yes/no): ");
                String confirm = bufferedReader.readLine();
                if (confirm.equals("yes")) {
                    bookService.deleteBook(bookIdToDelete);
                } else {
                    System.out.println("Book hasn't deleted");
                }
                inputIsValid = true;
                bufferedReader.close();
            } catch (NumberFormatException ex) {
                System.out.println("It isn't valid ID!");
            } catch (IOException ex) {
                System.out.println("Data reading error!");
            }

        }
    }


    public void getOneBook(){
        boolean inputIsValid = false;
        while(!inputIsValid) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Which book do you want to select, put id: ");
                int idBook = Integer.parseInt(bufferedReader.readLine());
                Book book = bookService.getOneBook(idBook);
                if(book != null){
                    System.out.println("TITLE: " + book.getTitle() + "\nAUTHOR: " + book.getAuthor() + "\nBORROWED: " + book.isBorrowed());
                    inputIsValid = true;
                    bufferedReader.close();
                } else {
                    System.out.println("Book hasn't been found");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (NumberFormatException ex) {
                System.out.println("It isn't valid ID");
            }
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
