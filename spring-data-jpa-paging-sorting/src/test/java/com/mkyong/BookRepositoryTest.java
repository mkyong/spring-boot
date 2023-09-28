package com.mkyong;

import com.mkyong.book.Book;
import com.mkyong.book.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {

        bookRepository.deleteAll();

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

        bookRepository.saveAllAndFlush(List.of(b1, b2, b3, b4, b5, b6));
    }


    @Test
    public void testFindAll_Paging_Sorting() {

        // page 1, size 4, sort by title, desc
        Sort sort = Sort.by(Sort.Direction.DESC, "title");
        Pageable pageable = PageRequest.of(0, 4, sort);

        Page<Book> result = bookRepository.findAll(pageable);

        List<Book> books = result.getContent();

        assertEquals(4, books.size());

        assertThat(result).extracting(Book::getTitle)
                .containsExactlyInAnyOrder(
                        "Book C", "Book D", "Book E", "Book F");

        // page 2, size 4, sort by title, desc
        Pageable pageable2 = PageRequest.of(1, 4, sort);

        Page<Book> result2 = bookRepository.findAll(pageable2);

        List<Book> books2 = result2.getContent();

        assertEquals(2, books2.size());

        assertThat(result2).extracting(Book::getTitle)
                .containsExactlyInAnyOrder(
                        "Book A", "Book B");
    }

}
