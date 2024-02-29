package petstore.user.service;

import petstore.common.dto.BasicResponse;
import petstore.user.dto.user.request.CreateBatchUserRequest;

public interface UserListService {
  BasicResponse create(String oneTimeId, CreateBatchUserRequest request);
}
