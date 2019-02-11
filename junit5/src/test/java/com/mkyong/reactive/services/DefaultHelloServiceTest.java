package com.mkyong.reactive.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.junit.jupiter.api.Assertions.*;

//Test MVC, Test Normal Service, Test DAO
//https://docs.spring.io/spring-boot/docs/2.1.2.RELEASE/reference/htmlsingle/#boot-features-testing-spring-boot-applications
@SpringBootTest
public class DefaultHelloServiceTest {

    @Autowired
    HelloService helloService;

    @DisplayName("A simple hello world test")
    @Test
    void testGet() {

        assertEquals("Hello JUnit 5", helloService.get());
    }


}