package com.mkyong.sp;

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
import java.util.Map;
import java.util.Optional;

@Component
public class StoredProcedure1 {

    private static final Logger log = LoggerFactory.getLogger(StoredProcedure1.class);

    @Autowired
    @Qualifier("jdbcBookRepository")
    private BookRepository bookRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCall;

    // init SimpleJdbcCall
    @PostConstruct
    void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("get_book_by_id");

    }

    private static final String SQL_STORED_PROC = ""
            + " CREATE OR REPLACE PROCEDURE get_book_by_id "
            + " ("
            + "  p_id IN BOOKS.ID%TYPE,"
            + "  o_name OUT BOOKS.NAME%TYPE,"
            + "  o_price OUT BOOKS.PRICE%TYPE"
            + " ) AS"
            + " BEGIN"
            + "  SELECT NAME, PRICE INTO o_name, o_price from BOOKS WHERE ID = p_id;"
            + " END;";


    public void start() {

        log.info("Creating Store Procedures and Function...");
        jdbcTemplate.execute(SQL_STORED_PROC);

        /* Test Stored Procedure */
        Book book = findById(2L).orElseThrow(IllegalArgumentException::new);
        
        // Book{id=2, name='Mkyong in Java', price=1.99}
        System.out.println(book);


    }

    Optional<Book> findById(Long id) {

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id", id);

        Optional result = Optional.empty();

        try {

            Map out = simpleJdbcCall.execute(in);

            if (out != null) {
                Book book = new Book();
                book.setId(id);
                book.setName((String) out.get("O_NAME"));
                book.setPrice((BigDecimal) out.get("O_PRICE"));
                result = Optional.of(book);
            }

        } catch (Exception e) {
            // ORA-01403: no data found, or any java.sql.SQLException
            System.err.println(e.getMessage());
        }

        return result;
    }

}
