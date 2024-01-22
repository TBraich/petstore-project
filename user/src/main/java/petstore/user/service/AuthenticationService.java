package petstore.user.service;

import petstore.user.dto.auth.LoginRequest;
import petstore.user.dto.auth.LoginResponse;

public interface AuthenticationService {
  LoginResponse login(String oneTimeId, LoginRequest request);
}
