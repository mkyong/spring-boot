package com.mkyong;

import com.mkyong.book.Book;
import com.mkyong.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

//@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    BookRepository bookRepository;

    @Override
    public void run(String... args) {

        bookRepository.save(
                new Book("Book A",
                        BigDecimal.valueOf(9.99),
                        LocalDate.of(1982, 8, 31))
        );

        System.out.println("Database initialized!");

    }
}
