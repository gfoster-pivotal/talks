package com.example.catalog;

import java.util.Collection;

public class Catalog {
    private final Collection<String> authors;
    private final String libraryName;

    public Catalog(Collection<String> authors, String libraryName) {
        this.authors = authors;
        this.libraryName = libraryName;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public Collection<String> getAuthors() {
        return authors;
    }
}
