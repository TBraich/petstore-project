package petstore.user.controller;

import static petstore.common.utils.CommonFunctions.prepareResponse;
import static petstore.user.dto.common.RestApiHeader.EVENT_TIME;
import static petstore.user.dto.common.RestApiHeader.ONE_TIME_ID;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petstore.user.dto.auth.LoginRequest;
import petstore.user.service.AuthenticationService;

@RestController
@Validated
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/ath")
public class AuthenticationController {
  private final AuthenticationService service;

  @PostMapping("login")
  public ResponseEntity<ObjectNode> login(
      @RequestHeader(name = ONE_TIME_ID) String oneTimeId,
      @RequestHeader(name = EVENT_TIME) String eventTime,
      @Valid @RequestBody LoginRequest request) {
    log.info("START API Create User, ID: {} at {}, Request: {}", oneTimeId, eventTime, request);
    var response = service.login(oneTimeId, request);
    return prepareResponse(oneTimeId, response);
  }
}
