package com.banquito.parametros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ParametrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParametrosApplication.class, args);
	}

}
