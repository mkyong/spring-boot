package com.mkyong;

import com.mkyong.config.GlobalProperties;
import com.mkyong.config.WordpressProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private WordpressProperties wpProperties;

    @Autowired
    private GlobalProperties globalProperties;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println(globalProperties);
        System.out.println(wpProperties);
    }
}
