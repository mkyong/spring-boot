package com.mkyong.sp;

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

@Component
public class StoredFunction {

    private static final Logger log = LoggerFactory.getLogger(StoredFunction.class);

    @Autowired
    @Qualifier("jdbcBookRepository")
    private BookRepository bookRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallFunction1;
    private SimpleJdbcCall simpleJdbcCallFunction2;

    // init SimpleJdbcCall
    @PostConstruct
    public void init() {

        jdbcTemplate.setResultsMapCaseInsensitive(true);

        simpleJdbcCallFunction1 = new SimpleJdbcCall(jdbcTemplate).withFunctionName("get_price_by_id");
        simpleJdbcCallFunction2 = new SimpleJdbcCall(jdbcTemplate).withFunctionName("get_database_time");
    }

    private static final String SQL_STORED_FUNCTION_1 = ""
            + " CREATE OR REPLACE FUNCTION get_price_by_id(p_id IN BOOKS.ID%TYPE) "
            + " RETURN NUMBER"
            + " IS o_price BOOKS.PRICE%TYPE;"
            + " BEGIN"
            + "  SELECT PRICE INTO o_price from BOOKS WHERE ID = p_id;"
            + "  RETURN(o_price);"
            + " END;";

    private static final String SQL_STORED_FUNCTION_2 = ""
            + " CREATE OR REPLACE FUNCTION get_database_time "
            + " RETURN VARCHAR2"
            + " IS o_date VARCHAR2(20);"
            + " BEGIN"
            + "  SELECT TO_CHAR(SYSDATE, 'DD-MON-YYYY HH:MI:SS') INTO o_date FROM dual;"
            + "  RETURN(o_date);"
            + " END;";

    public void start() {

        log.info("Creating Store Procedures and Function...");
        jdbcTemplate.execute(SQL_STORED_FUNCTION_1);
        jdbcTemplate.execute(SQL_STORED_FUNCTION_2);

        /* Test Stored Function 1 */
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id", 3L);
        BigDecimal price = simpleJdbcCallFunction1.executeFunction(BigDecimal.class, in);
        System.out.println(price);  // 37.3

        /* Test Stored Function 2 */
        String database_time = simpleJdbcCallFunction2.executeFunction(String.class);
        System.out.println(database_time); // e.g current date, 23-JUL-2019 05:08:44

    }

}
