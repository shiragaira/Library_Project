package com.kacperfrankowski.library;

import com.kacperfrankowski.library.GUI.Gui;

public class Main {

    public static void main(String[] args) {

        BookService bookService = new DatabaseBookService();
        Gui gui = new Gui(bookService);
        gui.printMenu();

    }
}
