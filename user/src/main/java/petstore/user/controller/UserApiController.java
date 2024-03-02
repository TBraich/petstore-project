package petstore.user.controller;

import static petstore.common.utils.CommonFunctions.prepareResponse;
import static petstore.common.dto.RestApiHeader.EVENT_TIME;
import static petstore.common.dto.RestApiHeader.ONE_TIME_ID;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import petstore.common.validation.Required;
import petstore.user.dto.user.request.CreateUserRequest;
import petstore.user.dto.user.request.UpdateUserRequest;
import petstore.user.service.UserService;

@RestController
@Validated
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApiController {
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<ObjectNode> create(
      @RequestHeader(name = ONE_TIME_ID) String oneTimeId,
      @RequestHeader(name = EVENT_TIME) String eventTime,
      @Valid @RequestBody CreateUserRequest request) {
    log.info("START API Create User, ID: {} at {}, Request: {}", oneTimeId, eventTime, request);
    var response = userService.create(oneTimeId, request);
    return prepareResponse(oneTimeId, response);
  }

  @GetMapping("/{userInfo}")
  public ResponseEntity<ObjectNode> find(
      @RequestHeader(name = ONE_TIME_ID) String oneTimeId,
      @RequestHeader(name = EVENT_TIME) String eventTime,
      @Required @RequestParam("userInfo") String userId) {
    log.info("START API Find User, ID: {} at {}, Request: {}", oneTimeId, eventTime, userId);
    var response = userService.find(oneTimeId, userId);
    return prepareResponse(oneTimeId, response);
  }

  @PutMapping("/{userInfo}")
  public ResponseEntity<ObjectNode> update(
      @RequestHeader(name = ONE_TIME_ID) String oneTimeId,
      @RequestHeader(name = EVENT_TIME) String eventTime,
      @Nonnull @RequestParam("userInfo") String userId,
      @RequestBody UpdateUserRequest request) {
    log.info("START API Update User, ID: {} at {}, userId: {}", oneTimeId, eventTime, userId);
    var response = userService.update(oneTimeId, userId, request);
    return prepareResponse(oneTimeId, response);
  }

  @DeleteMapping("/{userInfo}")
  public ResponseEntity<ObjectNode> delete(
      @RequestHeader(name = ONE_TIME_ID) String oneTimeId,
      @RequestHeader(name = EVENT_TIME) String eventTime,
      @Nonnull @RequestParam("userInfo") String userId) {
    log.info(
        "START API Delete User, RequestID: {} at {}, Request: {}", oneTimeId, eventTime, userId);
    var response = userService.delete(oneTimeId, userId);
    return prepareResponse(oneTimeId, response);
  }
}
