package com.mkyong.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/books") // Every URL here starts with /api/books
public class BookController {

    private final BookRepository repository;

    // Spring hands you the repository
    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    // CREATE - POST /api/books
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Send 201 instead of 200
    public Book create(@RequestBody Book book) {
        return repository.save(book); // INSERT
    }

    // READ ALL - GET /api/books
    @GetMapping
    public List<Book> findAll() {
        return repository.findAll(); // SELECT
    }

    // READ ONE - GET /api/books/1
    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> notFound(id));
    }

    // UPDATE - PUT /api/books/1
    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book input) {

        // Grab the old row first
        Book book = repository.findById(id)
                .orElseThrow(() -> notFound(id));

        // Change the fields
        book.setTitle(input.getTitle());
        book.setAuthor(input.getAuthor());

        return repository.save(book); // UPDATE
    }

    // DELETE - DELETE /api/books/1
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Send 204, no body
    public void delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw notFound(id);
        }
        repository.deleteById(id); // DELETE
    }

    // One place to build the 404 error
    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Book not found: " + id);
    }

}
