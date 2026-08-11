package com.mkyong.book;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    // Two books kept in memory
    private final List<Book> books = List.of(
            new Book(1L, "Java 25 Basics", "Mkyong"),
            new Book(2L, "Spring Boot 4 Basics", "Mkyong")
    );

    // GET /books/1
    @GetMapping("/{id}")
    public Book findById(@PathVariable Long id) {
        return books.stream()
                .filter(book -> book.id().equals(id)) // keep the matching book
                .findFirst()                          // take the first one
                .orElse(null);
    }
}
