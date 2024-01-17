package petstore.user.dto.common;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Builder
public class RestApiHeader {
    private String oneTimeId;
    private LocalDateTime requestTime;
}
