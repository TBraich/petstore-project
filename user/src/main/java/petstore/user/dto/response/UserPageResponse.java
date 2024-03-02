package petstore.user.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPageResponse {
  private boolean hasNext;
  private int totalPage;
  private int currentPage;
  private int pageSize;

  private List<UserDetailResponse> records;
}
