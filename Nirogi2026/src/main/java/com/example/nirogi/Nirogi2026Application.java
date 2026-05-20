package com.example.nirogi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {
                UserDetailsServiceAutoConfiguration.class
        })
public class Nirogi2026Application {

	public static void main(String[] args) {
		SpringApplication.run(Nirogi2026Application.class, args);
	}

}
