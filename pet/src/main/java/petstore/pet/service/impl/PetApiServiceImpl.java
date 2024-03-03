package petstore.pet.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petstore.common.advice.exception.NotFoundException;
import petstore.pet.dto.request.CreatePetRequest;
import petstore.pet.dto.request.UpdatePetRequest;
import petstore.pet.dto.response.PetDetailResponse;
import petstore.pet.dto.response.PetResponse;
import petstore.pet.repository.PetRepository;
import petstore.pet.service.PetApiService;

@Service
@Log4j2
@RequiredArgsConstructor
public class PetApiServiceImpl implements PetApiService {
  private final PetRepository petRepository;
  private final ModelMapper mapper;

  @Transactional
  @Override
  public PetResponse create(String oneTimeId, CreatePetRequest request) {
    petRepository.create(request.prepareId().checkBirthday());
    return PetResponse.builder().id(request.getId()).name(request.getName()).build();
  }

  @Transactional
  @Override
  public PetResponse update(String oneTimeId, String petId, UpdatePetRequest request) {
    var pet =
        petRepository
            .findForUpdate(petId)
            .orElseThrow(
                () -> {
                  log.error("Pet not found with id: {}", petId);
                  return new NotFoundException(petId);
                });

    log.info("Found one pet: {}, start updating", pet.getId());
    petRepository.update(pet.getId(), request);

    return PetResponse.builder().id(pet.getId()).name(pet.getName()).build();
  }

  @Transactional
  @Override
  public PetResponse delete(String oneTimeId, String petId) {
    var pet =
        petRepository
            .findForUpdate(petId)
            .orElseThrow(
                () -> {
                  log.error("Pet not found with id: {}", petId);
                  return new NotFoundException(petId);
                });

    log.info("Found one pet: {}, start deleting", pet.getId());
    petRepository.delete(pet.getId());

    return PetResponse.builder().id(pet.getId()).name(pet.getName()).build();
  }

  @Override
  public PetDetailResponse find(String oneTimeId, String petId) {
    var pet =
        petRepository
            .findById(petId)
            .orElseThrow(
                () -> {
                  log.error("Pet not found with id: {}", petId);
                  return new NotFoundException(petId);
                });

    log.info("Found a pet: {}", pet);
    return mapper.map(pet, PetDetailResponse.class);
  }
}
