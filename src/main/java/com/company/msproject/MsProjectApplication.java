package com.company.msproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MsProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsProjectApplication.class, args);
	}

}
