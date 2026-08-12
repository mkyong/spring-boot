package com.mkyong.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring writes findAll(), findById(), save(), deleteById() for you
public interface BookRepository extends JpaRepository<Book, Long> {

    // Spring reads this name and builds the SQL: where author = ?
    List<Book> findByAuthor(String author);
}
