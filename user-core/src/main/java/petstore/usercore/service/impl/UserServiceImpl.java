package petstore.usercore.service.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import petstore.common.entity.User;
import petstore.usercore.dto.UserRequest;
import petstore.usercore.repository.UserRepository;
import petstore.usercore.service.UserService;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
  private final ModelMapper modelMapper;
  private final UserRepository userRepository;

  @Transactional
  @Override
  public void create(String oneTimeId, List<UserRequest> request) {
    var newUsers =
        request.stream()
            .filter(
                user -> {
                  var entity = userRepository.findByEmail(user.getEmail());
                  if (entity.isPresent()) {
                    log.warn("User with email {} already exists in System.", user.getEmail());
                    return false;
                  }
                  return true;
                })
            .map(user -> modelMapper.map(user, User.class))
            .toList();

    var user = userRepository.saveAll(newUsers);

    user.forEach(
        u -> log.info("Create users successfully, email: {}, ID: {}", u.getEmail(), u.getId()));
  }
}
