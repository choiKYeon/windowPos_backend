package com.example.windowPos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WindowPosApplication {

	public static void main(String[] args) {
		SpringApplication.run(WindowPosApplication.class, args);
	}

}
