package com.pair.book;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
public class BookController {
    @RequestMapping(path = "/books")
    public List<Book> getBooks() {
        return Collections.singletonList(new Book("Moby Dick", "0553213113", new Author("Herman", "Melville")));
    }
}
