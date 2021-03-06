package com.pair.book;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookControllerContractTest {
    @Value("${book.api.version}")
    String apiVersion;

    @Autowired
    BookController bookController;

    @Autowired
    private WebApplicationContext context;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void writeProducerContract() throws Exception {
        String contentAsString = mockMvc.perform(get("/books"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String path = "src/test/resources/contracts/books-contract-" + apiVersion + ".json";
        FileSystemResource fileSystemResource = new FileSystemResource(path);
        BufferedWriter out = new BufferedWriter(new FileWriter(fileSystemResource.getFile()));
        out.write(contentAsString);
        out.close();

        path = "src/test/resources/contracts/output.json";
        fileSystemResource = new FileSystemResource(path);
        out = new BufferedWriter(new FileWriter(fileSystemResource.getFile()));
        out.write(contentAsString);
        out.close();
    }
}
