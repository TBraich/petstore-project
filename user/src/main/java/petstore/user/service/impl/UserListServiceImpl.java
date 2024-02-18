package petstore.user.service.impl;

import static petstore.common.utils.KafkaUtils.USERS_TOPIC;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import petstore.user.dto.common.BasicResponse;
import petstore.user.dto.user.request.CreateBatchUserRequest;
import petstore.user.service.UserListService;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserListServiceImpl implements UserListService {
  private final KafkaProducer<String, Object> kafkaProducer;

  @Override
  public BasicResponse create(String oneTimeId, CreateBatchUserRequest request) {
    // Separate to insert with 10 users per partition
    var listBatch = Lists.partition(request.getUsers(), 10);

    // Prepare and sending records
    for (int i = 0; i < listBatch.size(); i++) {
      String key = "id-" + i;

      ProducerRecord<String, Object> userBatchRecords =
          new ProducerRecord<>(USERS_TOPIC, key, listBatch.get(i));

      kafkaProducer.send(
          userBatchRecords,
          (recordMetadata, e) -> {
            if (ObjectUtils.isNotEmpty(e)) {
              log.error(
                  "Failed sending records for {}, key: {} - Error: {}",
                  recordMetadata.topic(),
                  key,
                  e.getMessage());
            } else {
              log.info(
                  "Send records successfully! key: {}, partition: {}",
                  key,
                  recordMetadata.partition());
            }
          });
    }

    kafkaProducer.flush();

    kafkaProducer.close();

    return BasicResponse.builder().result(HttpStatus.OK.name()).build();
  }
}
