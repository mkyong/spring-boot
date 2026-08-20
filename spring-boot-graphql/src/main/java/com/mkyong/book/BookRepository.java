package com.mkyong.book;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository // Tells Spring to make one of these and keep it
public class BookRepository {

    // A growable list of books. Starts with three
    private final List<Book> books = new ArrayList<>(List.of(
            new Book("book-1", "Effective Java", 416, "author-1"),
            new Book("book-2", "Java Concurrency in Practice", 432, "author-2"),
            new Book("book-3", "Clean Code", 464, "author-3")
    ));

    private final List<Author> authors = List.of(
            new Author("author-1", "Joshua", "Bloch"),
            new Author("author-2", "Brian", "Goetz"),
            new Author("author-3", "Robert", "Martin")
    );

    // Used to build the next id, like "book-4"
    private final AtomicInteger counter = new AtomicInteger(3);

    // Find one book, or nothing if the id is unknown
    public Optional<Book> findBookById(String id) {
        return books.stream()
                .filter(book -> book.id().equals(id))
                .findFirst();
    }

    // Return every book
    public List<Book> findAllBooks() {
        return List.copyOf(books);
    }

    // Find books whose name contains the keyword, ignoring upper and lower case
    public List<Book> searchBooks(String keyword) {
        return books.stream()
                .filter(book -> book.name().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    // Find many authors in one go, using their ids
    public List<Author> findAuthorsByIds(List<String> ids) {
        System.out.println("Loading authors for ids: " + ids); // watch this line later
        return authors.stream()
                .filter(author -> ids.contains(author.id()))
                .toList();
    }

    // Add a new book and hand it back
    public Book save(String name, int pageCount, String authorId) {
        Book book = new Book("book-" + counter.incrementAndGet(), name, pageCount, authorId);
        books.add(book);
        return book;
    }
}
