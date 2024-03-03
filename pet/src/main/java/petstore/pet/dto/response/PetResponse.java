package petstore.pet.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PetResponse {
  private String id;
  private String name;
}
