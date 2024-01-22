package petstore.user.dto.user.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private LocalDate birthday;
    private String phone;
}
