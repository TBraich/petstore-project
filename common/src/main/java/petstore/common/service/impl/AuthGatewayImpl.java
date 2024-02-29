package petstore.common.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import petstore.common.advice.exception.ExternalException;
import petstore.common.entity.AuthorizeInfo;
import petstore.common.service.AuthGateway;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthGatewayImpl implements AuthGateway {
  private final RestTemplate restTemplate;

  @Value("${petstore.app.auth.host:http://localhost:8083}")
  private String baseHost;

  @Value("${petstore.app.auth.authorize:/ath/authorize}")
  private String authorizePath;

  @Value("${petstore.app.auth.path:/ath/role}")
  private String rolePath;

  @Override
  public AuthorizeInfo authorize(String userId) {
    try {
      String url = baseHost + authorizePath + "?user_id=" + userId;

      // Create headers
      var headers = new HttpHeaders();
      headers.add("one-time-id", UUID.randomUUID().toString());
      headers.add("event-time", LocalDateTime.now().toString());
      // Add any other necessary headers here

      // Create a new HttpEntity
      var entity = new HttpEntity<>(headers);

      ResponseEntity<String> response =
          restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
      log.info("Authorization Response for user {}: {}", userId, response.getBody());

      ObjectMapper mapper = new ObjectMapper();
      JsonNode rootNode = mapper.readTree(response.getBody());

      JsonNode dataNode = rootNode.path("data");
      return mapper.treeToValue(dataNode, AuthorizeInfo.class);
    } catch (Exception e) {
      log.error("Failed to get authorization info");
      throw new ExternalException("AuthGateway");
    }
  }
}
