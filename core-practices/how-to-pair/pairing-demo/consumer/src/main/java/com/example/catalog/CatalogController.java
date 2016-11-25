package com.example.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class CatalogController {
    private final String bookApiUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public CatalogController(@Value("${com.example.book.api}") String bookApiUrl, RestTemplate restTemplate) {
        this.bookApiUrl = bookApiUrl;
        this.restTemplate = restTemplate;
    }

    @RequestMapping(path = "/catalogs", method = RequestMethod.GET)
    public Catalog catalog() {
        ResponseEntity<List> responseEntity = restTemplate.exchange(bookApiUrl, HttpMethod.GET, null, List.class);
        List<Map> books = responseEntity.getBody();
        Set<String> authors = books
                .stream()
                .map(book -> {
                    if (book.containsKey("author")) {
                        Map<String, String> author = (Map<String, String>) book.get("author");
                        return author.get("firstName").toString() + " " + author.get("lastName").toString();
                    } else {
                        return book.get("firstName").toString() + " " + book.get("lastName").toString();
                    }
                })
                .collect(Collectors.toSet());
        return new Catalog(authors, "Bucktown Library");
    }

    public String getBookApiUrl() {
        return bookApiUrl;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
