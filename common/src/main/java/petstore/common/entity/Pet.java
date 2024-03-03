package petstore.common.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {
  private String id;
  private String name;
  private String storeId;
  private String categoryId;
  private String status;
  private LocalDate birthday;
}
