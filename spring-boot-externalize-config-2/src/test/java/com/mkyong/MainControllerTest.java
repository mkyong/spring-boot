package com.mkyong;

import com.mkyong.global.EmailServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringBootTest
// @AutoConfigureMockMvc

@WebMvcTest(MainController.class)
@ComponentScan(basePackageClasses = {EmailServer.class})
public class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testEmailServer() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/email")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.server", is("smtp.gmail.com")))
                .andExpect(jsonPath("$.port", is(467)))
                .andExpect(jsonPath("$.username", is("hello@gmail.com")));
    }

    @Test
    public void testEmailServer2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/email2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.server", is("smtp.gmail.com")))
                .andExpect(jsonPath("$.port", is(467)))
                .andExpect(jsonPath("$.username", is("hello@gmail.com")));
    }
}
