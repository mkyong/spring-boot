package com.mkyong;

import com.mkyong.book.Book;
import com.mkyong.book.BookController;
import com.mkyong.book.BookRepository;
import com.mkyong.book.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Testing BookController with mocking
@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookRepository bookRepository;

    @MockBean
    BookService bookService;

    private List<Book> books;

    private Page<Book> bookPage;
    private PageRequest pageRequest;
    private PageRequest pageRequestWithSorting;

    @BeforeEach
    void setUp() {

        Book b1 = new Book(1L, "Book A",
                BigDecimal.valueOf(9.99),
                LocalDate.of(2023, 8, 31));
        Book b2 = new Book(2L, "Book B",
                BigDecimal.valueOf(19.99),
                LocalDate.of(2023, 7, 31));
        Book b3 = new Book(3L, "Book C",
                BigDecimal.valueOf(29.99),
                LocalDate.of(2023, 6, 10));
        Book b4 = new Book(4L, "Book D",
                BigDecimal.valueOf(39.99),
                LocalDate.of(2023, 5, 5));
        Book b5 = new Book(5L, "Book E",
                BigDecimal.valueOf(49.99),
                LocalDate.of(2023, 4, 1));
        Book b6 = new Book(6L, "Book F",
                BigDecimal.valueOf(59.99),
                LocalDate.of(2023, 3, 1));

        books = List.of(b1, b2, b3, b4, b5, b6);

        bookPage = new PageImpl<>(books);
        pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "name"));

    }

    @Test
    void testFindAllDefault() throws Exception {

        when(bookService
                .findAll(0, 10, "id", "ASC"))
                .thenReturn(bookPage);

        ResultActions result = mockMvc.perform(get("/books"));

        result.andExpect(status().isOk()).andDo(print());

        verify(bookService, times(1)).findAll(0, 10, "id", "ASC");

    }

    @Test
    void testFindAllDefault2() throws Exception {

        when(bookService
                .findAll(0, 10, "id", "asc"))
                .thenReturn(bookPage);

        ResultActions result = mockMvc
                .perform(get("/books?pageNo=0&pageSize=10&sortBy=id&sortDirection=asc"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$.[*].title",
                        containsInAnyOrder("Book A", "Book B", "Book C",
                                "Book D", "Book E", "Book F")))
                .andDo(print());

    }

}