package com.mkyong;

import org.springframework.boot.SpringApplication;

public class TestMyApplication {

    public static void main(String[] args) {
        SpringApplication
                .from(MainApplication::main)
                .with(MyContainersConfiguration.class)
                .run(args);
    }

}
