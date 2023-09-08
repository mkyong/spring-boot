package com.mkyong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@SpringBootApplication
public class MainApplication {

    private static final Logger log = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    // Spring runs CommandLineRunner bean when Spring Boot App starts
    @Bean
    public CommandLineRunner demo(BookRepository bookRepository) {
        return (args) -> {

            Book b1 = new Book("Book A", BigDecimal.valueOf(9.99), LocalDate.of(2023, 8, 31));
            Book b2 = new Book("Book B", BigDecimal.valueOf(19.99), LocalDate.of(2023, 7, 31));
            Book b3 = new Book("Book C", BigDecimal.valueOf(29.99), LocalDate.of(2023, 6, 10));
            Book b4 = new Book("Book D", BigDecimal.valueOf(39.99), LocalDate.of(2023, 5, 5));

            // save a few books, ID auto increase, expect 1, 2, 3, 4
            bookRepository.save(b1);
            bookRepository.save(b2);
            bookRepository.save(b3);
            bookRepository.save(b4);

            // find all books
            log.info("findAll(), expect 4 books");
            log.info("-------------------------------");
            for (Book book : bookRepository.findAll()) {
                log.info(book.toString());
            }
            log.info("\n");

            // find book by ID
            Optional<Book> optionalBook = bookRepository.findById(1L);
            optionalBook.ifPresent(obj -> {
                log.info("Book found with findById(1L):");
                log.info("--------------------------------");
                log.info(obj.toString());
                log.info("\n");
            });

            // find book by title
            log.info("Book found with findByTitle('Book B')");
            log.info("--------------------------------------------");
            bookRepository.findByTitle("Book C").forEach(b -> {
                log.info(b.toString());
                log.info("\n");
            });

            // find book by published date after
            log.info("Book found with findByPublishedDateAfter(), after 2023/7/1");
            log.info("--------------------------------------------");
            bookRepository.findByPublishedDateAfter(LocalDate.of(2023, 7, 1)).forEach(b -> {
                log.info(b.toString());
                log.info("\n");
            });

            // delete a book
            bookRepository.deleteById(2L);
            log.info("Book delete where ID = 2L");
            log.info("--------------------------------------------");
            // find all books
            log.info("findAll() again, expect 3 books");
            log.info("-------------------------------");
            for (Book book : bookRepository.findAll()) {
                log.info(book.toString());
            }
            log.info("\n");

        };
    }

}