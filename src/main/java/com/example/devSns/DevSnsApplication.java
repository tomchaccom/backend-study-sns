package com.example.devSns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DevSnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevSnsApplication.class, args);
	}

}
