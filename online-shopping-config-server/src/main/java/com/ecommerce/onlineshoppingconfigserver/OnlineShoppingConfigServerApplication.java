package com.ecommerce.onlineshoppingconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class OnlineShoppingConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineShoppingConfigServerApplication.class, args);
	}

}
