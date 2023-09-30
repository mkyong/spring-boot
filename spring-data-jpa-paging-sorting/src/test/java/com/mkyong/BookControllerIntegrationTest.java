package com.mkyong;

import com.mkyong.book.Book;
import com.mkyong.book.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Integration test using TestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String BASEURI;

    @Autowired
    BookRepository bookRepository;

    // pre-populated with books for test
    @BeforeEach
    void testSetUp() {

        BASEURI = "http://localhost:" + port;

        bookRepository.deleteAll();

        Book b1 = new Book("Book A",
                BigDecimal.valueOf(9.99),
                LocalDate.of(2023, 8, 31));
        Book b2 = new Book("Book B",
                BigDecimal.valueOf(19.99),
                LocalDate.of(2023, 7, 31));
        Book b3 = new Book("Book C",
                BigDecimal.valueOf(29.99),
                LocalDate.of(2023, 6, 10));
        Book b4 = new Book("Book D",
                BigDecimal.valueOf(39.99),
                LocalDate.of(2023, 5, 5));
        Book b5 = new Book("Book E",
                BigDecimal.valueOf(49.99),
                LocalDate.of(2023, 4, 1));
        Book b6 = new Book("Book F",
                BigDecimal.valueOf(59.99),
                LocalDate.of(2023, 3, 1));

        bookRepository.saveAll(List.of(b1, b2, b3, b4, b5, b6));
    }

    @Test
    void testFindAllWithPagingAndSorting() {

        ParameterizedTypeReference<List<Book>> typeRef = new ParameterizedTypeReference<>() {
        };

        // sort by price, desc, get page 0 , size = 4
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                BASEURI + "/books?pageNo=0&pageSize=4&sortBy=title&sortDirection=desc",
                HttpMethod.GET,
                null,
                typeRef
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        // test list of objects
        List<Book> result = response.getBody();
        assert result != null;

        assertEquals(4, result.size());

        // Get Book C, D, E, F
        assertThat(result).extracting(Book::getTitle)
                .containsExactlyInAnyOrder(
                        "Book C", "Book D", "Book E", "Book F");
        assertThat(result).extracting(Book::getPrice)
                .containsExactlyInAnyOrder(
                        new BigDecimal("59.99"),
                        new BigDecimal("49.99"),
                        new BigDecimal("39.99"),
                        new BigDecimal("29.99")
                );


        // sort by price, desc, get page 1 , size = 4
        ResponseEntity<List<Book>> response2 = restTemplate.exchange(
                BASEURI + "/books?pageNo=1&pageSize=4&sortBy=title&sortDirection=desc",
                HttpMethod.GET,
                null,
                typeRef
        );

        assertEquals(HttpStatus.OK, response2.getStatusCode());

        // test list of objects
        List<Book> result2 = response2.getBody();
        assert result2 != null;

        assertEquals(2, result2.size());

        // Get Book A, B
        assertThat(result2).extracting(Book::getTitle)
                .containsExactlyInAnyOrder(
                        "Book A", "Book B");
        assertThat(result2).extracting(Book::getPrice)
                .containsExactlyInAnyOrder(
                        new BigDecimal("9.99"),
                        new BigDecimal("19.99")
                );

    }

}