package com.mkyong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class BookRepositoryMockTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // init the mocks
    }

    @Test
    public void testSave() {

        Book book = new Book();
        book.setTitle("Hello Java");

        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.save(book);
        assertEquals("Hello Java", result.getTitle());

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testFindById() {

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Hello Java");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Hello Java", result.get().getTitle());

        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAll() {
        Book book1 = new Book();
        book1.setTitle("Book A");

        Book book2 = new Book();
        book2.setTitle("Book B");

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<Book> results = bookService.findAll();
        assertEquals(2, results.size());

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteById() {

        Long bookId = 1L;
        bookService.deleteById(1L);

        verify(bookRepository, times(1)).deleteById(bookId);
    }

}