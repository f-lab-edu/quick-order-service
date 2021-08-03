package com.quickorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class QuickorderserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickorderserviceApplication.class, args);
	}

}
