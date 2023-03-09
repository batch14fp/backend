package com.lawencon.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "com.lawencon")
@ComponentScan(basePackages = "com.lawencon")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
