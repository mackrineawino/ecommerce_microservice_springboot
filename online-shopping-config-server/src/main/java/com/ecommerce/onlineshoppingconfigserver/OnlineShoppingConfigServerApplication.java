package com.ecommerce.onlineshoppingconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableConfigServer
public class OnlineShoppingConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineShoppingConfigServerApplication.class, args);
	}

}
