package nttdata.bootcamp.mscreditstransactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "MS-CREDITS-TRANSACTIONS", version = "1.0", description = "MicroServicio de transacciones de créditos."))
@EnableEurekaClient
@SpringBootApplication
public class MsCreditsTransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCreditsTransactionsApplication.class, args);
	}

}
