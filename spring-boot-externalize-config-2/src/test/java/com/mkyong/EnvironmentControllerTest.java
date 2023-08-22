package com.mkyong;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringBootTest
// @AutoConfigureMockMvc

@WebMvcTest(EnvironmentController.class)
public class EnvironmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testEmailServerFromEnv() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/env")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.server", is("smtp.gmail.com")))
                .andExpect(jsonPath("$.port", is("467")))
                .andExpect(jsonPath("$.username", is("hello@gmail.com")));
    }

}
