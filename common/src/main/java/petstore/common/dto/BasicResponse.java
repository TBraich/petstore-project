package petstore.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasicResponse {
  private String result;
}
