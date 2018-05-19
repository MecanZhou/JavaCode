package com.gsafety.bean;

public class Book {

    private int bookPage;

    public Book() {
    }

    public int getBookPage() {
        return bookPage;
    }

    public void setBookPage(int bookPage) {
        this.bookPage = bookPage;
    }

    public Book(int bookPage) {

        this.bookPage = bookPage;
    }
}
