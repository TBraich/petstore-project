package petstore.common.advice;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static petstore.common.utils.CommonFunctions.convertObjectToString;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import petstore.common.advice.exception.ExistingRecordException;

@Log4j2
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
  private final ObjectMapper objectMapper;

  @ExceptionHandler({ConstraintViolationException.class, ServletRequestBindingException.class})
  public ResponseEntity<Object> handleConstraintViolationException(
      final Exception ex, final WebRequest request) {
    log.error("{} occurred: {}", ex.getClass().getName(), ex.getMessage());
    String oneTimeId =
        isBlank(request.getHeader("oneTimeId")) ? "" : request.getHeader("oneTimeId");
    var response = prepareResponse(oneTimeId, ex.getMessage());
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleUnexpectedException(
      final Exception ex, final WebRequest request) {
    log.error("{} occurred: {}", ex.getClass().getName(), ex.getMessage());
    String oneTimeId =
        isBlank(request.getHeader("oneTimeId")) ? "" : request.getHeader("oneTimeId");
    var response = prepareResponse(oneTimeId, ex.getMessage());
    return ResponseEntity.internalServerError().body(response);
  }

  @ExceptionHandler({ExistingRecordException.class})
  public ResponseEntity<Object> handleInvalidRecordException(
      final Exception ex, final WebRequest request) {
    log.error("{} occurred: {}", ex.getClass().getName(), ex.getMessage());
    String oneTimeId =
        isBlank(request.getHeader("oneTimeId")) ? "" : request.getHeader("oneTimeId");
    var response = prepareResponse(oneTimeId, ex.getMessage());
    return ResponseEntity.unprocessableEntity().body(response);
  }

  private ObjectNode prepareResponse(String oneTimeUid, Object body) {
    var responseBody = objectMapper.createObjectNode();
    responseBody.put("requestId", oneTimeUid);
    responseBody.put("data", convertObjectToString(body));
    return responseBody;
  }
}
