package petstore.user.dto.request;

import jakarta.annotation.Nonnull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {
    @Nonnull private String firstName;
    @Nonnull private String lastName;
    @Nonnull private String email;
    @Nonnull private String address;
    @Nonnull private LocalDate birthday;
    @Nonnull private String phone;
}
