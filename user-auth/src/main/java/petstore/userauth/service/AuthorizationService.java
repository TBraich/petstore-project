package petstore.userauth.service;

import petstore.common.entity.AuthorizeInfo;
import petstore.userauth.dto.LoginRequest;
import petstore.userauth.dto.LoginResponse;

public interface AuthorizationService {
  LoginResponse login(String oneTimeId, LoginRequest request);

  AuthorizeInfo authorize(String oneTimeId, String userId);
}
