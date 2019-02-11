package com.mkyong.reactive;

import com.mkyong.reactive.model.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// junit 4
//@RunWith(SpringRunner.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient(timeout = "10000")//10 seconds
public class TestCommentWebApplication {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testCommentStream() {

        List<Comment> comments = webClient
                .get().uri("/comment/stream")
                .accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
                .exchange()
                .expectStatus().isOk()
                //.expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON) // caused timeout
                .returnResult(Comment.class)
                .getResponseBody()
                .take(3)
                .collectList()
                .block();

        comments.forEach(x -> System.out.println(x));

        assertEquals(3, comments.size());

    }

}
