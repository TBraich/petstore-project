package petstore.userauth.repository;

import java.util.Optional;
import java.util.Set;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import petstore.common.entity.Role;
import petstore.common.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  @NonNull
  Optional<User> findById(@NonNull String id);

  @Query("SELECT roles FROM User WHERE id = :userId")
  Set<Role> findRolesByUserId(@Param("userId") String userId);
}
