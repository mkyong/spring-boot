package com.mkyong;

import com.mkyong.model.Book;
import com.mkyong.repository.BookRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// activate automatic startup and stop of containers
@Testcontainers
// JPA drop and create table, good for testing
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
public class BookControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    BookRepository bookRepository;

    // static, all tests share this postgres container
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
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

        given()
                .contentType(ContentType.JSON)
                .when()
                    .get("/books")
                .then()
                    .statusCode(200)    // expecting HTTP 200 OK
                    .contentType(ContentType.JSON) // expecting JSON response content
                    .body(".", hasSize(4));

    }

    @Test
    void testFindByTitle() {

        String title = "Book C";

        given()
                //Returning floats and doubles as BigDecimal
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .contentType(ContentType.JSON)
                .pathParam("title", title)
                .when()
                    .get("/books/find/title/{title}")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body(
                        ".", hasSize(1),
                        "[0].title", equalTo("Book C"),
                        "[0].price", is(new BigDecimal("29.99")),
                        "[0].publishDate", equalTo("2023-06-10")
                );
    }

    @Test
    void testFindByPublishedDateAfter() {

        String date = "2023-07-01";

        Response result = given()
                //Returning floats and doubles as BigDecimal
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .contentType(ContentType.JSON)
                .pathParam("date", date)
                .when()
                    .get("/books/find/date-after/{date}")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body(
                        ".", hasSize(2),
                        "title", hasItems("Book A", "Book B"),
                        "price", hasItems(new BigDecimal("9.99"), new BigDecimal("19.99")),
                        "publishDate", hasItems("2023-08-31", "2023-07-31")
                    )
                .extract().response();

        // get the response and print it out
        System.out.println(result.asString());

    }


    @Test
    public void testDeleteById() {
        Long id = 1L; // replace with a valid ID
        given()
                .pathParam("id", id)
                .when()
                    .delete("/books/{id}")
                .then()
                    .statusCode(204); // expecting HTTP 204 No Content
    }

    @Test
    public void testCreate() {

        given()
                .contentType(ContentType.JSON)
                .body("{ \"title\": \"Book E\", \"price\": \"9.99\", \"publishDate\": \"2023-09-14\" }")
                .when()
                    .post("/books")
                .then()
                    .statusCode(201) // expecting HTTP 201 Created
                    .contentType(ContentType.JSON); // expecting JSON response content

        // find the new saved book
        given()
                //Returning floats and doubles as BigDecimal
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .contentType(ContentType.JSON)
                .pathParam("title", "Book E")
                .when()
                    .get("/books/find/title/{title}")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body(
                        ".", hasSize(1),
                        "[0].title", equalTo("Book E"),
                        "[0].price", is(new BigDecimal("9.99")),
                        "[0].publishDate", equalTo("2023-09-14")
                    );
    }

    /**
     * Book b4 = new Book("Book D",
     * BigDecimal.valueOf(39.99),
     * LocalDate.of(2023, 5, 5));
     */
    @Test
    public void testUpdate() {

        Book bookD = bookRepository.findByTitle("Book D").get(0);
        System.out.println(bookD);

        Long id = bookD.getId();

        bookD.setTitle("Book E");
        bookD.setPrice(new BigDecimal("199.99"));
        bookD.setPublishDate(LocalDate.of(2024, 1, 31));

        given()
                .contentType(ContentType.JSON)
                .body(bookD)
                .when()
                    .put("/books")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON);

        // get the updated book
        Book updatedBook = bookRepository.findById(id).orElseThrow();
        System.out.println(updatedBook);

        assertEquals(id, updatedBook.getId());
        assertEquals("Book E", updatedBook.getTitle());
        assertEquals(new BigDecimal("199.99"), updatedBook.getPrice());
        assertEquals(LocalDate.of(2024, 1, 31), updatedBook.getPublishDate());
        
    }


}