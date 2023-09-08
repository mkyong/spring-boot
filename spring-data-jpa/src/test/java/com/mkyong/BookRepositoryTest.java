package com.mkyong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// Default, JPA tests data are transactional and roll back at the end of each test.
@DataJpaTest
public class BookRepositoryTest {

    // Alternative for EntityManager
    // Optional in this case, we can use bookRepository to do the same stuff
    @Autowired
    private TestEntityManager testEM;
    @Autowired
    private BookRepository bookRepository;

    // Need clean up if the MainApplication CommandLineRunner bean inserted some data
    // empty table.
    @BeforeEach
    void cleanup() {
        bookRepository.deleteAll();
        bookRepository.flush();
        testEM.clear();
    }

    @Test
    public void testSave() {

        Book b1 = new Book("Book A", BigDecimal.valueOf(9.99), LocalDate.of(2023, 8, 31));

        //testEM.persistAndFlush(b1); the same
        bookRepository.save(b1);

        Long savedBookID = b1.getId();

        Book book = bookRepository.findById(savedBookID).orElseThrow();
        // Book book = testEM.find(Book.class, savedBookID);

        assertEquals(savedBookID, book.getId());
        assertEquals("Book A", book.getTitle());
        assertEquals(BigDecimal.valueOf(9.99), book.getPrice());
        assertEquals(LocalDate.of(2023, 8, 31), book.getPublishDate());


    }

    @Test
    public void testUpdate() {

        Book b1 = new Book("Book A", BigDecimal.valueOf(9.99), LocalDate.of(2023, 8, 31));

        //testEM.persistAndFlush(b1);
        bookRepository.save(b1);

        // update price from 9.99 to 19.99
        b1.setPrice(BigDecimal.valueOf(19.99));

        bookRepository.save(b1);

        List<Book> result = bookRepository.findByTitle("Book A");

        assertEquals(1, result.size());

        Book book = result.get(0);
        assertNotNull(book.getId());
        assertTrue(book.getId() > 0);

        assertEquals("Book A", book.getTitle());
        assertEquals(BigDecimal.valueOf(19.99), book.getPrice());
        assertEquals(LocalDate.of(2023, 8, 31), book.getPublishDate());


    }

    @Test
    public void testFindByTitle() {

        Book b1 = new Book("Book A", BigDecimal.valueOf(9.99), LocalDate.of(2023, 8, 31));
        bookRepository.save(b1);

        List<Book> result = bookRepository.findByTitle("Book A");

        assertEquals(1, result.size());
        Book book = result.get(0);
        assertNotNull(book.getId());
        assertTrue(book.getId() > 0);

        assertEquals("Book A", book.getTitle());
        assertEquals(BigDecimal.valueOf(9.99), book.getPrice());
        assertEquals(LocalDate.of(2023, 8, 31), book.getPublishDate());


    }

    @Test
    public void testFindAll() {

        Book b1 = new Book("Book A", BigDecimal.valueOf(9.99), LocalDate.of(2023, 8, 31));
        Book b2 = new Book("Book B", BigDecimal.valueOf(19.99), LocalDate.of(2023, 7, 31));
        Book b3 = new Book("Book C", BigDecimal.valueOf(29.99), LocalDate.of(2023, 6, 10));
        Book b4 = new Book("Book D", BigDecimal.valueOf(39.99), LocalDate.of(2023, 5, 5));

        bookRepository.saveAll(List.of(b1, b2, b3, b4));

        List<Book> result = bookRepository.findAll();
        assertEquals(4, result.size());

    }

    @Test
    public void testFindByPublishedDateAfter() {

        Book b1 = new Book("Book A", BigDecimal.valueOf(9.99), LocalDate.of(2023, 8, 31));
        Book b2 = new Book("Book B", BigDecimal.valueOf(19.99), LocalDate.of(2023, 7, 31));
        Book b3 = new Book("Book C", BigDecimal.valueOf(29.99), LocalDate.of(2023, 6, 10));
        Book b4 = new Book("Book D", BigDecimal.valueOf(39.99), LocalDate.of(2023, 5, 5));

        bookRepository.saveAll(List.of(b1, b2, b3, b4));

        List<Book> result = bookRepository.findByPublishedDateAfter(LocalDate.of(2023, 7, 1));
        assertEquals(2, result.size());

    }

    @Test
    public void testDeleteById() {

        Book b1 = new Book("Book A", BigDecimal.valueOf(9.99), LocalDate.of(2023, 8, 31));
        bookRepository.save(b1);

        Long savedBookID = b1.getId();

        // Book book = bookRepository.findById(savedBookID).orElseThrow();
        // Book book = testEM.find(Book.class, savedBookID);

        bookRepository.deleteById(savedBookID);

        Optional<Book> result = bookRepository.findById(savedBookID);
        assertTrue(result.isEmpty());

    }

}