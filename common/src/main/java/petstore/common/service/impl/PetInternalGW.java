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
import petstore.common.dto.gw.PetInfo;
import petstore.common.repository.CacheRepository;
import petstore.common.service.PetGateway;

@Service
@RequiredArgsConstructor
@Log4j2
public class PetInternalGW implements PetGateway {
  private final RestTemplate restTemplate;
  private final CacheRepository cacheRepository;

  @Value("${petstore.app.pet.host:http://localhost:8092}")
  private String baseHost;

  @Value("${petstore.app.auth.authorize:internal/%s}")
  private String petPath;

  @Value("${petstore.app.auth.authorize:internal/list/%s}")
  private String petListPath;

  @Override
  public PetInfo find(String petId) {
    try {
      String url = baseHost + String.format("%s", petId);

      // Create headers
      var headers = new HttpHeaders();
      headers.add("one-time-id", UUID.randomUUID().toString());
      headers.add("event-time", LocalDateTime.now().toString());
      // Add any other necessary headers here

      // Create a new HttpEntity
      var entity = new HttpEntity<>(headers);

      ResponseEntity<String> response =
          restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
      log.info("Pet Detail Response for pet {}: {}", petId, response.getBody());

      ObjectMapper mapper = new ObjectMapper();
      JsonNode rootNode = mapper.readTree(response.getBody());

      JsonNode dataNode = rootNode.path("data");
      return mapper.treeToValue(dataNode, PetInfo.class);
    } catch (Exception e) {
      log.error("Failed to get Pet info");
      throw new ExternalException("PetInternalGW");
    }
  }
}
