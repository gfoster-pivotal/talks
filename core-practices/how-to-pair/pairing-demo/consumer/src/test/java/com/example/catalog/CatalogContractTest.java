package com.example.catalog;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.toomuchcoding.jsonassert.JsonAssertion;
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

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
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

    @Value("${producer.contract.locations}")
    String locations;

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

//    @Test
//    public void validResponseFromTheBookProducer_callTheBookProducerApi_catalogReturnsASpecificContract() throws Exception {
//        FileSystemResource fileSystemResource = new FileSystemResource(location);
//        Path inputPath = FileSystems
//                .getDefault()
//                .getPath(fileSystemResource.getPath());
//        byte[] bytes = Files.readAllBytes(inputPath);
//
//        server.expect(manyTimes(), requestTo(catalogController.getBookApiUrl()))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withSuccess(bytes, MediaType.APPLICATION_JSON));
//
//        FileSystemResource outputFileSystemResource = new FileSystemResource("src/test/resources/contracts/output.json");
//        Path outputPath = FileSystems.getDefault()
//                .getPath(outputFileSystemResource.getPath());
//        String expected = new String(Files.readAllBytes(outputPath), Charset.forName("UTF-8"));
//        String actual = mockMvc.perform(get("/catalogs"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        JSONAssert.assertEquals(expected, actual, true);
//    }

    @Test
    public void validResponsesFromTheBookProducer_callTheCatalogConsumerApi_catalogReturnsASpecificContract() throws Exception {
        File folder = new File(locations);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {

            // given
            Path inputPath = FileSystems
                    .getDefault()
                    .getPath(file.getPath());
            byte[] bytes = Files.readAllBytes(inputPath);

            server = MockRestServiceServer.bindTo(catalogController.getRestTemplate())
                    .build();
            server.expect(manyTimes(), requestTo(catalogController.getBookApiUrl()))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withSuccess(bytes, MediaType.APPLICATION_JSON));

            // when
            System.out.println("validating file = " + file);
            String actual = mockMvc.perform(get("/catalogs"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            // then
            DocumentContext documentContext = JsonPath.parse(actual);
            assertThatJson(documentContext).field("libraryName").isEqualTo("Bucktown Library");
            assertThatJson(documentContext).field("authors").array().contains("Herman Melville");
        }
    }
}
