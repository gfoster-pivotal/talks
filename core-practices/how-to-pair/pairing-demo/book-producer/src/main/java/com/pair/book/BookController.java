package com.pair.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping(path = "/books")
    public List<Book> getBooks() {
        return bookRepository.findAll().stream()
                .map(bookEntity -> {
                    Author author = new Author(bookEntity.getFirstName(), bookEntity.getLastName());
                    return new Book(bookEntity.getTitle(), bookEntity.getIsbn(), author);
                })
                .sorted((o1, o2) -> o1.getAuthor().lastName.compareTo(o2.author.lastName))
                .collect(Collectors.toList());
    }

    protected static final class Author {
        private final String firstName;
        private final String lastName;

        public Author(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }

    protected static final class Book {
        private final String title;
        private final String isbn;
        private final Author author;

        public Book(String title, String isbn, Author author) {
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
}
