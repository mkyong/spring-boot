package com.mkyong.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.graphql.test.autoconfigure.GraphQlTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

// Loads only BookController, not the whole app
@GraphQlTest(BookController.class)
@Import(BookRepository.class) // the controller needs this, so bring it in
class BookControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldReturnBookWithAuthor() {
        this.graphQlTester
                .documentName("bookDetails")  // reads bookDetails.graphql
                .variable("id", "book-1")     // fills in the $id placeholder
                .execute()
                .path("bookById")             // zoom into the bookById part
                .matchesJson("""
                        {
                            "id": "book-1",
                            "name": "Effective Java",
                            "pageCount": 416,
                            "author": {
                              "firstName": "Joshua",
                              "lastName": "Bloch"
                            }
                        }
                        """);
    }

    @Test
    void shouldFindTwoJavaBooks() {
        this.graphQlTester
                .document("{ searchBooks(keyword: \"java\") { name } }") // inline query
                .execute()
                .path("searchBooks")
                .entityList(Book.class)   // turn the JSON list into Java objects
                .hasSize(2);              // expect two matches
    }
}
