package petstore.user.service;

import petstore.user.dto.request.CreateUserRequest;
import petstore.user.dto.request.UpdateUserRequest;
import petstore.user.dto.response.UserResponse;

public interface UserService {
    UserResponse create(String oneTimeId, CreateUserRequest request);

    UserResponse update(String oneTimeId, String userId, UpdateUserRequest request);

    UserResponse find(String oneTimeId, String userId);

    UserResponse delete(String oneTimeId, String userId);

}
