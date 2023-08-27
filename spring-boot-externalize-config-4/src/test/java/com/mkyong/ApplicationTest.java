package com.mkyong;

import com.mkyong.service.DatabaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

// easy but load entire context, no good.
// @SpringBootTest

// Keep test in minimal configuration, only `test.properties` is loaded.
@SpringJUnitConfig
@TestPropertySource("classpath:test.properties")
public class ApplicationTest {

    @Autowired
    private DatabaseService dbServer;

    @Test
    public void testDefaultDatabaseName() {
        assertEquals("hello", dbServer.getName());
    }

    @Test
    public void testDatabaseThreadPool() {
        assertEquals(10, dbServer.getThreadPool());
    }

    // Mock Configuration within the test class
    @Configuration
    static class TestConfig {
        @Bean
        public DatabaseService dbServer() {
            return new DatabaseService();
        }
    }

}
