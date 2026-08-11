# Spring Boot REST Docs Example

Document a REST API with [Spring REST Docs](https://docs.spring.io/spring-restdocs/tutorial/getting-started/index.html).
The snippets are produced by the tests, so the documentation can never drift away from the real API.

Visit Article - [Spring Boot REST Docs Example](https://mkyong.com/spring-boot/spring-boot-rest-docs-example/)

## Things you will use:

* Spring Boot 4.1.0
* Spring REST Docs 4.0.1
* Java 25
* Maven 3.9.6
* Asciidoctor Maven Plugin 3.2.0

## How it works

1. `mvn test` runs the tests. Every documented request writes a set of `.adoc` snippets into
   `target/generated-snippets`.
2. `mvn package` then runs Asciidoctor, which reads `src/main/asciidoc/index.adoc`, pulls in
   those snippets, and writes `target/generated-docs/index.html`.
3. The same HTML is copied into the jar under `static/docs`, so a running app serves it at
   http://localhost:8080/docs/index.html

If a controller changes and a test is not updated, the test fails and the build stops. That is
the whole point, the docs are verified.

## How to start

```bash
$ git clone https://github.com/mkyong/spring-boot.git

$ cd spring-boot-rest-doc

# 1. Generate snippets + HTML, and package the jar
$ mvn clean package

# 2. Open the generated documentation
# target/generated-docs/index.html

# 3. Or run the app and browse the docs it serves
$ java -jar target/spring-boot-rest-doc-1.0.jar

# http://localhost:8080/docs/index.html
```

Note: `mvn spring-boot:run` does not serve `/docs`, the documentation is generated during
`prepare-package`. Use `mvn package` and run the jar.

## REST API

| Method   | URI                    | Description                    |
|----------|------------------------|--------------------------------|
| `GET`    | `/books`               | List all books                 |
| `GET`    | `/books?author={name}` | List books filtered by author  |
| `GET`    | `/books/{id}`          | Find one book, 404 if missing  |
| `POST`   | `/books`               | Create a book, returns 201     |
| `DELETE` | `/books/{id}`          | Delete a book, returns 204     |

```bash
$ curl localhost:8080/books

$ curl localhost:8080/books/1

$ curl -X POST localhost:8080/books \
    -H "Content-Type: application/json" \
    -d '{"title":"Clean Code","author":"Robert C. Martin","price":42.00}'

$ curl -X DELETE localhost:8080/books/2
```

## Two ways to set up REST Docs

Both are in this project, on purpose. They split the API between them so that no operation is
documented twice.

**1. Manual, the way the official tutorial does it** - `BookErrorDocumentationTests`

We build the `MockMvc` ourselves and apply `documentationConfiguration()` to it. No Spring Boot
test auto-configuration involved. Nothing is documented unless you ask for it, there is no
implicit `document("{method-name}")`. This class documents the `404` case.

```java
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class BookErrorDocumentationTests {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
               RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void books_find_by_id_404() throws Exception {
        this.mockMvc.perform(get("/books/{id}", 999).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(document("books-find-by-id-404",
                        pathParameters(
                                parameterWithName("id").description("Unique id of a book that does not exist")
                        )));
    }
}
```

**2. Spring Boot auto-configuration** - `BookControllerDocumentationTests`

`@AutoConfigureRestDocs` configures the injected `MockMvc` for you, and applies
`document("{method-name}")` to every request automatically. This class documents everything
else.

```java
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.mkyong.com", uriPort = 443)
public class BookControllerDocumentationTests { ... }
```

Note the package moved in Spring Boot 4:

```java
// Spring Boot 3.x
// import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;

// Spring Boot 4.x, needs the spring-boot-starter-restdocs dependency
import org.springframework.boot.restdocs.test.autoconfigure.AutoConfigureRestDocs;
```

## Generated snippets

Every documented call always produces `curl-request`, `http-request`, `http-response`,
`httpie-request`, `request-body` and `response-body`. The rest you ask for explicitly:

| Snippet             | Produced by                              |
|---------------------|------------------------------------------|
| `path-parameters`   | `pathParameters(parameterWithName(...))` |
| `query-parameters`  | `queryParameters(parameterWithName(...))`|
| `request-fields`    | `requestFields(fieldWithPath(...))`      |
| `response-fields`   | `responseFields(fieldWithPath(...))`     |
| `response-headers`  | `responseHeaders(headerWithName(...))`   |

Two things that catch people out:

* `pathParameters` only works if the request keeps the URI template and is built with
  `RestDocumentationRequestBuilders`, not `MockMvcRequestBuilders`.

  ```java
  // works, the {id} template survives
  import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
  mockMvc.perform(get("/books/{id}", 1))
  ```

* `requestFields` and `responseFields` must describe every field in the payload. Miss one and
  the test fails with an "undocumented field" error. Describe one that is not there and it
  fails too.

Use `preprocessResponse(prettyPrint())` to get readable JSON in the snippets instead of one
long line.

## Assembling the document

`src/main/asciidoc/index.adoc` pulls the snippets in. The `{snippets}` attribute and the
`operation::` macro come from the `spring-restdocs-asciidoctor` dependency declared on the
Asciidoctor plugin.

```asciidoc
operation::books-find-by-id[snippets='curl-request,path-parameters,http-request,http-response,response-fields']
```

Or include a single snippet by hand:

```asciidoc
include::{snippets}/books-find-by-id/curl-request.adoc[]
```
