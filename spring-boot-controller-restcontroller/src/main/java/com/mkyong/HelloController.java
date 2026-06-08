package com.mkyong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// @Controller means this class returns web pages
@Controller
public class HelloController {

    // When you open / in a browser, this sends the text straight back
    @GetMapping("/")
    @ResponseBody
    public String welcome() {
        return "Hello World, Spring Boot!";
    }

    // When you open /hello in a browser, this runs
    @GetMapping("/hello")
    public String hello() {
        // This returns the NAME of a page called "hello"
        return "hello";
    }
}