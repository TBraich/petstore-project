package petstore.userauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import petstore.common.validation.Required;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
  @Required private String email;
  @Required private String password;
}
