package petstore.user.dto.request;

import jakarta.annotation.Nullable;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {
    @Nullable private String firstName;
    @Nullable private String lastName;
    @Nullable private String email;
    @Nullable private String address;
    @Nullable private LocalDate birthday;
    @Nullable private String phone;
}
