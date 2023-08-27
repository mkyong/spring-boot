package com.mkyong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication implements CommandLineRunner {

    @Autowired
    private DatabaseService dbConfig;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(dbConfig.getName());
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }
}