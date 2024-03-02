package petstore.user.controller;

import static java.lang.Integer.parseInt;
import static petstore.common.dto.RestApiHeader.EVENT_TIME;
import static petstore.common.dto.RestApiHeader.ONE_TIME_ID;
import static petstore.common.utils.CommonFunctions.prepareResponse;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import petstore.common.validation.IntValue;
import petstore.common.validation.NotBlank;
import petstore.user.dto.request.CreateBatchUserRequest;
import petstore.user.dto.request.CreateUserRequest;
import petstore.user.dto.request.UserPageRequest;
import petstore.user.service.UserListService;

@RestController
@Validated
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserListApiController {
  private final UserListService userListService;

  @PostMapping("register")
  public ResponseEntity<ObjectNode> createByBatch(
      @RequestHeader(name = ONE_TIME_ID) String oneTimeId,
      @RequestHeader(name = EVENT_TIME) String eventTime,
      @Valid @RequestBody CreateBatchUserRequest request) {
    log.info(
        "START API Create Batch Users, ID: {} at {}, users: {}",
        oneTimeId,
        eventTime,
        request.getUsers().stream()
            .map(CreateUserRequest::getFirstName)
            .collect(Collectors.joining(", ")));
    var response = userListService.create(oneTimeId, request);
    return prepareResponse(oneTimeId, response);
  }

  @GetMapping("list")
  public ResponseEntity<ObjectNode> list(
      @RequestHeader(name = ONE_TIME_ID) String oneTimeId,
      @RequestHeader(name = EVENT_TIME) String eventTime,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "email", required = false) String email,
      @RequestParam(value = "name", required = false) String name,
      @NotBlank @IntValue @RequestParam(value = "page", required = false, defaultValue = "1")
          String page,
      @NotBlank @IntValue @RequestParam(value = "size", required = false, defaultValue = "30")
          String size,
      @NotBlank
          @Pattern(regexp = "^(fistName|lastName|email)$")
          @RequestParam(value = "sort", required = false, defaultValue = "firstName")
          String sort,
      @NotBlank
          @Pattern(regexp = "(?i)^(asc|desc)$")
          @RequestParam(value = "order", required = false, defaultValue = "ASC")
          String order) {
    // Prepare list request
    var sortPage = Sort.by("firstName");
    var pageable =
        PageRequest.of(
            parseInt(page),
            parseInt(size),
            order.equalsIgnoreCase("ASC") ? sortPage.ascending() : sortPage.descending());
    var request =
        UserPageRequest.builder()
            .userId(StringUtils.isNotBlank(userId) ? userId : "")
            .email(StringUtils.isNotBlank(email) ? email : "")
            .name(StringUtils.isNotBlank(name) ? name : "")
            .pageable(pageable)
            .build();

    log.info("START API Search Users, ID: {} at {}, users: {}", oneTimeId, eventTime, request);

    var response = userListService.list(oneTimeId, request);

    return prepareResponse(oneTimeId, response);
  }
}
