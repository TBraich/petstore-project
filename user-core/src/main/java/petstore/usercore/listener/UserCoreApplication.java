package petstore.usercore.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "petstore")
@EnableJpaRepositories("petstore.user.repository")
@ComponentScan("petstore")
@EntityScan("petstore.user.entity")
public class UserCoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserCoreApplication.class, args);
  }
}
