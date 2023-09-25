package com.mkyong;

import com.mkyong.book.Book;
import com.mkyong.book.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing with TestRestTemplate and @Testcontainers (image mysql:8.0-debian)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// activate automatic startup and stop of containers
@Testcontainers
// JPA drop and create table, good for testing
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
public class BookControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String BASEURI;

    @Autowired
    BookRepository bookRepository;

    // static, all tests share this postgres container
    @Container
    @ServiceConnection
    static MySQLContainer<?> postgres = new MySQLContainer<>(
            "mysql:8.0-debian"
    );

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

        bookRepository.saveAll(List.of(b1, b2, b3, b4));
    }

    @Test
    void testFindAll() {

        // ResponseEntity<List> response = restTemplate.getForEntity(BASEURI + "/books", List.class);

        // find all books and return List<Book>
        ParameterizedTypeReference<List<Book>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                BASEURI + "/books",
                HttpMethod.GET,
                null,
                typeRef
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(4, response.getBody().size());

    }

    @Test
    void testFindByTitle() {
        String title = "Book C";
        ParameterizedTypeReference<List<Book>> typeRef = new ParameterizedTypeReference<>() {
        };

        // find Book C
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                BASEURI + "/books/find/title/" + title,
                HttpMethod.GET,
                null,
                typeRef
        );

        // test response code
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Book> list = response.getBody();
        assert list != null;

        assertEquals(1, list.size());

        // Test Book C details
        Book book = list.get(0);
        assertEquals("Book C", book.getTitle());
        assertEquals(BigDecimal.valueOf(29.99), book.getPrice());
        assertEquals(LocalDate.of(2023, 6, 10), book.getPublishDate());

    }

    @Test
    void testFindByPublishedDateAfter() {

        String date = "2023-07-01";
        ParameterizedTypeReference<List<Book>> typeRef = new ParameterizedTypeReference<>() {
        };

        // find Book C
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                BASEURI + "/books/find/date-after/" + date,
                HttpMethod.GET,
                null,
                typeRef
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        // test list of objects
        List<Book> result = response.getBody();
        assert result != null;

        assertEquals(2, result.size());

        assertThat(result).extracting(Book::getTitle)
                .containsExactlyInAnyOrder(
                        "Book A", "Book B");
        assertThat(result).extracting(Book::getPrice)
                .containsExactlyInAnyOrder(
                        new BigDecimal("9.99"), new BigDecimal("19.99"));
        assertThat(result).extracting(Book::getPublishDate)
                .containsExactlyInAnyOrder
                        (LocalDate.parse("2023-08-31"), LocalDate.parse("2023-07-31"));
    }

    @Test
    public void testDeleteById() {

        List<Book> list = bookRepository.findByTitle("Book A");
        Book bookA = list.get(0);

        // get Book A id
        Long id = bookA.getId();

        // delete by id
        ResponseEntity<Void> response = restTemplate.exchange(
                BASEURI + "/books/" + id,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        // test 204
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // find Book A again, ensure no result
        List<Book> listAgain = bookRepository.findByTitle("Book A");
        assertEquals(0, listAgain.size());

    }

    @Test
    public void testCreate() {

        // Create a new Book E
        Book newBook = new Book("Book E", new BigDecimal("9.99"), LocalDate.parse("2023-09-14"));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<Book> request = new HttpEntity<>(newBook, headers);

        // test POST save
        ResponseEntity<Book> responseEntity =
                restTemplate.postForEntity(BASEURI + "/books", request, Book.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // find Book E
        List<Book> list = bookRepository.findByTitle("Book E");

        // Test Book E details
        Book book = list.get(0);
        assertEquals("Book E", book.getTitle());
        assertEquals(BigDecimal.valueOf(9.99), book.getPrice());
        assertEquals(LocalDate.of(2023, 9, 14), book.getPublishDate());

    }

    /**
     * Book b4 = new Book("Book D",
     * BigDecimal.valueOf(39.99),
     * LocalDate.of(2023, 5, 5));
     */
    @Test
    public void testUpdate() {
        // Find Book D
        Book bookD = bookRepository.findByTitle("Book D").get(0);
        Long id = bookD.getId();

        // Update the book details
        bookD.setTitle("Book DDD");
        bookD.setPrice(new BigDecimal("199.99"));
        bookD.setPublishDate(LocalDate.of(2024, 1, 31));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // put the updated book in HttpEntity
        HttpEntity<Book> request = new HttpEntity<>(bookD, headers);

        // Perform the PUT request to update the book
        ResponseEntity<Book> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/books",
                HttpMethod.PUT,
                request,
                Book.class
        );

        // ensure OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // verify the updated book
        Book updatedBook = bookRepository.findById(id).orElseThrow();

        assertEquals(id, updatedBook.getId());
        assertEquals("Book DDD", updatedBook.getTitle());
        assertEquals(BigDecimal.valueOf(199.99), updatedBook.getPrice());
        assertEquals(LocalDate.of(2024, 1, 31), updatedBook.getPublishDate());

    }

}