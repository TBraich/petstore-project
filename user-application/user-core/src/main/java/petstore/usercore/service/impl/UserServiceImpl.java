package petstore.usercore.service.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import petstore.common.entity.User;
import petstore.common.service.Auth0Service;
import petstore.usercore.dto.UserRequest;
import petstore.usercore.repository.UserRepository;
import petstore.usercore.service.UserService;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
  private final ModelMapper modelMapper;
  private final UserRepository userRepository;
  private final Auth0Service auth0Service;

  @Transactional
  @Override
  public void create(String oneTimeId, List<UserRequest> request) {
    var newUsers =
        request.stream()
            .filter(this::validate)
            .map(user -> modelMapper.map(user, User.class))
            .peek(auth0Service::createNewUser)
            .toList();

    userRepository.saveAll(newUsers);
  }

  private boolean validate(UserRequest user) {
    var newEmail = user.getEmail();

    var entity = userRepository.findByEmail(newEmail);
    if (entity.isPresent()) {
      log.error("System : User with email {} already exists!", newEmail);
      // ignore and continue processing other email
      return false;
    }

    // Start updating Auth0
    var userPage = auth0Service.listByEmail(newEmail);
    if (!userPage.isEmpty()) {
      log.error("Auth0 : User with email {} already exists!", userPage.get(0).getId());
      // ignore and continue processing other email
      return false;
    }

    return true;
  }
}
