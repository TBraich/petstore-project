package petstore.pet.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
@Builder
public class UpdatePetRequest {
  private String storeId;
  private String name;
  private String categoryId;

  @Pattern(
      regexp = "^([012])$",
      message = "Not an Pet status enum (0 : Available) | (1 : SOLD) | (2 : ORDERED)")
  private String status;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate birthday;
}
