package petstore.common.enums;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PetStatus {
  ORDERED("1"),
  SOLD("2"),
  AVAILABLE("0");

  private String status;

  @SuppressWarnings("unused")
  public static PetStatus get(String petStatus) {
    return Arrays.stream(PetStatus.values())
        .filter(v -> v.status.equals(petStatus))
        .findFirst()
        .orElse(AVAILABLE);
  }

  public String status() {
    return status;
  }

  public boolean check(String petStatus) {
    return status.equals(petStatus);
  }
}
