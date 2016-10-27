package com.pair.book;

import java.util.Date;

public class Book {
    private final String title;
    private final String isbn;
    private final Author author;
    private final Date date;

    public Book(String title, String isbn, Date date, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.date = date;
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

    public Date getDate() {
        return date;
    }
}
