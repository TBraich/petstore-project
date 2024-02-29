package petstore.common.service.impl;

import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.json.mgmt.users.User;
import java.util.List;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import petstore.common.service.Auth0Service;

/** Stub for dev environment. */
@Log4j2
@Service
public class Auth0Stub implements Auth0Service {

  @Override
  public ManagementAPI manage() {
    return null;
  }

  @Override
  public List<User> listByEmail(String email) {
    return List.of();
  }

  @Override
  public String createNewUser(petstore.common.entity.User user) {
    return UUID.randomUUID().toString();
  }

  @Override
  public String deleteUser(String userId) {
    return userId;
  }
}
