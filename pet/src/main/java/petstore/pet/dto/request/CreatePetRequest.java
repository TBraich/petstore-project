package petstore.pet.dto.request;

import static petstore.common.utils.CommonFunctions.generatePetId;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import petstore.common.validation.Required;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CreatePetRequest {
  private String id;
  @Required private String storeId;
  @Required private String name;
  @Required private String categoryId;
  @Required private String status;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate birthday;

  /**
   * Generate petId by storeId and categoryId.
   *
   * @return Make birthday currentDate value.
   */
  public CreatePetRequest prepareId() {
    if (StringUtils.isBlank(id)) {
      this.id = generatePetId(this.storeId, this.categoryId);
    }

    return this;
  }

  /**
   * Check if request added birthday or not.
   *
   * @return Make birthday currentDate value.
   */
  public CreatePetRequest checkBirthday() {
    if (ObjectUtils.isEmpty(birthday)) {
      this.birthday = LocalDate.now();
    }

    return this;
  }
}
