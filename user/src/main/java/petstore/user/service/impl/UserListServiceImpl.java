package petstore.user.service.impl;


import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import petstore.user.dto.common.BasicResponse;
import petstore.user.dto.user.request.CreateBatchUserRequest;
import petstore.user.service.UserListService;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserListServiceImpl implements UserListService {
  private final KafkaTemplate<String, Object> kafkaProducer;

  @Value("${kafka.topics.user:users}")
  private String topic;

  @Override
  public BasicResponse create(String oneTimeId, CreateBatchUserRequest request) {
    // Separate to insert with 10 users per partition
    var listBatch = Lists.partition(request.getUsers(), 10);

    // Prepare and sending records
    for (int i = 0; i < listBatch.size(); i++) {
      String key = "id-" + i;

      ProducerRecord<String, Object> userBatchRecords =
          new ProducerRecord<>(topic, key, listBatch.get(i));

      var future = kafkaProducer.send(userBatchRecords);
      future.whenComplete(
          (res, err) -> {
            if (ObjectUtils.isNotEmpty(err)) {
              log.error(
                  "Failed sending records for {}, key: {} - Error: {}",
                  topic,
                  key,
                  err.getMessage());
            }
          });
    }

    return BasicResponse.builder().result(HttpStatus.OK.name()).build();
  }
}
