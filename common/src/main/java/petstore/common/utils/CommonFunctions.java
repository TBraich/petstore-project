package petstore.common.utils;

import static java.util.UUID.randomUUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;

@Log4j2
public class CommonFunctions {
  public static ResponseEntity<ObjectNode> prepareResponse(String oneTimeId, Object body) {
    var responseBody = new ObjectMapper().createObjectNode();
    responseBody.put("requestId", oneTimeId);
    responseBody.putPOJO("data", body);
    return ResponseEntity.ok(responseBody);
  }

  public static String generatePetId(String storeId, String category) {
    // Base id of pet will be UUID non-divided character
    var baseId = randomUUID().toString().replace("-", "");

    return String.format("%s-%s-%s", storeId, category, baseId);
  }
}
