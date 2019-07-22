package com.mkyong.misc;

import com.mkyong.Book;
import com.mkyong.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/*
    CREATE OR REPLACE PROCEDURE get_book_by_id(
        p_id IN BOOKS.ID%TYPE,
        o_name OUT BOOKS.NAME%TYPE,
        o_price OUT BOOKS.PRICE%TYPE)
    AS
    BEGIN

        SELECT NAME , PRICE INTO o_name, o_price from BOOKS WHERE ID = p_id;

    END;
 */

@Component
public class StoreProcedureTest {

    private static final Logger log = LoggerFactory.getLogger(StoreProcedureTest.class);

    @Autowired
    @Qualifier("namedParameterJdbcBookRepository")
    private BookRepository bookRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    SimpleJdbcCall simpleJdbcCall;

    //@Autowired
    //DataSource dataSource;

    @PostConstruct
    public void init() {
        // o_name and O_NAME, same
        // jdbcTemplate.setResultsMapCaseInsensitive(true);
        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("get_book_by_id");
    }

    private static final String SQL_CREATE_TABLE = "CREATE TABLE BOOKS"
            + "("
            + " ID number GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),"
            + " NAME varchar2(100) NOT NULL,"
            + " PRICE number(15, 2) NOT NULL,"
            + " CONSTRAINT book_pk PRIMARY KEY (ID)"
            + ")";

    private static final String SQL_STORED_PROC = "CREATE OR REPLACE PROCEDURE get_book_by_id "
            + "("
            + " p_id IN BOOKS.ID%TYPE,"
            + " o_name OUT BOOKS.NAME%TYPE,"
            + " o_price OUT BOOKS.PRICE%TYPE)"
            + " AS"
            + " BEGIN"
            + " SELECT NAME, PRICE INTO o_name, o_price from BOOKS WHERE ID = p_id;"
            + " END;";


    public void runStoredProc() {

        log.info("StoreProcedureTest...");

        log.info("Creating tables for testing...1");

        jdbcTemplate.execute("DROP TABLE BOOKS");
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

        log.info("Creating stored procedure for testing...");

        jdbcTemplate.execute(SQL_STORED_PROC);

        Book book = findById(2L);
        System.out.println(book);

    }

    Book findById(Long id) {

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id", id);

        Map out = simpleJdbcCall.execute(in);

        Book book = new Book();
        book.setId(id);
        book.setName((String) out.get("O_NAME"));
        book.setPrice((BigDecimal) out.get("O_PRICE"));

        return book;
    }
}
