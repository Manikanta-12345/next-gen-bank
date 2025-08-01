package com.nextgen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class NextGenConfigserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(NextGenConfigserverApplication.class, args);
	}

}
