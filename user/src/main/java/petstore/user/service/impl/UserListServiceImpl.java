package petstore.user.service.impl;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import petstore.common.dto.BasicResponse;
import petstore.user.dto.request.CreateBatchUserRequest;
import petstore.user.dto.request.UserPageRequest;
import petstore.user.dto.response.UserDetailResponse;
import petstore.user.dto.response.UserPageResponse;
import petstore.user.repository.UserRepository;
import petstore.user.service.UserListService;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserListServiceImpl implements UserListService {
  private final KafkaTemplate<String, Object> kafkaProducer;
  private final UserRepository userRepository;
  private final ModelMapper mapper;

  @Value("${kafka.topics.user:users}")
  private String topic;

  @Override
  public UserPageResponse list(String oneTimeId, UserPageRequest request) {
    var users =
        userRepository.findByPage(
            request.getUserId(), request.getEmail(), request.getName(), request.getPageable());

    var records =
        users.getContent().stream().map(u -> mapper.map(u, UserDetailResponse.class)).toList();

    return UserPageResponse.builder()
        .total(users.getTotalPages())
        .current(users.getNumber())
        .size(users.getSize())
        .hasNext(users.hasNext())
        .records(records)
        .build();
  }

  @Override
  public BasicResponse create(String oneTimeId, CreateBatchUserRequest request) {
    // Separate to insert with 10 users per partition
    var listBatch = Lists.partition(request.getUsers(), 10);

    // Prepare and sending records
    for (var batch : listBatch) {
      ProducerRecord<String, Object> userBatchRecords =
          new ProducerRecord<>(topic, oneTimeId, batch);

      var future = kafkaProducer.send(userBatchRecords);
      future.whenComplete(
          (res, err) -> {
            if (ObjectUtils.isNotEmpty(err)) {
              log.error(
                  "Failed sending records for {}, key: {} - Error: {}",
                  topic,
                  oneTimeId,
                  err.getMessage());
            } else {
              log.info("Send message successfully! Response: {}", res.getProducerRecord().key());
            }
          });
    }

    return BasicResponse.builder().result(HttpStatus.OK.name()).build();
  }
}
