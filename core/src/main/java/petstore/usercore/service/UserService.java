package petstore.usercore.service;

import petstore.usercore.dto.UserRequest;

import java.util.List;

public interface UserService {
  void create(String oneTimeId, List<UserRequest> request);
}
