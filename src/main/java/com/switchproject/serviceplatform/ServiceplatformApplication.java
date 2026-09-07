package com.switchproject.serviceplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ServiceplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceplatformApplication.class, args);
	}

}
