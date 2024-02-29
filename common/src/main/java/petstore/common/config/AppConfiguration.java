package petstore.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;
import petstore.common.service.Auth0Service;
import petstore.common.service.impl.Auth0ServiceImpl;
import petstore.common.service.impl.Auth0Stub;

@EnableRetry
@Configuration
public class AppConfiguration {
  @Value("${petstore.app.env:dev}")
  private String env;

  @Bean
  public Auth0Service auth0Service() {
    if ("dev".equalsIgnoreCase(env)) {
      return new Auth0Stub();
    } else {
      return new Auth0ServiceImpl();
    }
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
