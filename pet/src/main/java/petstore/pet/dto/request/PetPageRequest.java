package petstore.pet.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import petstore.common.dto.PageResponse;
import petstore.pet.dto.response.PetDetailResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetPageRequest {
  private String name;
  private List<String> stores;
  private List<String> categories;
  private String status;
  private Integer page;
  private Integer size;
  private String sortKey;
  private String orderBy;
  private RowBounds rowBounds;

  public PageResponse<PetDetailResponse> preparePageResponse(
      int totalRecords, List<PetDetailResponse> pets) {
    var totalPage = (int) Math.ceil((double) totalRecords / size);
    return PageResponse.<PetDetailResponse>builder()
        .totalPage(totalPage)
        .currentPage(page + 1)
        .pageSize(pets.size())
        .hasNext(page < totalPage)
        .records(pets)
        .build();
  }
}
