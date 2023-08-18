package com.mkyong;

import com.mkyong.model.Author;
import com.mkyong.model.Book;
import com.mkyong.model.SimpleBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class WebController {

    private static final Logger logger =
            LoggerFactory.getLogger(WebController.class);

    @GetMapping("/")
    public SimpleBook main() {
        return new SimpleBook("Hello World");
    }

    @GetMapping("/book")
    public Book returnBook() {

        Author obj1 = new Author(1L, "Raoul-Gabriel Urma", "111-1111111");
        Author obj2 = new Author(2L, "Mario Fusco", "222-2222222");
        Author obj3 = new Author(3L, "Alan Mycroft", "333-3333333");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Modern Java in Action");
        book.setAuthors(List.of(obj1, obj2, obj3));
        book.setTags(List.of("Java", "Java 8"));
        book.setPublishedDate(LocalDate.of(2018, 11, 15));
        book.setMeta(Map.of("isbn-10", "1617293563", "isbn-13", "978-1617293566"));

        return book;

    }

    @GetMapping("/list")
    public List<String> returnList() {
        return List.of("Java", "React", "JavaScript");
    }

    @GetMapping("/map")
    public Map<String, String> returnMap() {
        return Map.of("key1", "a", "key2", "b", "key3", "c");
    }

}