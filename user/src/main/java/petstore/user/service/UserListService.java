package petstore.user.service;

import petstore.common.dto.BasicResponse;
import petstore.common.dto.PageResponse;
import petstore.user.dto.request.CreateBatchUserRequest;
import petstore.user.dto.request.UserPageRequest;
import petstore.user.dto.response.UserDetailResponse;

public interface UserListService {
  PageResponse<UserDetailResponse> list(String oneTimeId, UserPageRequest request);

  BasicResponse create(String oneTimeId, CreateBatchUserRequest request);
}
