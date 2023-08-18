package com.mkyong.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Book {

    private long id;
    private String title;
    private List<String> tags;
    private List<Author> authors;
    private LocalDate publishedDate;
    private Map<String, String> meta;

    public Book() {
    }

    public Book(long id, String title, List<String> tags, List<Author> authors, LocalDate publishedDate, Map<String, String> meta) {
        this.id = id;
        this.title = title;
        this.tags = tags;
        this.authors = authors;
        this.publishedDate = publishedDate;
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", tags=" + tags +
                ", authors=" + authors +
                ", publishedDate=" + publishedDate +
                ", meta=" + meta +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Map<String, String> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, String> meta) {
        this.meta = meta;
    }
}
