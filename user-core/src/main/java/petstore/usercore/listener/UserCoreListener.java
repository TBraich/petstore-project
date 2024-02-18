package petstore.usercore.listener;

import static petstore.common.utils.KafkaUtils.USERS_TOPIC;
import static petstore.common.utils.KafkaUtils.USER_GROUP;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class UserCoreListener {

  @KafkaListener(topics = USERS_TOPIC, groupId = USER_GROUP)
  public void consume(String message) {
    log.info("Found message: " + message);
  }
}
