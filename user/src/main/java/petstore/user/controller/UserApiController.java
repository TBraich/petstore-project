package petstore.user.controller;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import petstore.user.dto.common.RestApiHeader;
import petstore.user.dto.request.CreateUserRequest;
import petstore.user.dto.request.UpdateUserRequest;
import petstore.user.dto.response.UserResponse;
import petstore.user.service.UserService;
import petstore.user.validation.Required;

@RestController
@Validated
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApiController {
  private final UserService userService;

  @PostMapping
  public UserResponse create(RestApiHeader header, @RequestBody CreateUserRequest request) {
    return userService.create(header.getOneTimeId(), request);
  }

  @GetMapping("/{userInfo}")
  public UserResponse find(RestApiHeader header, @Required @RequestParam("userInfo") String userId) {
//    MissingServletRequestParameterException
    return userService.find(header.getOneTimeId(), userId);
  }

  @PutMapping("/{userInfo}")
  public UserResponse update(
          RestApiHeader header,
          @Nonnull @RequestParam("userInfo") String userId,
          @RequestBody UpdateUserRequest request) {
    return userService.update(header.getOneTimeId(), userId, request);
  }

  @DeleteMapping("/{userInfo}")
  public UserResponse delete(
          RestApiHeader header, @Nonnull @RequestParam("userInfo") String userId) {
    return userService.delete(header.getOneTimeId(), userId);
  }
}
