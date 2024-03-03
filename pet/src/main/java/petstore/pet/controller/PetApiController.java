package petstore.pet.controller;

import static petstore.common.dto.RestApiHeader.EVENT_TIME;
import static petstore.common.dto.RestApiHeader.ONE_TIME_ID;
import static petstore.common.utils.CommonFunctions.prepareResponse;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import petstore.common.validation.Required;
import petstore.pet.dto.request.CreatePetRequest;
import petstore.pet.dto.request.UpdatePetRequest;
import petstore.pet.service.PetApiService;

@RestController
@Log4j2
@RequiredArgsConstructor
@Validated
@RequestMapping("/pet")
public class PetApiController {
  private final PetApiService petService;

  @PostMapping("/register")
  public ResponseEntity<ObjectNode> create(
      @RequestHeader(name = ONE_TIME_ID) String oneTimeId,
      @RequestHeader(name = EVENT_TIME) String eventTime,
      @Valid @RequestBody CreatePetRequest request) {
    log.info("START API Create Pet, ID: {} at {}, Request: {}", oneTimeId, eventTime, request);
    var response = petService.create(oneTimeId, request);
    return prepareResponse(oneTimeId, response);
  }

  @GetMapping("/{petId}")
  public ResponseEntity<ObjectNode> findById(
      @RequestHeader(name = ONE_TIME_ID) String oneTimeId,
      @RequestHeader(name = EVENT_TIME) String eventTime,
      @Required @PathVariable(value = "petId", required = false) String petId) {
    log.info("START API Find Pet, ID: {} at {}, petId: {}", oneTimeId, eventTime, petId);
    var response = petService.find(oneTimeId, petId);
    return prepareResponse(oneTimeId, response);
  }

  // TODO: Implement findByStore in PetListApi
  //  @GetMapping("/store}")
  //  public ResponseEntity<ObjectNode> findByStore(
  //      @RequestHeader(name = ONE_TIME_ID) String oneTimeId,
  //      @RequestHeader(name = EVENT_TIME) String eventTime,
  //      @Required @RequestParam(value = "storeId", required = false) List<String> listStores) {
  //    log.info(
  //        "START API Find Pets with store, ID: {} at {}, from stores: {}",
  //        oneTimeId,
  //        eventTime,
  //        listStores);
  //    var response = userService.find(oneTimeId, userInfo);
  //    return prepareResponse(oneTimeId, response);
  //  }

  @PutMapping("/update")
  public ResponseEntity<ObjectNode> update(
      @RequestHeader(name = ONE_TIME_ID) String oneTimeId,
      @RequestHeader(name = EVENT_TIME) String eventTime,
      @Required @RequestParam(value = "petId", required = false) String petId,
      @Valid @RequestBody UpdatePetRequest request) {
    log.info("START API Update Pet, ID: {} at {}, PetId: {}", oneTimeId, eventTime, request);
    var response = petService.update(oneTimeId, petId, request);
    return prepareResponse(oneTimeId, response);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<ObjectNode> delete(
      @RequestHeader(name = ONE_TIME_ID) String oneTimeId,
      @RequestHeader(name = EVENT_TIME) String eventTime,
      @Required @RequestParam(value = "petId", required = false) String petId) {
    log.info("START API Delete Pet, ID: {} at {}, PetId: {}", oneTimeId, eventTime, petId);
    var response = petService.delete(oneTimeId, petId);
    return prepareResponse(oneTimeId, response);
  }
}
