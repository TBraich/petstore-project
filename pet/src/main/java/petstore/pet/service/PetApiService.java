package petstore.pet.service;

import petstore.pet.dto.request.CreatePetRequest;
import petstore.pet.dto.request.UpdatePetRequest;
import petstore.pet.dto.response.PetDetailResponse;
import petstore.pet.dto.response.PetResponse;

public interface PetApiService {
  PetResponse create(String oneTimeId, CreatePetRequest request);

  PetResponse update(String oneTimeId, String petId, UpdatePetRequest request);

  PetResponse delete(String oneTimeId, String petId);

  PetDetailResponse find(String oneTimeId, String petId);
}
