package com.example.AdvancedUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.AdvancedUser.repository")
@EntityScan(basePackages = "com.example.AdvancedUser.model")
public class AdvancedUserApplication {

	public static void main(String[] args) {

		SpringApplication.run(AdvancedUserApplication.class, args);

	}

}
