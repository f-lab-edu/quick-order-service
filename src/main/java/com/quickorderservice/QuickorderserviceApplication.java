package com.quickorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuickorderserviceApplication {

	public static void main(String[] args) {
		setProperty();
		SpringApplication.run(QuickorderserviceApplication.class, args);
	}

	private static void setProperty() {
		String profile = System.getProperty("spring.profiles.active");
		if(profile == null) {
			System.setProperty("spring.profiles.active", "production");
		}	
	}

}
