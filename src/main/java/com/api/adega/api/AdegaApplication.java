package com.api.adega.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.api.adega.api")
public class AdegaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdegaApplication.class, args);
	}

}
