package com.bhakti.bktapijava2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BktapiJava2Application {

	public static void main(String[] args) {
		SpringApplication.run(BktapiJava2Application.class, args);
	}

}
