# Spring Boot + Spring Data JPA + MySQL example

Article link : https://mkyong.com/spring-boot/spring-boot-spring-data-jpa-mysql-example/

## Technologies used:
* Spring Boot 3.1.2
* Spring Data JPA (Hibernate 6 is the default JPA implementation)
* MySQL 8
* Java 17
* Maven 3
* JUnit 5
* Spring Test using TestRestTemplate
* Docker, [Testcontainers](https://testcontainers.com/) (for Spring integration tests using a MySQL container)

## How to run it
```

$ git clone [https://github.com/mkyong/spring-boot.git](https://github.com/mkyong/spring-boot.git)

$ cd spring-data-jpa-mysql

# Run MySQL container for testing
$ docker run --name c1 -p 3306:3306 -e MYSQL_USER=mkyong -e MYSQL_PASSWORD=password -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=mydb -d mysql:8.1

# Skip test, the Testcontainers takes time
$ ./mvnw clean package -Dmaven.test.skip=true

$ ./mvnw spring-boot:run

```


