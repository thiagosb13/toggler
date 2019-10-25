package com.thiagobezerra.toggler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class TogglerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TogglerApplication.class, args);
	}
}
