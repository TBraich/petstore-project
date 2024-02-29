package petstore.common.service.impl;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.mgmt.users.User;
import java.util.HashMap;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import petstore.common.advice.exception.ExternalException;
import petstore.common.advice.exception.SystemException;
import petstore.common.service.Auth0Service;

@Log4j2
@Service
public class Auth0ServiceImpl implements Auth0Service {
  @Value("${auth0.domain:}")
  private String domain;

  @Value("${auth0.audience:}")
  private String apiAudience;

  @Value("${auth0.client-id:}")
  private String clientId;

  @Value("${auth0.client-secret:}")
  private String clientSecret;

  @Value("${auth0.connection:}")
  private String connection;

  @Override
  public ManagementAPI manage() {
    try {
      var auth = new AuthAPI(domain, clientId, clientSecret);
      TokenHolder holder = auth.requestToken(apiAudience).execute();

      return new ManagementAPI(domain, holder.getAccessToken());
    } catch (Auth0Exception ex) {
      log.error("Some error happened when executing Auth0: {}", ex.getMessage());
      throw new ExternalException(ex.getMessage());
    } catch (Exception ex) {
      log.error("Unexpected error for Auth0: {}", ex.getMessage());
      throw new SystemException(ex.getMessage());
    }
  }

  @Override
  public List<User> listByEmail(String email) {
    try {
      return manage().users().listByEmail(email, null).execute();
    } catch (Auth0Exception ex) {
      log.error("Some error happened when executing Auth0: {}", ex.getMessage());
      throw new ExternalException(ex.getMessage());
    } catch (Exception ex) {
      log.error("Unexpected error for Auth0: {}", ex.getMessage());
      throw new SystemException(ex.getMessage());
    }
  }

  @Override
  public String createNewUser(petstore.common.entity.User entityUser) {
    try {
      var metadata = new HashMap<String, Object>();
      metadata.put("phone_number", entityUser.getPhone());
      metadata.put("first_name", entityUser.getFirstName());
      metadata.put("last_name", entityUser.getLastName());

      var auth0User = new User();
      auth0User.setEmail(entityUser.getEmail());
      auth0User.setEmailVerified(true);
      auth0User.setUserMetadata(metadata);
      auth0User.setConnection(connection);

      // Create and return auth0 user ID
      var response = manage().users().create(auth0User).execute();
      log.info(
          "Create users successfully, email: {}, Id: {}", response.getEmail(), response.getId());
      return response.getId();
    } catch (Auth0Exception ex) {
      log.error("Some error happened when executing Auth0: {}", ex.getMessage());
      throw new ExternalException(ex.getMessage());
    } catch (Exception ex) {
      log.error("Unexpected error for Auth0: {}", ex.getMessage());
      throw new SystemException(ex.getMessage());
    }
  }

  @Override
  public String deleteUser(String userId) {
    try {
      manage().users().delete(userId).execute();
      log.info("Delete users successfully Id: {}", userId);
      return userId;
    } catch (Auth0Exception ex) {
      log.error("Some error happened when executing Auth0: {}", ex.getMessage());
      throw new ExternalException(ex.getMessage());
    } catch (Exception ex) {
      log.error("Unexpected error for Auth0: {}", ex.getMessage());
      throw new SystemException(ex.getMessage());
    }
  }
}
