package com.mkyong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController always sends back data (text or JSON)
@RestController
public class HelloRestController {

    @GetMapping("/hello3")
    public String hello() {
        // This text goes straight back to the browser
        return "Hello World @RestController";
    }

}
