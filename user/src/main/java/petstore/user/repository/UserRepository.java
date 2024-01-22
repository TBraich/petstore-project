package petstore.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petstore.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findById(String id);

  Optional<User> findByEmail(String email);

  Optional<User> findByFirstName(String firstName);
}
