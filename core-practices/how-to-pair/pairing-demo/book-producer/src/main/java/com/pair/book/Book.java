package com.pair.book;

import java.util.Date;

public class Book {
    private final String title;
    private final String isbn;
    private final Author author;

    public Book(String title, String isbn, Date date, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }
}
