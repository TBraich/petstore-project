package petstore.user.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import petstore.common.advice.exception.ExistingRecordException;
import petstore.common.advice.exception.NotFoundException;
import petstore.common.entity.User;
import petstore.user.dto.request.CreateUserRequest;
import petstore.user.dto.request.UpdateUserRequest;
import petstore.user.dto.response.UserDetailResponse;
import petstore.user.dto.response.UserResponse;
import petstore.user.repository.UserRepository;
import petstore.user.service.UserService;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
  private final ModelMapper modelMapper;
  private final UserRepository userRepository;

  @Transactional
  @Override
  public UserResponse create(String oneTimeId, CreateUserRequest request) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      log.error("User already exists: {}", request.getEmail());
      throw new ExistingRecordException(request.getEmail());
    }
    var user = userRepository.save(modelMapper.map(request, User.class));
    return UserResponse.builder().id(user.getId()).userName(user.getFirstName()).build();
  }

  @Override
  public UserResponse update(String oneTimeId, String userId, UpdateUserRequest request) {
    return null;
  }

  @Override
  public UserDetailResponse find(String oneTimeId, String userInfo) {
    var user =
        userRepository.findByInfo(userInfo).stream()
            .findFirst()
            .orElseThrow(
                () -> {
                  log.error("User not found {}", userInfo);
                  return new NotFoundException(userInfo);
                });

    log.info("Found one user with {}: {}", userInfo, user);

    return modelMapper.map(user, UserDetailResponse.class);
  }

  @Override
  public UserResponse delete(String oneTimeId, String userId) {
    return null;
  }
}
