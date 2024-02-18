package petstore.user.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;
import petstore.user.dto.common.BasicResponse;
import petstore.user.dto.user.request.CreateBatchUserRequest;
import petstore.user.service.UserListService;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserListServiceImpl implements UserListService {
  private final ObjectMapper objectMapper;
  private final KafkaProducer<String, Object> kafkaProducer;

  public static void main(String[] args) throws JsonProcessingException {
    var properties = new Properties();

    // Normal local kafka
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "0.0.0.0:9092");

    // Producer config
    properties.setProperty(
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

    var kafkaProducer = new KafkaProducer<String, Object>(properties);

    var userBatchRecords =
        new ProducerRecord<String, Object>(
            "users", new ObjectMapper().writeValueAsString(List.of("A", "B", "C")));

    kafkaProducer.send(userBatchRecords);

    kafkaProducer.flush();

    kafkaProducer.close();
  }

  @Override
  public BasicResponse create(String oneTimeId, CreateBatchUserRequest request) {
    ProducerRecord<String, Object> userBatchRecords =
        new ProducerRecord<>("users", request.getUsers());

    kafkaProducer.send(userBatchRecords);

    kafkaProducer.flush();

    kafkaProducer.close();

    return BasicResponse.builder().result("OK").build();
  }
}
