package com.mkyong.sp;

import com.mkyong.Book;
import com.mkyong.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class TestData {

    private static final Logger log = LoggerFactory.getLogger(TestData.class);

    @Autowired
    @Qualifier("jdbcBookRepository")
    private BookRepository bookRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_CREATE_TABLE = ""
            + " CREATE TABLE BOOKS"
            + " ("
            + "  ID number GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),"
            + "  NAME varchar2(100) NOT NULL,"
            + "  PRICE number(15, 2) NOT NULL,"
            + "  CONSTRAINT book_pk PRIMARY KEY (ID)"
            + " )";

    public void start() {
        createTestData(true);
    }

    void createTestData(boolean dropTable) {

        log.info("Creating tables for testing...1");

        if (dropTable) {
            jdbcTemplate.execute("DROP TABLE BOOKS");
        }

        jdbcTemplate.execute(SQL_CREATE_TABLE);

        List<Book> books = Arrays.asList(
                new Book("Thinking in Java", new BigDecimal("46.32")),
                new Book("Mkyong in Java", new BigDecimal("1.99")),
                new Book("Getting Clojure", new BigDecimal("37.3")),
                new Book("Head First Android Development", new BigDecimal("41.19"))
        );

        log.info("[SAVE]");
        books.forEach(book -> {
            log.info("Saving...{}", book.getName());
            bookRepository.save(book);
        });

    }

}
