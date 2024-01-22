package petstore.user.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import petstore.common.advice.exception.NotFoundException;
import petstore.user.dto.auth.LoginRequest;
import petstore.user.dto.auth.LoginResponse;
import petstore.user.repository.UserRepository;
import petstore.user.service.AuthenticationService;
import petstore.common.utils.JwtUtil;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  @Override
  public LoginResponse login(String oneTimeId, LoginRequest request) {
    // TODO: implement authentication logic
    // TODO: implement token generating
    log.info("Start generating token: {}, email: {}", oneTimeId, request);

    var user =
        userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new NotFoundException(request.getEmail()));

    return LoginResponse.builder()
        .accessToken(
            jwtUtil.generateToken(
                new User(
                    user.getId(),
                    "default",
                    List.of(
                        new SimpleGrantedAuthority("ADMIN"),
                        new SimpleGrantedAuthority("APP_USER")))))
        .expiredIn(99999L) // TODO: update expired Time
        .build();
  }
}
