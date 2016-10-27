package com.pair.book;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


public class BookControllerTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

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
        Book book = bookController.getBooks().get(0);
        assertThat(book.getTitle()).isEqualToIgnoringCase("Moby Dick");
        assertThat(book.getIsbn()).isEqualToIgnoringCase("0553213113");
    }

    @Test
    public void could_this_be_a_good_test() {
        Date date = bookController.getBooks().get(0).getDate();
        assertThat(date).isBetween(Date.from(Instant.ofEpochMilli(0)), Date.from(Instant.now()));
    }

    @Test
    public void what_about_this_test() {
        Author author = bookController.getBooks().get(0).getAuthor();
        assertThat(author.getFirstName()).isEqualToIgnoringCase("Herman");
        assertThat(author.getLastName()).isEqualToIgnoringCase("Melville");
    }
}
