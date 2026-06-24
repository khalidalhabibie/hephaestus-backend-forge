package com.example.jpabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.example.jpabackend.config.LoanInterestConfig;

@SpringBootApplication
@EnableConfigurationProperties(LoanInterestConfig.class)
public class TrainingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingApplication.class, args);
	}

}