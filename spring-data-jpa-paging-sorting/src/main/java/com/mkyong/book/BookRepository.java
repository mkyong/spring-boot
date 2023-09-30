package com.mkyong.book;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository extends ListPagingAndSortingRepository
// ListPagingAndSortingRepository extends PagingAndSortingRepository
public interface BookRepository extends JpaRepository<Book, Long> {

}