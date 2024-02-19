package petstore.user.repository;

import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petstore.common.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findById(@NonNull String id);

  Optional<User> findByEmail(String email);

  Optional<User> findByFirstName(String firstName);
}
