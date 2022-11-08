package nttdata.bootcamp.mscustomeraccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "MS-RESPONSIBLES", version = "1.0", description = "MicroServicio de responsables."))
@EnableEurekaClient
@SpringBootApplication
public class MsResponsiblesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsResponsiblesApplication.class, args);
	}

}
