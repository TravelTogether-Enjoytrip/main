package com.ssafy.enjoytrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ssafy.enjoytrip"})
public class EnjoytripFrameworkS10Team06Application {

	public static void main(String[] args) {
		SpringApplication.run(EnjoytripFrameworkS10Team06Application.class, args);
	}

}
