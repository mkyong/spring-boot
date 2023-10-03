/*package com.mkyong;

import com.mkyong.book.Book;
import com.mkyong.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class StartApplication2 implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication2.class, args);
    }

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
}*/