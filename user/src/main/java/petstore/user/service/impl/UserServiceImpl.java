package petstore.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import petstore.user.dto.request.CreateUserRequest;
import petstore.user.dto.request.UpdateUserRequest;
import petstore.user.dto.response.UserResponse;
import petstore.user.service.UserService;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    @Override
    public UserResponse create(String oneTimeId, CreateUserRequest request) {
        return null;
    }

    @Override
    public UserResponse update(String oneTimeId, String userId, UpdateUserRequest request) {
        return null;
    }

    @Override
    public UserResponse find(String oneTimeId, String userId) {
        return null;
    }

    @Override
    public UserResponse delete(String oneTimeId, String userId) {
        return null;
    }
}
