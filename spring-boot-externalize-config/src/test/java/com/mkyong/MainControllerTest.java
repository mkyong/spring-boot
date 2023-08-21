package com.mkyong;

import com.mkyong.global.AppProperties;
import com.mkyong.global.GlobalProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringBootTest
// @AutoConfigureMockMvc

@WebMvcTest(MainController.class)
@EnableConfigurationProperties({AppProperties.class, GlobalProperties.class})
public class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testGlobalProperties() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/global")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // has field name `threadPool` of with value of 5
                .andExpect(jsonPath("$.threadPool", is(5)))
                .andExpect(jsonPath("$.email", is("test@mkyong.com")));
    }

    @Test
    public void testAppProperties() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error", is("/error/")))
                .andExpect(jsonPath("$.compiler.timeout", is("5")))
                .andExpect(jsonPath("$.compiler.outputFolder", is("/temp/")))
                .andExpect(jsonPath("$.menus[0].title", is("Home")))
                .andExpect(jsonPath("$.menus[1].title", is("Login")));
    }

}
