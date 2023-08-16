package com.mkyong;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebServerTests {

    @LocalServerPort
    private int port;

    @Test
    public void testServerPort() {
        assertEquals(8181, port, "Server is not running on port 8181");
    }

}
