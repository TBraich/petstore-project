package petstore.pet.service;

import petstore.common.dto.PageResponse;
import petstore.pet.dto.request.PetPageRequest;
import petstore.pet.dto.response.PetDetailResponse;

public interface PetListService {
  PageResponse<PetDetailResponse> list(String oneTimeId, PetPageRequest request);
}
