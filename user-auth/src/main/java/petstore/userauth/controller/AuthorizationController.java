package petstore.userauth.controller;

import static petstore.common.dto.RestApiHeader.EVENT_TIME;
import static petstore.common.dto.RestApiHeader.ONE_TIME_ID;
import static petstore.common.utils.CommonFunctions.prepareResponse;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import petstore.common.validation.Required;
import petstore.userauth.service.AuthorizationService;

@RestController
@Validated
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/ath")
public class AuthorizationController {
  private final AuthorizationService service;

  @GetMapping("/authorize")
  public ResponseEntity<ObjectNode> authorize(
      @RequestHeader(name = ONE_TIME_ID) String oneTimeId,
      @RequestHeader(name = EVENT_TIME) String eventTime,
      @Required @RequestParam(name = "user_id", required = false) String userId) {
    log.info(
        "START API Authorization, for user: {} at {}, oneTimeId: {}", userId, eventTime, oneTimeId);
    var response = service.authorize(oneTimeId, userId);
    return prepareResponse(oneTimeId, response);
  }
}
