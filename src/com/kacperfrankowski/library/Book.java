package com.kacperfrankowski.library;

public class Book {

    //  Object that we return when we suppose it will be null;
    public static Book NOT_FOUND = new Book("Book not found","Unknown");

    private int id;
    private String title;
    private String author;
    private boolean borrowed;

    public Book(String title, String author){
        this.title = title;
        this.author = author;
        this.borrowed = false;
    }

    public Book(int id, String title, String author){
        this(id,title,author,false);
    }

    public Book(int id, String title, String author, boolean borrowed){
        this.id = id;
        this.title = title;
        this.author = author;
        this.borrowed = borrowed;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }


}
