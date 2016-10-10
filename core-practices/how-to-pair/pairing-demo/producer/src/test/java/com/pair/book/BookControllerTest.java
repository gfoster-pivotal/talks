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
        assertThat(book.getTitle()).isEqualToIgnoringCase("foo");
        assertThat(book.getIsbn()).isEqualToIgnoringCase("bar");
    }

//    @Test
//    public void aSingleBook_getAListOfBooks_validContract() throws Exception {
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books");
//        String actual = mockMvc.perform(requestBuilder)
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        String expected = "[{\"title\":\"foo\",\"isbn\":\"bar\", \"author\":\"baz\"}]";
//        JSONAssert.assertEquals(expected, actual, false);
//    }

}
