package com.mkyong;

import com.mkyong.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private DatabaseService dbConfig;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(dbConfig);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}