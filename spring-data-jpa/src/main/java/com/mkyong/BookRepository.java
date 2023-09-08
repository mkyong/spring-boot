package com.mkyong;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    //CRUD implementation created at runtime, beauty of Spring Data JPA

    // match the book field name
    List<Book> findByTitle(String title);

    // Custom Query
    @Query("SELECT b FROM Book b WHERE b.publishDate > :date")
    List<Book> findByPublishedDateAfter(@Param("date") LocalDate date);

}
