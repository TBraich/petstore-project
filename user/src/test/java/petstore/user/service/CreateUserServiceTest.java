package petstore.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import petstore.common.advice.exception.ExistingRecordException;
import petstore.user.dto.user.request.CreateUserRequest;
import petstore.user.entity.User;
import petstore.user.repository.UserRepository;

@SpringBootTest
public class CreateUserServiceTest {
  private static final String ONE_TIME_ID = UUID.randomUUID().toString();
  @Autowired UserService service;
  @Autowired ModelMapper modelMapper;
  @MockBean UserRepository repository;

  @Test
  @DisplayName("Abnormal : Email already existing")
  void abnormal01() {
    when(repository.findByEmail(anyString())).thenReturn(Optional.of(User.builder().build()));

    assertThrows(
        ExistingRecordException.class,
        () ->
            service.create(
                ONE_TIME_ID, CreateUserRequest.builder().email("fakeemail@email.com").build()));

    verify(repository, atMostOnce()).findByEmail(anyString());
    verify(repository, never()).save(any());
  }

  @Test
  @DisplayName("Normal : Save successfully")
  void missingHeaders_oneTimeId() {
    var request =
        CreateUserRequest.builder()
            .firstName("SomeOne")
            .lastName("Cool")
            .address("Fake address")
            .email("fakeemail@email.com")
            .phone("+6566554")
            .build();

    when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
    when(repository.save(any())).thenReturn(modelMapper.map(request, User.class));

    var response = service.create(ONE_TIME_ID, request);
    assertEquals(response.getUserName(), request.getFirstName());

    verify(repository, atMostOnce()).findByEmail(anyString());
    verify(repository, atMostOnce()).save(any());
  }
}
