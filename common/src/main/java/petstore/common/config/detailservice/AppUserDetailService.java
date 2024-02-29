package petstore.common.config.detailservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import petstore.common.service.AuthGateway;

@Log4j2
@Service
@RequiredArgsConstructor
public class AppUserDetailService implements UserDetailsService {
  private final AuthGateway authGateway;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    // TODO: implement checking with cache before querying DB

    var user = authGateway.authorize(userId);

    return new User(
        user.getId(),
        "default",
        user.getRoles().stream().map(SimpleGrantedAuthority::new).toList());
  }
}
