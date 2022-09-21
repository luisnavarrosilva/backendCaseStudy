package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //(exclude = {SecurityAutoConfiguration.class})
public class HtdApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(HtdApplication.class, args);
	}
    
}
