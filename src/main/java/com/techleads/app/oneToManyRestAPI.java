package com.techleads.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class oneToManyRestAPI {

	public static void main(String[] args) {
		SpringApplication.run(oneToManyRestAPI.class, args);
	}
//https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-many-mapping-example/
}
