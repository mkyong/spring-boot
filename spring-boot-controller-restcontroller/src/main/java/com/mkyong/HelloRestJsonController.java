package com.mkyong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController always sends back data (text or JSON)
@RestController
public class HelloRestJsonController {

    // When you open /user, this runs
    @GetMapping("/user")
    public User user() {
        // Make a new user and send it back
        return new User("mkyong", 40);
    }

    // A simple class to hold user data
    public record User(String name, int age) {
    }

}
