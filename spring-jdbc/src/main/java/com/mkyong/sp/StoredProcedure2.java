package com.mkyong.sp;

import com.mkyong.Book;
import com.mkyong.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class StoredProcedure2 {

    private static final Logger log = LoggerFactory.getLogger(StoredProcedure2.class);

    @Autowired
    @Qualifier("jdbcBookRepository")
    private BookRepository bookRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;

    // init SimpleJdbcCall
    @PostConstruct
    public void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

        // Convert o_c_book SYS_REFCURSOR to List<Book>
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("get_book_by_name")
                .returningResultSet("o_c_book",
                        BeanPropertyRowMapper.newInstance(Book.class));

    }

    private static final String SQL_STORED_PROC_REF = ""
            + " CREATE OR REPLACE PROCEDURE get_book_by_name "
            + " ("
            + "  p_name IN BOOKS.NAME%TYPE,"
            + "  o_c_book OUT SYS_REFCURSOR"
            + " ) AS"
            + " BEGIN"
            + "  OPEN o_c_book FOR"
            + "  SELECT * FROM BOOKS WHERE NAME LIKE '%' || p_name || '%';"
            + " END;";

    public void start() {

        log.info("Creating Store Procedures and Function...");
        jdbcTemplate.execute(SQL_STORED_PROC_REF);

        /* Test Stored Procedure RefCursor */
        List<Book> books = findBookByName("Java");

        // Book{id=1, name='Thinking in Java', price=46.32}
        // Book{id=2, name='Mkyong in Java', price=1.99}
        books.forEach(x -> System.out.println(x));

    }

    List<Book> findBookByName(String name) {

        SqlParameterSource paramaters = new MapSqlParameterSource()
                .addValue("p_name", name);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("o_c_book");
        }

    }


}
