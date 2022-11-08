package nttdata.bootcamp.msaccountstransactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "MS-ACCOUNTS-TRANSACTIONS", version = "1.0", description = "MicroServicio de transacciones de cuentas."))
@EnableEurekaClient
@SpringBootApplication
public class MsAccountsTransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAccountsTransactionsApplication.class, args);
	}

}
