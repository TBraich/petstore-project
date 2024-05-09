package petstore.common.service;

import petstore.common.dto.gw.AuthorizeInfo;

/** Gateway for internal APIs. */
public interface AuthGateway {
  AuthorizeInfo authorize(String userId);
}
