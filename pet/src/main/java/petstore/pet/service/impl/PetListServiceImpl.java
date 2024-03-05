package petstore.pet.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import petstore.common.dto.PageResponse;
import petstore.pet.dto.request.PetPageRequest;
import petstore.pet.dto.response.PetDetailResponse;
import petstore.pet.repository.PetRepository;
import petstore.pet.service.PetListService;

@Log4j2
@Service
@RequiredArgsConstructor
public class PetListServiceImpl implements PetListService {
  private final PetRepository petRepository;
  private final ModelMapper mapper;

  @Override
  public PageResponse<PetDetailResponse> list(String oneTimeId, PetPageRequest request) {
    // Find totals records + pets
    var rowBounds = request.getRowBounds();
    var pets = petRepository.findList(request, rowBounds);
    var totalRecords = petRepository.findTotal(request);

    // Transform response
    var records = pets.stream().map(p -> mapper.map(p, PetDetailResponse.class)).toList();
    log.info("Found total {} records, records: {}", totalRecords, records);

    return request.preparePageResponse(totalRecords, records);
  }
}
