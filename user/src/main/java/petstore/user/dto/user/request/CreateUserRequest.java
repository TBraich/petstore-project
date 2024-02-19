package petstore.user.dto.user.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CreateUserRequest {
  @Required private String firstName;
  @Required private String lastName;
  @Required private String email;
  private String address;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate birthday;

  private String phone;
}
