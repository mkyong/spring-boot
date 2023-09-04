package com.mkyong;

import com.mkyong.config.DatabaseProperties;
import com.mkyong.config.FileProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//@PropertySource({"classpath:/server/db.properties", "classpath:/server/file.properties"})
/*@PropertySources({
        @PropertySource("classpath:/server/db.properties"),
        @PropertySource("classpath:/server/file.properties")
})*/
@PropertySource(value = "classpath:missing.properties", ignoreResourceNotFound = true)
public class Application implements CommandLineRunner {

    @Autowired
    private DatabaseProperties db;

    @Autowired
    private FileProperties file;

    @Override
    public void run(String... args) {
        System.out.println(db);
        System.out.println(file);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
