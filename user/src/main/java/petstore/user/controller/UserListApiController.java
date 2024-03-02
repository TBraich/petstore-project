package petstore.user.controller;

import static petstore.common.dto.RestApiHeader.EVENT_TIME;
import static petstore.common.dto.RestApiHeader.ONE_TIME_ID;
import static petstore.common.utils.CommonFunctions.prepareResponse;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petstore.user.dto.user.request.CreateBatchUserRequest;
import petstore.user.dto.user.request.CreateUserRequest;
import petstore.user.service.UserListService;

@RestController
@Validated
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/users/register")
public class UserListApiController {
  private final UserListService userListService;

    @PostMapping
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
}
