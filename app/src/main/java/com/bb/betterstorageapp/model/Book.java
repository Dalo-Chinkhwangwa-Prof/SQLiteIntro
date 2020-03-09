package com.bb.betterstorageapp.model;

public class Book {

    private String bookName;
    private int bookPages;
    private int bookId;

    public Book(String bookName, int bookPages, int bookId) {
        this.bookName = bookName;
        this.bookPages = bookPages;
        this.bookId = bookId;
    }

    public Book(String bookName, int bookPages) {
        this.bookName = bookName;
        this.bookPages = bookPages;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookPages() {
        return bookPages;
    }

    public void setBookPages(int bookPages) {
        this.bookPages = bookPages;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
