package com.mkyong;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mkyong.model.Author;
import com.mkyong.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// too heavy to load entire spring context, uses @WebMvcTest
//@SpringBootTest
//@AutoConfigureMockMvc

@WebMvcTest(WebController.class)
public class WebControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testHello() throws Exception {

        mvc.perform(get("/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // has field name "$title" with a value of "Hello World"
                .andExpect(jsonPath("$.title").value("Hello World"));

    }

    /**
     * {
     * "id" : 1,
     * "title" : "Modern Java in Action",
     * "tags" : [ "Java", "Java 8" ],
     * "authors" : [ {
     * "id" : 1,
     * "name" : "Raoul-Gabriel Urma",
     * "phoneNo" : "111-1111111"
     * }, {
     * "id" : 2,
     * "name" : "Mario Fusco",
     * "phoneNo" : "222-2222222"
     * }, {
     * "id" : 3,
     * "name" : "Alan Mycroft",
     * "phoneNo" : "333-3333333"
     * } ],
     * "publishedDate" : "2018-11-15",
     * "meta" : {
     * "isbn-10" : "1617293563",
     * "isbn-13" : "978-1617293566"
     * }
     * }
     */
    @Test
    public void testBook() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/book")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.title", is("Modern Java in Action")))
                .andExpect(jsonPath("$.bookName").doesNotExist())
                .andExpect(jsonPath("$.bookName").doesNotExist())
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags", hasSize(2)))
                .andExpect(jsonPath("$.tags", hasItem("Java"))) // order not fix, check with contains
                .andExpect(jsonPath("$.tags", hasItem("Java 8")))
                .andExpect(jsonPath("$.publishedDate", is(LocalDate.of(2018, 11, 15).toString())))
                .andExpect(jsonPath("$.authors", hasSize(3)))
                .andExpect(jsonPath("$.meta.isbn-10", is("1617293563")))
                .andExpect(jsonPath("$.meta.isbn-13", is("978-1617293566")))
                // better convert to list of objects and test it, see below testBookAuthor
                .andExpect(jsonPath("$.authors[*].id", hasItem(1)))
                .andExpect(jsonPath("$.authors[*].id", containsInAnyOrder(3, 1, 2)))
                .andExpect(jsonPath("$.authors[*].name", hasItem("Raoul-Gabriel Urma")))
                .andExpect(jsonPath("$.authors[*].phoneNo", hasItem("111-1111111")));

                /*.andExpect(jsonPath("$.authors[0].id").value(1))  // first author of the book
                .andExpect(jsonPath("$.authors[0].name").value("Raoul-Gabriel Urma"))
                .andExpect(jsonPath("$.authors[0].phoneNo").value("111-1111111"))

                .andExpect(jsonPath("$.authors[1].id").value(2))    // second author of the book
                .andExpect(jsonPath("$.authors[2].name").value("Mario Fusco"))
                .andExpect(jsonPath("$.authors[3].phoneNo").value("222-2222222")
                );*/

    }

    // Better convert to list of objects and test it
    @Test
    public void testBookAuthor() throws Exception {

        MvcResult mvcResult = mvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        // supports Java 8 date time
        mapper.registerModule(new JavaTimeModule());

        Book book = mapper.readValue(mvcResult.getResponse().getContentAsString(), Book.class);

        List<Author> authors = book.getAuthors();

        Author obj1 = new Author(1L, "Raoul-Gabriel Urma", "111-1111111");
        Author obj2 = new Author(2L, "Mario Fusco", "222-2222222");
        Author obj3 = new Author(3L, "Alan Mycroft", "333-3333333");

        assertThat(authors.size(), is(3));

        assertThat(authors, hasItem(obj1));
        assertThat(authors, hasItem(obj2));
        assertThat(authors, hasItem(obj3));

        // need exactly list item but in any order
        assertThat(authors, containsInAnyOrder(obj3, obj1, obj2));

    }


    /**
     * [
     * "Java",
     * "React",
     * "JavaScript"
     * ]
     */
    @Test
    public void testList() throws Exception {

        mvc.perform(get("/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // $ refer to root element
                .andExpect(jsonPath("$", hasSize(3)))
                // $[0] refer to first element of the list
                .andExpect(jsonPath("$[0]").value("Java"))
                .andExpect(jsonPath("$[1]").value("React"))
                .andExpect(jsonPath("$[2]").value("JavaScript"))

                // normally list order is not fix, better use hasItem
                // if contains a specific value
                .andExpect(jsonPath("$", hasItem("React")));

    }

    /**
     * {
     * "key1": "a",
     * "key2": "b",
     * "key3": "c"
     * }
     */
    @Test
    public void testMap() throws Exception {

        mvc.perform(get("/map")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key1").value("a"))
                .andExpect(jsonPath("$.key2").value("b"))
                .andExpect(jsonPath("$.key3").value("c"));

        // Deserialize and assert to test the map size, is there a better way?
        MvcResult result = mvc.perform(get("/map")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // convert JSON to Map object
        String content = result.getResponse().getContentAsString();
        Map<String, Object> resultMap = new ObjectMapper().readValue(content, new TypeReference<>() {
        });

        assertEquals(3, resultMap.size());

    }

}
