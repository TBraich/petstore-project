package petstore.user.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private LocalDate birthday;
    private String phone;
}
