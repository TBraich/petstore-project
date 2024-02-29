package petstore.common.enums;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserRoles {
  GUEST,
  USER,
  ADMIN
}
