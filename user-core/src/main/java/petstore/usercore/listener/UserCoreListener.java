package petstore.usercore.listener;

import static petstore.common.utils.KafkaUtils.USERS_TOPIC;
import static petstore.common.utils.KafkaUtils.USER_GROUP;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import petstore.usercore.service.UserService;

@Component
@Log4j2
@RequiredArgsConstructor
public class UserCoreListener {
  private final UserService userService;
  private final ObjectMapper objectMapper;

  @KafkaListener(topics = USERS_TOPIC, groupId = USER_GROUP)
  public void consume(@Payload String message) throws JsonProcessingException {
    log.info("Found message: " + message);
    userService.create(null, objectMapper.readValue(message, new TypeReference<>() {}));
  }
}
