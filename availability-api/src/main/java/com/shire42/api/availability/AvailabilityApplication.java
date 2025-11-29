package com.shire42.api.availability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AvailabilityApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvailabilityApplication.class, args);
	}

}
