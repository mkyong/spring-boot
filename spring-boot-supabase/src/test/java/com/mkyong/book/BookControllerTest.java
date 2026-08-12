package com.mkyong.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class) // Load only this controller
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc; // A fake browser

    @MockitoBean // A fake repository. In Spring Boot 4 this replaced @MockBean
    private BookRepository repository;

    @Test
    void getAll_returnsJsonArray() throws Exception {

        Book book = new Book("Java 25 Basics", "Mkyong");

        // Tell the fake repository what to answer
        when(repository.findAll()).thenReturn(List.of(book));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Java 25 Basics"));
    }

    @Test
    void post_returns201() throws Exception {

        Book book = new Book("New Book", "Mkyong");
        when(repository.save(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Book\",\"author\":\"Mkyong\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    void getOne_missingBook_returns404() throws Exception {

        // The fake repository finds nothing
        when(repository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_returns204() throws Exception {

        when(repository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());

        // Check the controller really asked to delete row 1
        verify(repository).deleteById(1L);
    }
}
