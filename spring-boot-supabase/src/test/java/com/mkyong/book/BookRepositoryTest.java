package com.mkyong.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Load only the JPA parts, not the whole app
// Do NOT swap in a fake in-memory database, use the real Supabase one
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Test
    void save_thenFindById_returnsTheBook() {

        // Write a new row
        Book saved = repository.save(new Book("Test Driven Java", "Mkyong"));

        // Postgres gave it a number
        assertThat(saved.getId()).isNotNull();

        // Read it back
        Book found = repository.findById(saved.getId()).orElseThrow();
        assertThat(found.getTitle()).isEqualTo("Test Driven Java");
    }

    @Test
    void findByAuthor_returnsOnlyThatAuthor() {

        repository.save(new Book("Clean Maven", "Mkyong"));
        repository.save(new Book("Some Other Book", "Someone Else"));

        List<Book> books = repository.findByAuthor("Mkyong");

        // Every book that came back belongs to Mkyong
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(b -> b.getAuthor().equals("Mkyong"));
    }

}
