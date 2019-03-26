package com.mkyong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

//https://docs.spring.io/spring-boot/docs/current/reference/html/howto-logging.html
@Controller
public class HelloController {

    private static final Logger logger = LogManager.getLogger(HelloController.class);

    private List<Integer> num = Arrays.asList(1, 2, 3, 4, 5);

    @GetMapping("/")
    public String main(Model model) {

        logger.debug("Hello from Log4j 2");

        model.addAttribute("tasks", num);

        return "welcome"; //view
    }

}
