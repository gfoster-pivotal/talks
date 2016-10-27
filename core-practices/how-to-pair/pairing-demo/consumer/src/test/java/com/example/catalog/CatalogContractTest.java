package com.example.catalog;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.client.ExpectedCount.manyTimes;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class CatalogContractTest {
    @Value("${producer.contract.location}")
    String location;

    @Autowired
    CatalogController catalogController;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    MockRestServiceServer server;

    @Before
    public void setUpMockMvc() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Before
    public void setUpMockRestServer() throws Exception {
        server = MockRestServiceServer.bindTo(catalogController.getRestTemplate())
                .build();
    }

    @Test
    public void doStuff() throws Exception {
        System.out.println("location = " + location);
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        FileSystemResource fileSystemResource = new FileSystemResource(location);
        Path inputPath = FileSystems
                .getDefault()
                .getPath(fileSystemResource.getPath());
        byte[] bytes = Files.readAllBytes(inputPath);

        server.expect(manyTimes(), requestTo(catalogController.getBookApiUrl()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(bytes, MediaType.APPLICATION_JSON));

        FileSystemResource outputFileSystemResource = new FileSystemResource("src/test/resources/contracts/output.json");
        Path outputPath = FileSystems.getDefault()
                .getPath(outputFileSystemResource.getPath());
        String expected = new String(Files.readAllBytes(outputPath), Charset.forName("UTF-8"));
        String actual = mockMvc.perform(get("/catalogs"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(expected, actual, true);
    }
}
