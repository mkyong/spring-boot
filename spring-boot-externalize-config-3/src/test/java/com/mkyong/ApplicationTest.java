package com.mkyong;

import com.mkyong.config.DatabaseProperties;
import com.mkyong.config.FileProperties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// easy but load entire context, no good.
// @SpringBootTest

// Keep test in minimal configuration
@SpringJUnitConfig
public class ApplicationTest {

    @Autowired
    private DatabaseProperties dbServer;

    @Autowired
    private FileProperties fileServer;

    @BeforeAll
    public static void setUp() {
        System.setProperty("app.home", "/home/other");
    }

    @Test
    public void testFileServer() {
        assertEquals("/home/other/server1/file/path", fileServer.getPath());
    }

    @Test
    public void testDatabaseServer() {

        assertEquals("PostgreSQL", dbServer.getName());
        List<String> clusterIp = dbServer.getClusterIp();
        assertEquals(3, clusterIp.size());
        assertTrue(clusterIp.contains("127.0.0.1"));
        assertTrue(clusterIp.contains("127.0.0.2"));
        assertTrue(clusterIp.contains("127.0.0.3"));

    }

    // Mock Configuration within the test class
    @Configuration
    @PropertySource({"classpath:/server/db.properties", "classpath:/server/file.properties"})
    static class TestConfig {
        @Bean
        public DatabaseProperties dbServer() {
            return new DatabaseProperties();
        }

        @Bean
        public FileProperties fileServer() {
            return new FileProperties();
        }

        // supports List from .properties file.
        @Bean
        public ConversionService conversionService() {
            return new DefaultConversionService();
        }
    }

}
