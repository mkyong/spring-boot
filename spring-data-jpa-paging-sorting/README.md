# Spring Boot + Spring Data JPA + Paging And Sorting example

Article link : https://mkyong.com/spring-boot/spring-data-jpa-paging-and-sorting-example/

## Technologies used:
* Spring Boot 3.1.2
* Spring Data JPA
* H2 in-memory database
* Java 17
* Maven 3
* JUnit 5
* Spring Integration Tests with TestRestTemplate
* Unit Tests with Mocking (Mockito)

## How to run it
```

$ git clone [https://github.com/mkyong/spring-boot.git](https://github.com/mkyong/spring-boot.git)

$ cd spring-data-jpa-paging-sorting

$ ./mvnw spring-boot:run

$ curl -s "http://localhost:8080/books"

$ curl -s "http://localhost:8080/books?pageNo=1&pageSize=4&sortBy=title&sortDirection=desc" | python3 -m json.tool

```


