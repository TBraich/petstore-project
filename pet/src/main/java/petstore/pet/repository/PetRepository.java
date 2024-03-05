package petstore.pet.repository;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import petstore.common.entity.Pet;
import petstore.pet.dto.request.CreatePetRequest;
import petstore.pet.dto.request.PetPageRequest;
import petstore.pet.dto.request.UpdatePetRequest;

@Mapper
public interface PetRepository {
  void create(CreatePetRequest request);

  Optional<Pet> findById(String id);

  Optional<Pet> findForUpdate(String id);

  void update(String petId, UpdatePetRequest request);

  void delete(String id);

  List<Pet> findList(PetPageRequest request, RowBounds rowBounds);

  int findTotal(PetPageRequest request);
}
