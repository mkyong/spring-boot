package com.mkyong;

import com.mkyong.model.Book;
import com.mkyong.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @DataJpaTest 1. It scans the `@Entity` classes and Spring Data JPA repositories.
 * 2. Set the `spring.jpa.show-sql` property to true and enable the SQL queries logging.
 * 3. Default, JPA test data are transactional and roll back at the end of each test;
 * it means we do not need to clean up saved or modified table data after each test.
 * 4. Replace the application DataSource, run and configure the embed database on classpath.
 */
@DataJpaTest
// We provide the `test containers` as DataSource, don't replace it.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//  activate automatic startup and stop of containers
@Testcontainers
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    // static, all tests share this
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @Test
    public void testSave() {

        Book b1 = new Book("Book A",
                BigDecimal.valueOf(9.99),
                LocalDate.of(2023, 8, 31));

        //testEM.persistAndFlush(b1); the same
        bookRepository.save(b1);

        Long savedBookID = b1.getId();

        Book book = bookRepository.findById(savedBookID).orElseThrow();

        assertEquals(savedBookID, book.getId());
        assertEquals("Book A", book.getTitle());
        assertEquals(BigDecimal.valueOf(9.99), book.getPrice());
        assertEquals(LocalDate.of(2023, 8, 31), book.getPublishDate());

    }

}