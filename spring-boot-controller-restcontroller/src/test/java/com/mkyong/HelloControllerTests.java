package com.mkyong;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

// Old (Spring Boot 3.x)
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

// New (Spring Boot 4.x)
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void welcome_ok() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello World, Spring Boot!")));
    }

    @Test
    public void hello_page_ok() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("hello"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("<title>Hello Page</title>")))
                .andExpect(content().string(containsString("Hello World from Spring Boot!")));
    }

}
