package petstore.user.config.detailservice;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import petstore.common.advice.exception.NotFoundException;
import petstore.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AppUserDetailService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    // TODO: implement checking with cache before querying DB

    var user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));

    // TODO: implement role checking
    return new User(
        user.getId(),
        "default",
        List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("APP_USER")));
  }
}
