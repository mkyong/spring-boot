package com.mkyong;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

// @RestController returning a record -> JSON response body
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class HelloRestJsonControllerRestClientTests {

    @Autowired
    private RestTestClient restTestClient;

    @Test
    public void user_ok() {
        restTestClient.get().uri("/user")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.name").isEqualTo("mkyong")
                .jsonPath("$.age").isEqualTo(40);
    }

}
