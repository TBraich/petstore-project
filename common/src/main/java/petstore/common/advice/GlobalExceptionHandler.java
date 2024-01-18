package petstore.common.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static petstore.common.utils.CommonFunctions.convertObjectToString;

@Log4j2
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ObjectMapper objectMapper;

    @ExceptionHandler({ConstraintViolationException.class, Exception.class})
    public ResponseEntity<Object> handleConstraintViolationException(
            final Exception ex, final WebRequest request) {
        log.error("Exception occurred: {}", ex.getMessage());
        String oneTimeId = isBlank(request.getHeader("oneTimeId")) ? "" : request.getHeader("oneTimeId");
        var response = prepareResponse(oneTimeId, ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    private ObjectNode prepareResponse(String oneTimeUid, Object body) {
        var responseBody = objectMapper.createObjectNode();
        responseBody.put("requestId", oneTimeUid);
        responseBody.put("data", convertObjectToString(body));
        return responseBody;
    }
}
