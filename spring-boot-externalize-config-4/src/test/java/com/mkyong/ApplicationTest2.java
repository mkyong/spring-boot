package com.mkyong;

import com.mkyong.service.DatabaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Simulate Spring Boot Application behaviour of loading properties
 * 1. Properties from @TestPropertySource
 * 2. The application.properties or application.yml from the src/test/resources directory.
 * 3. The application.properties or application.yml from the src/main/resources directory.
 * <p>
 * In this case, application.properties from the src/main/resources is loaded.
 */
@SpringBootTest
@TestPropertySource("classpath:test.properties")
// inline properties
//@TestPropertySource(properties = {"db.thread-pool=10"})
/* multiple inline properties
@TestPropertySource(properties = {
        "db.thread-pool=10",
        "db.name=mkyong"
})*/
public class ApplicationTest2 {

    @Autowired
    private DatabaseService dbServer;

    @Test
    public void testDatabaseName() {
        assertEquals("mkyong", dbServer.getName());
    }

    @Test
    public void testDatabaseThreadPool() {
        assertEquals(10, dbServer.getThreadPool());
    }

}

