package petstore.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommonFunctions {
    public static String convertObjectToString(Object input) {
        try {
            return new ObjectMapper().writeValueAsString(input);
        } catch (JsonProcessingException e) {
            log.warn("Can't convert for value {} to String, error: {}", input, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
