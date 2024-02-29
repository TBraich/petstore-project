package petstore.common.service;

import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.json.mgmt.users.User;
import java.util.List;

public interface Auth0Service {
  ManagementAPI manage();

  List<User> listByEmail(String email);

  String createNewUser(petstore.common.entity.User user);

  String deleteUser(String userId);
}
