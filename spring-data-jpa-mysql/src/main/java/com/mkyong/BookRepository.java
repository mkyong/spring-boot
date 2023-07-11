package com.mkyong;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByName(String name);

    List<Book> findById(int id);

    List<Book> findByPrice(int price);

    List<Book> deleteById(int id);

    List<Book> findByIdAndName(int id,String name);

    List<Book> fetchById(int id);

    List<Book> findByUnAndPwd(String un,String pwd);
    

}
