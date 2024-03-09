package petstore.user.repository;

import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import petstore.common.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  @NonNull
  Optional<User> findById(@NonNull String id);

  Optional<User> findByEmail(String email);

  @Query(
      "SELECT u FROM User u "
          + "WHERE "
          + "id LIKE CONCAT('%', :userInfo, '%') "
          + "OR "
          + "email LIKE CONCAT('%', :userInfo, '%') "
          + "OR "
          + "firstName LIKE CONCAT('%', :userInfo, '%') "
          + "OR "
          + "lastName LIKE CONCAT('%', :userInfo, '%') ")
  List<User> findByInfo(@Param("userInfo") String userInfo);

  @Query(
      "SELECT u FROM User u "
          + "WHERE "
          + "id LIKE CONCAT('%', :userId, '%') "
          + "OR "
          + "email LIKE CONCAT('%', :email, '%') "
          + "OR "
          + "firstName LIKE CONCAT('%', :name, '%') "
          + "OR "
          + "lastName LIKE CONCAT('%', :name, '%') ")
  Page<User> findByPage(
      @Param("userId") String userId,
      @Param("email") String email,
      @Param("name") String name,
      Pageable pageable);

  Optional<User> findByFirstName(String firstName);
}
