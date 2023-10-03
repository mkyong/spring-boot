package com.mkyong.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book save(String title, BigDecimal price, LocalDate publishedDate) {

        Book book = new Book();
        book.setTitle(title);
        book.setPrice(price);
        book.setPublishDate(publishedDate);

        return bookRepository.save(book);

    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

}
