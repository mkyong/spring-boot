package com.mkyong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyWebApplication {

    public static void main(String[] args) {
        System.out.print("main...");
        SpringApplication.run(MyWebApplication.class, args);
    }

}