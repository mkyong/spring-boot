package com.mkyong.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// This extension writes the snippets for you
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
class BookControllerDocumentationTests {

    private MockMvc mockMvc;

    // Runs before every test
    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
               RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                // This one line switches REST Docs on
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    /*@Test
    void findBookById() throws Exception {

        this.mockMvc.perform(get("/books/1"))  // call the real endpoint
                .andExpect(status().isOk())    // check it worked
                .andDo(document("find-book",
                        // Explain every field that comes back
                        responseFields(
                                fieldWithPath("id").description("The book id"),
                                fieldWithPath("title").description("The name of the book"),
                                fieldWithPath("author").description("Who wrote it")
                        )));

    }*/

    @Test
    void findBookById() throws Exception {

        // Pass the template "/books/{id}" and the value 1 separately
        this.mockMvc.perform(get("/books/{id}", 1))
                .andExpect(status().isOk())
                .andDo(document("find-book",
                        pathParameters(
                                parameterWithName("id").description("The id of the book you want")
                        ),
                        responseFields(
                                fieldWithPath("id").description("The book id"),
                                fieldWithPath("title").description("The name of the book"),
                                fieldWithPath("author").description("Who wrote it")
                        )));
    }

}