package com.pair.book;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

        FileSystemResource fileSystemResource = new FileSystemResource("src/test/resources/contracts/output.json");
        File file = fileSystemResource.getFile();
        System.out.println("file = " + file);
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(contentAsString);
        out.close();
    }
}
