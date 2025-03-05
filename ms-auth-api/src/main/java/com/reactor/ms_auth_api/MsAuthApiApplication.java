package com.reactor.ms_auth_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.reactor.ms_auth_api")
public class MsAuthApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAuthApiApplication.class, args);
	}
}
