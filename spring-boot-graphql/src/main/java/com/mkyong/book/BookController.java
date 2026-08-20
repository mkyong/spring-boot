package com.mkyong.book;

import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookRepository repository;

    // Spring hands you the repository
    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    // Handles the "bookById" field under Query
    // The method name must match the field name in the schema
    @QueryMapping
    public Book bookById(@Argument String id) {
        // @Argument grabs the "id" the client sent in
        return repository.findBookById(id).orElse(null);
    }

    // Handles the "allBooks" field under Query
    @QueryMapping
    public List<Book> allBooks() {
        return repository.findAllBooks();
    }

    // Handles the "searchBooks" field under Query
    @QueryMapping
    public List<Book> searchBooks(@Argument String keyword) {
        return repository.searchBooks(keyword);
    }

    // Handles the "addBook" field under Mutation
    @MutationMapping
    public Book addBook(@Argument BookInput bookInput) {
        return repository.save(bookInput.name(), bookInput.pageCount(), bookInput.authorId());
    }

    // Handles the "author" field inside Book
    // Spring works out the type from the Book parameter and the field from the method name
    /*@SchemaMapping
    public Author author(Book book) {
        return repository.findAuthorsByIds(List.of(book.authorId()))
                .stream()
                .findFirst()
                .orElse(null);
    }*/

    // Spring hands you every Book it needs an author for, all together
    // You hand back a Map: which book goes with which author
    @BatchMapping
    public Map<Book, Author> author(List<Book> books) {

        // Collect all the author ids you need
        List<String> authorIds = books.stream()
                .map(Book::authorId)
                .distinct()
                .toList();

        // One trip to fetch them all
        Map<String, Author> authorsById = repository.findAuthorsByIds(authorIds)
                .stream()
                .collect(Collectors.toMap(Author::id, Function.identity()));

        // Pair each book with its author
        Map<Book, Author> result = new HashMap<>();
        for (Book book : books) {
            result.put(book, authorsById.get(book.authorId()));
        }
        return result;
    }

}
