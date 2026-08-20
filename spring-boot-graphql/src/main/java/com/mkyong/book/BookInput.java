package com.mkyong.book;

// The shape of the data a client sends in for the addBook mutation
public record BookInput(String name, int pageCount, String authorId) {
}
