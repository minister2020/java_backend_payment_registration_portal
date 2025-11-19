package com.example.AdvancedUser;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AdvancedUserApplication {

	public static void main(String[] args) {

		SpringApplication.run(AdvancedUserApplication.class, args);

	}

}
