package com.mkyong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {

        // convert to List
        /*Iterable<Book> booksIterable = bookRepository.findAll();
        List<Book> booksList = new ArrayList<>();
        booksIterable.forEach(booksList::add);*/
        return bookRepository.findAll();

    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findByPublishedDateAfter(LocalDate date) {
        return bookRepository.findByPublishedDateAfter(date);
    }
}
