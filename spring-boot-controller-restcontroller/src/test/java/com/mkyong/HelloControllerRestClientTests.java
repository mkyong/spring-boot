package com.mkyong;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

// @Controller + view name "hello" -> renders templates/hello.html
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class HelloControllerRestClientTests {

    @Autowired
    private RestTestClient restTestClient;

    @Test
    public void hello_page_ok() {
        restTestClient.get().uri("/hello")
                .accept(MediaType.TEXT_HTML)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .value(body -> {
                    assert body.contains("<title>Hello Page</title>");
                    assert body.contains("Hello World from Spring Boot!");
                });
    }

}
