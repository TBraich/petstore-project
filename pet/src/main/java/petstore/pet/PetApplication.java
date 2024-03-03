package petstore.pet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "petstore")
@ComponentScan("petstore")
public class PetApplication {
	public static void main(String[] args) {
		SpringApplication.run(PetApplication.class, args);
	}

}
