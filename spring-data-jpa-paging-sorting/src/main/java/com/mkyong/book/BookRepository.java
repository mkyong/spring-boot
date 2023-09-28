package com.mkyong.book;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository extends PagingAndSortingRepository
public interface BookRepository extends JpaRepository<Book, Long> {
}