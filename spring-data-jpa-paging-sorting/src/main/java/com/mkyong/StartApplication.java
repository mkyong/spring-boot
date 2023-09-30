package com.mkyong;

import com.mkyong.book.Book;
import com.mkyong.book.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class StartApplication {

    private static final Logger log = LoggerFactory.getLogger(StartApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Autowired
    BookRepository bookRepository;

    @Bean
    public CommandLineRunner startup() {

        return args -> {

            Book b1 = new Book("Book A",
                    BigDecimal.valueOf(9.99),
                    LocalDate.of(2023, 8, 31));
            Book b2 = new Book("Book B",
                    BigDecimal.valueOf(19.99),
                    LocalDate.of(2023, 7, 31));
            Book b3 = new Book("Book C",
                    BigDecimal.valueOf(29.99),
                    LocalDate.of(2023, 6, 10));
            Book b4 = new Book("Book D",
                    BigDecimal.valueOf(39.99),
                    LocalDate.of(2023, 5, 5));
            Book b5 = new Book("Book E",
                    BigDecimal.valueOf(49.99),
                    LocalDate.of(2023, 4, 1));
            Book b6 = new Book("Book F",
                    BigDecimal.valueOf(59.99),
                    LocalDate.of(2023, 3, 1));

            bookRepository.saveAll(List.of(b1, b2, b3, b4, b5, b6));

        };

    }
}