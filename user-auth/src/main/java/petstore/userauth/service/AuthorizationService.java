package petstore.userauth.service;

import petstore.common.entity.AuthorizeInfo;

public interface AuthorizationService {
  AuthorizeInfo authorize(String oneTimeId, String userId);
}
