package petstore.common.advice;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static petstore.common.dto.RestApiHeader.ONE_TIME_ID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.SystemException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import petstore.common.advice.exception.ExistingRecordException;
import petstore.common.advice.exception.ExternalException;
import petstore.common.advice.exception.NotFoundException;

@Log4j2
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
  private final ObjectMapper objectMapper;

  @ExceptionHandler({ConstraintViolationException.class, ServletRequestBindingException.class})
  public ResponseEntity<Object> handleBadRequestException(
      final Exception ex, final WebRequest request) {
    log.error("{} occurred: {}", ex.getClass().getName(), ex.getMessage());
    String oneTimeId =
        isBlank(request.getHeader(ONE_TIME_ID)) ? "" : request.getHeader(ONE_TIME_ID);
    var response = prepareResponse(BAD_REQUEST.value(), oneTimeId, ex.getMessage());
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<Object> handleMethodArgumentNotValidException(
      final MethodArgumentNotValidException ex, final WebRequest request) {
    log.error("{} occurred: {}", ex.getClass().getName(), ex.getMessage());
    String oneTimeId =
        isBlank(request.getHeader(ONE_TIME_ID)) ? "" : request.getHeader(ONE_TIME_ID);
    var error = ex.getBindingResult().getFieldError();
    var response =
        prepareResponse(
            BAD_REQUEST.value(),
            oneTimeId,
            ObjectUtils.isEmpty(error) ? ex.getMessage() : error.getDefaultMessage());
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<Object> handleNotFoundException(
      final Exception ex, final WebRequest request) {
    log.error("{} occurred: {}", ex.getClass().getName(), ex.getMessage());
    String oneTimeId =
        isBlank(request.getHeader(ONE_TIME_ID)) ? "" : request.getHeader(ONE_TIME_ID);
    var response = prepareResponse(NOT_FOUND.value(), oneTimeId, ex.getMessage());
    return ResponseEntity.status(NOT_FOUND).body(response);
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleUnexpectedException(
      final Exception ex, final WebRequest request) {
    log.error("{} occurred: {}", ex.getClass().getName(), ex.getMessage());
    String oneTimeId =
        isBlank(request.getHeader(ONE_TIME_ID)) ? "" : request.getHeader(ONE_TIME_ID);
    var response = prepareResponse(INTERNAL_SERVER_ERROR.value(), oneTimeId, ex.getMessage());
    return ResponseEntity.internalServerError().body(response);
  }

  @ExceptionHandler({ExistingRecordException.class})
  public ResponseEntity<Object> handleInvalidRecordException(
      final Exception ex, final WebRequest request) {
    log.error("{} occurred: {}", ex.getClass().getName(), ex.getMessage());
    String oneTimeId =
        isBlank(request.getHeader(ONE_TIME_ID)) ? "" : request.getHeader(ONE_TIME_ID);
    var response = prepareResponse(NOT_ACCEPTABLE.value(), oneTimeId, ex.getMessage());
    return ResponseEntity.unprocessableEntity().body(response);
  }

  @ExceptionHandler({SystemException.class, ExternalException.class})
  public ResponseEntity<Object> handleSystemException(
      final Exception ex, final WebRequest request) {
    log.error("{} occurred: {}", ex.getClass().getName(), ex.getMessage());
    String oneTimeId =
        isBlank(request.getHeader(ONE_TIME_ID)) ? "" : request.getHeader(ONE_TIME_ID);
    var response = prepareResponse(INTERNAL_SERVER_ERROR.value(), oneTimeId, ex.getMessage());
    return ResponseEntity.internalServerError().body(response);
  }

  private ObjectNode prepareResponse(int statusCode, String oneTimeUid, Object body) {
    var responseBody = objectMapper.createObjectNode();
    responseBody.put("requestId", oneTimeUid);
    responseBody.put("errorCode", String.valueOf(statusCode));
    responseBody.putPOJO("errorReason", body);
    return responseBody;
  }
}
