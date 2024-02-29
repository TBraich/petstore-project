package petstore.common.service;

import petstore.common.entity.AuthorizeInfo;

public interface AuthGateway {
  AuthorizeInfo authorize(String userId);
}
