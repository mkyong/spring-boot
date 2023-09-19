package com.mkyong;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starts Spring Boot Testcontainers application...");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}