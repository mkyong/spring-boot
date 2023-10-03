package com.mkyong;

import com.mkyong.book.Book;
import com.mkyong.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

//@Component
/*@ConditionalOnProperty(
        name = "db.init.enabled",
        havingValue = "true",
        matchIfMissing = false
)*/
//@Profile("dev")
//@ConditionalOnBean(BookController.class)
//@ConditionalOnMissingBean(BookController.class)
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    BookRepository bookRepository;

    //@Autowired
    //private Environment env;

    @Override
    public void run(String... args) {

        /*if ("true".equals(env.getProperty("db.init.enabled"))) {
            System.out.println("This runs when 'db.init.enabled' property is true.");
        }*/

        bookRepository.save(
                new Book("Book A",
                        BigDecimal.valueOf(9.99),
                        LocalDate.of(1982, 8, 31))
        );

        System.out.println("Database initialized!");

    }
}
