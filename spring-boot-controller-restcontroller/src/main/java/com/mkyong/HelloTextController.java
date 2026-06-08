package com.mkyong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloTextController {

    // @ResponseBody means "send this text back, not a page"
    @GetMapping("/hello2")
    @ResponseBody
    public String hello() {
        return "Hello World";
    }

}
