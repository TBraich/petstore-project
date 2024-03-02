package petstore.user.service;

import petstore.common.dto.BasicResponse;
import petstore.user.dto.request.CreateBatchUserRequest;
import petstore.user.dto.request.UserPageRequest;
import petstore.user.dto.response.UserPageResponse;

public interface UserListService {
  UserPageResponse list(String oneTimeId, UserPageRequest request);

  BasicResponse create(String oneTimeId, CreateBatchUserRequest request);
}
