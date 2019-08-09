package com.mkyong.repository;

import com.mkyong.Book;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookRepository {

    int count();

    int save(Book book);

    int update(Book book);

    int deleteById(Long id);

    List<Book> findAll();

    List<Book> findByNameAndPrice(String name, BigDecimal price);

    Optional<Book> findById(Long id);

    String findNameById(Long id);

    int[] batchInsert(List<Book> books);

    int[][] batchInsert(List<Book> books, int batchSize);

    int[] batchUpdate(List<Book> books);

    int[][] batchUpdate(List<Book> books, int batchSize);

    void saveImage(Long bookId, File image);

    List<Map<String, InputStream>> findImageByBookId(Long bookId);

}
