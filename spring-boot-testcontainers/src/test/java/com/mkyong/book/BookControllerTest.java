package com.mkyong.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class BookControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    // no need this, the @Testcontainers and @Container will auto start and stop the container.
    /*@BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }*/

    /**
     * The Testcontainers JUnit 5 Extension will take care of starting the container before tests and stopping it after tests.
     * If the container is a static field then it will be started once before all the tests and stopped after all the tests.
     * If it is a non-static field then the container will be started before each test and stopped after each test.
     */
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    /**
     * With Spring Boot 3.1, we use @ServiceConnection, no need @DynamicPropertySource
     */
    /*
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }*/

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