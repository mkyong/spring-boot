package com.mkyong.book;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerOldWayTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    /* static, all testes share this container */
    /**
     * postgres:15-alpine
     * PostgreSQL version 15 using the lightweight Alpine Linux as the base image
     */
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    public void testBookEndpoints() {

        // Create a new book
        Book book = new Book();
        book.setName("Is Java Dead?");
        book.setIsbn("111-111");

        ResponseEntity<Book> createResponse =
                restTemplate.postForEntity("/books", book, Book.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        Book savedBook = createResponse.getBody();

        assert savedBook != null;

        // Retrieve
        ResponseEntity<Book> getResponse =
                restTemplate.getForEntity("/books/" + savedBook.getId(), Book.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());

        Book bookFromGet = getResponse.getBody();

        assert bookFromGet != null;

        assertEquals("Is Java Dead?", bookFromGet.getName());
        assertEquals("111-111", bookFromGet.getIsbn());

        // Retrieve All
        ResponseEntity<Book[]> getAllResponse =
                restTemplate.getForEntity("/books", Book[].class);
        assertEquals(HttpStatus.OK, getAllResponse.getStatusCode());

        Book[] bookFromGetAll = getAllResponse.getBody();
        assert bookFromGetAll != null;

        assertEquals(1, bookFromGetAll.length);
    }

}