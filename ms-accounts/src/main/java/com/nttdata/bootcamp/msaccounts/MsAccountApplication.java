package com.nttdata.bootcamp.msaccounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "MS-ACCOUNTS", version = "1.0", description = "MicroServicio de cuentas."))
@EnableEurekaClient 
@SpringBootApplication
public class MsAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAccountApplication.class, args);
	}

}
