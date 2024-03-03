package petstore.pet.repository;

import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import petstore.common.entity.Pet;
import petstore.pet.dto.request.CreatePetRequest;
import petstore.pet.dto.request.UpdatePetRequest;

@Mapper
public interface PetRepository {
  void create(CreatePetRequest request);

  Optional<Pet> findById(String id);

  Optional<Pet> findForUpdate(String id);

  void update(String petId, UpdatePetRequest request);

  void delete(String id);
}
