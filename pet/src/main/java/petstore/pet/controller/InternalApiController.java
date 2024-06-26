package petstore.pet.controller;

import static java.lang.Integer.parseInt;
import static petstore.common.dto.RestApiHeader.EVENT_TIME;
import static petstore.common.dto.RestApiHeader.ONE_TIME_ID;
import static petstore.common.utils.CommonFunctions.prepareResponse;
import static petstore.common.utils.CommonFunctions.prepareRowBounds;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import petstore.common.validation.IntValue;
import petstore.common.validation.NotBlank;
import petstore.common.validation.Required;
import petstore.pet.dto.request.PetPageRequest;
import petstore.pet.service.PetApiService;
import petstore.pet.service.PetListService;

@RestController
@Log4j2
@RequiredArgsConstructor
@Validated
@RequestMapping("/pet/internal")
public class InternalApiController {
  private final PetApiService petService;
  private final PetListService listService;

  @GetMapping("/{petId}")
  public ResponseEntity<ObjectNode> findById(
      @RequestHeader(name = ONE_TIME_ID) String oneTimeId,
      @RequestHeader(name = EVENT_TIME) String eventTime,
      @Required @PathVariable(value = "petId", required = false) String petId) {
    log.info("START API Find Pet, ID: {} at {}, petId: {}", oneTimeId, eventTime, petId);
    var response = petService.find(oneTimeId, petId);
    return prepareResponse(oneTimeId, response);
  }

  @GetMapping("/list")
  public ResponseEntity<ObjectNode> findByStore(
      @RequestHeader(name = ONE_TIME_ID) String oneTimeId,
      @RequestHeader(name = EVENT_TIME) String eventTime,
      @RequestParam(value = "storeId", required = false) List<String> listStore,
      @RequestParam(value = "categoryId", required = false) List<String> listCategory,
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "status", required = false) String status,
      @NotBlank @IntValue @RequestParam(value = "page", required = false, defaultValue = "0")
          String page,
      @NotBlank @IntValue @RequestParam(value = "size", required = false, defaultValue = "15")
          String size,
      @NotBlank
          @Pattern(regexp = "^(name|storeId|categoryId)$")
          @RequestParam(value = "sort", required = false, defaultValue = "name")
          String sort,
      @NotBlank
          @Pattern(regexp = "(?i)^(asc|desc)$")
          @RequestParam(value = "order", required = false, defaultValue = "ASC")
          String order) {

    // Prepare list request
    var request =
        PetPageRequest.builder()
            .name(StringUtils.isNotBlank(name) ? name : "")
            .categories(ObjectUtils.isNotEmpty(listCategory) ? listCategory : null)
            .stores(ObjectUtils.isNotEmpty(listStore) ? listStore : null)
            .status(StringUtils.isNotBlank(status) ? status : "")
            .page(parseInt(page))
            .size(parseInt(size))
            .sortKey(sort)
            .orderBy(order)
            .rowBounds(prepareRowBounds(page, size))
            .build();

    log.info(
        "START API Find Pets with store, ID: {} at {}, request: {}", oneTimeId, eventTime, request);
    var response = listService.list(oneTimeId, request);
    return prepareResponse(oneTimeId, response);
  }
}
