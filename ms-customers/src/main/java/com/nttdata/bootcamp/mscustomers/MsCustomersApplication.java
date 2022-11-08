package com.nttdata.bootcamp.mscustomers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "MS-CUSTOMERS", version = "1.0", description = "MicroServicio de clientes."))
@EnableEurekaClient
@SpringBootApplication
public class MsCustomersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCustomersApplication.class, args);
	}

}
