package petstore.user.service;

import petstore.user.dto.user.request.CreateUserRequest;
import petstore.user.dto.user.request.UpdateUserRequest;
import petstore.user.dto.user.response.UserResponse;

public interface UserService {
    UserResponse create(String oneTimeId, CreateUserRequest request);

    UserResponse update(String oneTimeId, String userId, UpdateUserRequest request);

    UserResponse find(String oneTimeId, String userId);

    UserResponse delete(String oneTimeId, String userId);

}
