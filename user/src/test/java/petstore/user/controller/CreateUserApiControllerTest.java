package petstore.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static petstore.common.dto.RestApiHeader.EVENT_TIME;
import static petstore.common.dto.RestApiHeader.ONE_TIME_ID;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import petstore.user.dto.request.CreateUserRequest;
import petstore.user.dto.response.UserResponse;
import petstore.user.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class CreateUserApiControllerTest {
  private static final String USER_REGISTER_PATH = "/user/register";
  @Autowired ObjectMapper objectMapper;
  @MockBean UserService service;
  @Autowired MockMvc mockMvc;

  @Test
  @DisplayName("Missing Header : oneTimeId")
  void missingHeaders_oneTimeId() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(USER_REGISTER_PATH)
                .header(EVENT_TIME, "2024-01-01 00:00:00")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        CreateUserRequest.builder()
                            .firstName("SomeOne")
                            .lastName("Cool")
                            .address("Fake address")
                            .email("fakeemail@email.com")
                            .phone("+6566554")
                            .build())))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

    verify(service, never()).create(anyString(), any());
  }

  @Test
  @DisplayName("Missing Header : eventTime")
  void missingHeaders_eventTime() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(USER_REGISTER_PATH)
                .header(ONE_TIME_ID, UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        CreateUserRequest.builder()
                            .firstName("SomeOne")
                            .lastName("Cool")
                            .address("Fake address")
                            .email("fakeemail@email.com")
                            .phone("+6566554")
                            .build())))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

    verify(service, never()).create(anyString(), any());
  }

  @Test
  @Disabled
  @DisplayName("Missing Body : firstName")
  void missingBody_firstName() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(USER_REGISTER_PATH)
                .header(ONE_TIME_ID, UUID.randomUUID())
                .header(EVENT_TIME, "2024-01-01 00:00:00")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        CreateUserRequest.builder()
                            .lastName("Cool")
                            .address("Fake address")
                            .email("fakeemail@email.com")
                            .phone("+6566554")
                            .build())))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

    verify(service, never()).create(anyString(), any());
  }

  @Test
  @Disabled
  @DisplayName("Missing Body : lastName")
  void missingBody_lastName() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(USER_REGISTER_PATH)
                .header(ONE_TIME_ID, UUID.randomUUID())
                .header(EVENT_TIME, "2024-01-01 00:00:00")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        CreateUserRequest.builder()
                            .firstName("SomeOne")
                            .address("Fake address")
                            .email("fakeemail@email.com")
                            .phone("+6566554")
                            .build())))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

    verify(service, never()).create(anyString(), any());
  }

  @Test
  @Disabled
  @DisplayName("Missing Body : email")
  void missingBody_email() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(USER_REGISTER_PATH)
                .header(ONE_TIME_ID, UUID.randomUUID())
                .header(EVENT_TIME, "2024-01-01 00:00:00")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        CreateUserRequest.builder()
                            .firstName("SomeOne")
                            .lastName("Cool")
                            .address("Fake address")
                            .phone("+6566554")
                            .build())))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

    verify(service, never()).create(anyString(), any());
  }

  @Test
  @DisplayName("Normal case")
  void normalCase() throws Exception {
    when(service.create(anyString(), any()))
        .thenReturn(
            UserResponse.builder().id(UUID.randomUUID().toString()).userName("SomeOne").build());

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(USER_REGISTER_PATH)
                .header(ONE_TIME_ID, UUID.randomUUID())
                .header(EVENT_TIME, "2024-01-01 00:00:00")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        CreateUserRequest.builder()
                            .firstName("SomeOne")
                            .lastName("Cool")
                            .email("fakeemail@email.com")
                            .address("Fake address")
                            .phone("+6566554")
                            .build())))
        .andExpect(MockMvcResultMatchers.status().isOk());

    verify(service, atMostOnce()).create(anyString(), any());
  }
}
