package petstore.common.config.detailservice;

import static petstore.common.utils.CacheKey.AUTH_ROLE;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import petstore.common.dto.gw.AuthorizeInfo;
import petstore.common.repository.CacheRepository;
import petstore.common.service.AuthGateway;

@Log4j2
@Service
@RequiredArgsConstructor
public class AppUserDetailService implements UserDetailsService {
  private final AuthGateway authGateway;
  private final CacheRepository cacheRepository;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    var authData = cacheRepository.find(AUTH_ROLE, userId, AuthorizeInfo.class);

    if (ObjectUtils.isEmpty(authData)) {
      authData = authGateway.authorize(userId);

      // TODO: implement check path APIs with Role value

      cacheRepository.set(AUTH_ROLE, userId, authData);
    }

    return new User(
        authData.getId(),
        "unused",
        authData.getRoles().stream().map(SimpleGrantedAuthority::new).toList());
  }
}
