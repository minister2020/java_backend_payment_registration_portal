package com.example.AdvancedUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;

@SpringBootApplication
public class AdvancedUserApplication {

	public static void main(String[] args) {

		SpringApplication.run(AdvancedUserApplication.class, args);

	}

}
