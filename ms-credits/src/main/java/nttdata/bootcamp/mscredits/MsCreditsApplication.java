package nttdata.bootcamp.mscredits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "MS-CREDITS", version = "1.0", description = "MicroServicio de créditos."))
@EnableEurekaClient
@SpringBootApplication
public class MsCreditsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCreditsApplication.class, args);
	}

}
