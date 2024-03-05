package petstore.common.utils;

import static java.lang.Integer.parseInt;
import static java.util.UUID.randomUUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

  public static PageRequest preparePageRequest(
      String page, String size, String sortKey, String orderBy) {
    var sortPage = Sort.by(sortKey);
    return PageRequest.of(
        parseInt(page),
        parseInt(size),
        orderBy.equalsIgnoreCase("ASC") ? sortPage.ascending() : sortPage.descending());
  }

  public static RowBounds prepareRowBounds(String page, String size) {
    var pageNum = parseInt(page);
    var pageSize = parseInt(size);
    var offset = (pageNum - 1) * pageSize;
    return new RowBounds(offset, pageSize);
  }
}
