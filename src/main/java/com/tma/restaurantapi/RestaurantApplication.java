package com.tma.restaurantapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ContextConfiguration;

/**
 * The RestaurantApplication class is the entry point for the restaurant application.
 * Initializing and starting the Spring Boot application.
 */
@SpringBootApplication
@ContextConfiguration
@EnableJpaAuditing
public class RestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantApplication.class, args);
	}

}
