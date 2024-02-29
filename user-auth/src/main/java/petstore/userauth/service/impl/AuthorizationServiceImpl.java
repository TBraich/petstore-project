package petstore.userauth.service.impl;

import static petstore.common.enums.UserRoles.GUEST;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import petstore.common.advice.exception.NotFoundException;
import petstore.common.entity.AuthorizeInfo;
import petstore.common.entity.Role;
import petstore.userauth.repository.UserRepository;
import petstore.userauth.service.AuthorizationService;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthorizationServiceImpl implements AuthorizationService {
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  @Override
  public AuthorizeInfo authorize(String oneTimeId, String userId) {
    var user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    log.info("Found user with Id {}: {}", userId, user.getEmail());

    var roles = userRepository.findRolesByUserId(userId);

    var authorizeInfo = modelMapper.map(user, AuthorizeInfo.class);

    // Add GUEST role as a default role
    if (roles.isEmpty()) {
      authorizeInfo.setRoles(List.of(GUEST.name()));
    } else {
      authorizeInfo.setRoles(roles.stream().map(Role::getId).toList());
      if (authorizeInfo.getRoles().contains(GUEST.name())) {
        authorizeInfo.getRoles().add(GUEST.name());
      }
    }

    log.info("Authorization Info: {}", authorizeInfo);

    return authorizeInfo;
  }
}
