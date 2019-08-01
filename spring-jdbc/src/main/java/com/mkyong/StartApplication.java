package com.mkyong;

import com.mkyong.customer.Customer;
import com.mkyong.customer.CustomerRepository;
import com.mkyong.repository.BookRepository;
import com.mkyong.sp.StoredFunction;
import com.mkyong.sp.StoredProcedure1;
import com.mkyong.sp.StoredProcedure2;
import com.mkyong.sp.TestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

// https://spring.io/guides/gs/relational-data-access/
// https://docs.spring.io/spring/docs/current/spring-framework-reference/data-access.html#jdbc
@SpringBootApplication
public class StartApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StartApplication.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    //@Qualifier("jdbcBookRepository")              // Test JdbcTemplate
    @Qualifier("namedParameterJdbcBookRepository")  // Test NamedParameterJdbcTemplate
    private BookRepository bookRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TestData storeProcedureTest;

    @Autowired
    StoredProcedure1 storedProcedure1;

    @Autowired
    StoredProcedure2 storedProcedure2;

    @Autowired
    StoredFunction storedFunction;

    @Autowired
    TestData testDate;

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Override
    public void run(String... args) {

        log.info("StartApplication...");

        // To test Stored Procedure and Function
        // Uncomment Oracle JDBC in pom.xml and define datasource in application.properties
        //testDate.start();
        //storedProcedure1.start();
        //storedProcedure2.start();
        //storedFunction.start();

        //startCustomerApp();

        startBookApp();

    }

    // Tested with H2 database
    void startCustomerApp() {

        jdbcTemplate.execute("DROP TABLE customer IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customer(" +
                "id SERIAL, name VARCHAR(255), age NUMERIC(2), created_date timestamp)");

        List<Customer> list = Arrays.asList(
                new Customer("Customer A", 19),
                new Customer("Customer B", 20),
                new Customer("Customer C", 21),
                new Customer("Customer D", 22)
        );

        list.forEach(x -> {
            log.info("Saving...{}", x.getName());
            customerRepository.save(x);
        });

        log.info("[FIND_BY_ID]");
        log.info("{}", customerRepository.findByCustomerId(1L));
        log.info("{}", customerRepository.findByCustomerId2(2L));
        log.info("{}", customerRepository.findByCustomerId3(3L));

        log.info("[FIND_ALL]");
        log.info("{}", customerRepository.findAll());
        log.info("{}", customerRepository.findAll2());
        log.info("{}", customerRepository.findAll3());
        log.info("{}", customerRepository.findAll4());

        log.info("[FIND_NAME_BY_ID]");
        log.info("{}", customerRepository.findCustomerNameById(4L));

        log.info("[COUNT]");
        log.info("{}", customerRepository.count());

    }

    void startBookApp() {

        log.info("Creating tables for testing...");

        jdbcTemplate.execute("DROP TABLE books IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE books(" +
                "id SERIAL, name VARCHAR(255), price NUMERIC(15, 2))");

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

        // count
        log.info("[COUNT] Total books: {}", bookRepository.count());

        // find all
        log.info("[FIND_ALL] {}", bookRepository.findAll());

        // find by id
        log.info("[FIND_BY_ID] :2L");
        Book book = bookRepository.findById(2L).orElseThrow(IllegalArgumentException::new);
        log.info("{}", book);

        // find by name (like) and price
        log.info("[FIND_BY_NAME_AND_PRICE] : like '%Java%' and price <= 10");
        log.info("{}", bookRepository.findByNameAndPrice("Java", new BigDecimal(10)));

        // get name (string) by id
        log.info("[GET_NAME_BY_ID] :1L = {}", bookRepository.getNameById(1L));

        // update
        log.info("[UPDATE] :2L :99.99");
        book.setPrice(new BigDecimal("99.99"));
        log.info("rows affected: {}", bookRepository.update(book));

        // delete
        log.info("[DELETE] :3L");
        log.info("rows affected: {}", bookRepository.deleteById(3L));

        // find all
        log.info("[FIND_ALL] {}", bookRepository.findAll());

    }

}