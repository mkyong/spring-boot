package com.mkyong.book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity              // This class maps to a table
@Table(name = "book") // The table is called "book"
public class Book {

    @Id // This field is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Postgres makes the number
    private Long id;

    private String title;
    private String author;

    // JPA needs an empty constructor
    public Book() {
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}