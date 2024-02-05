package com.mkyong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // Get the SLF4J logger interface, default Logback, a SLF4J implementation
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/")
    public String hello() {

        logger.debug("Debug level - Hello Logback");

        logger.info("Info level - Hello Logback");

        logger.error("Error level - Hello Logback");

        return "Hello SLF4J";
    }

    // Log variables
    @GetMapping("/hello/{name}")
    String find(@PathVariable String name) {

        logger.debug("Debug level - Hello Logback {}", name);

        logger.info("Info level - Hello Logback {}", name);

        logger.error("Error level - Hello Logback {}", name);

        return "Hello SLF4J" + name;

    }

}