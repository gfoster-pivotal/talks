package com.pair.book;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


public class BookControllerTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookController bookController;

    MockMvc mockMvc;

    @Before
    public void setUpMockMVC() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(bookController)
                .build();
    }

    @Test
    public void is_this_a_good_test() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle("Moby Dick");
        bookEntity.setIsbn("0553213113");
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(bookEntity));

        BookController.Book book = bookController.getBooks().get(0);
        assertThat(book.getTitle()).isEqualToIgnoringCase("Moby Dick");
        assertThat(book.getIsbn()).isEqualToIgnoringCase("0553213113");
    }

    @Test
    public void could_this_be_a_good_test() {
//        Date date = bookController.getBooks().get(0).getDate();
//        assertThat(date).isNull();
    }

    @Test
    public void what_about_this_test() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setFirstName("Herman");
        bookEntity.setLastName("Melville");
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(bookEntity));

        BookController.Author author = bookController.getBooks().get(0).getAuthor();
        assertThat(author.getFirstName()).isEqualToIgnoringCase("Herman");
        assertThat(author.getLastName()).isEqualToIgnoringCase("Melville");
    }
}
