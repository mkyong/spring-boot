package com.mkyong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartWebApplication implements CommandLineRunner{

@Autowired
UserRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(StartWebApplication.class, args);

		User u = new User(1,"rintu",45000);
		repo.deleteById(1);
	}

}

