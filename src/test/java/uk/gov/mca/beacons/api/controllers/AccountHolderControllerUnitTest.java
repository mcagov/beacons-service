package uk.gov.mca.beacons.api.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.mca.beacons.api.WebMvcTestConfiguration;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.dto.AccountHolderDTO;
import uk.gov.mca.beacons.api.dto.CreateAccountHolderRequest;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.mappers.AccountHolderMapper;
import uk.gov.mca.beacons.api.mappers.BeaconsResponseFactory;
import uk.gov.mca.beacons.api.services.AccountHolderService;
import uk.gov.mca.beacons.api.services.GetBeaconsByAccountHolderIdService;

@WebMvcTest(controllers = AccountHolderController.class)
@AutoConfigureMockMvc
@Import(WebMvcTestConfiguration.class)
class AccountHolderControllerUnitTest {

  private final UUID accountHolderId = UUID.fromString(
    "432e083d-7bd8-402b-9520-05da24ad143f"
  );
  private final String authId = "04c4dbf3-ca7c-4df9-98b6-fb2ccf422526";
  private final AccountHolder accountHolder = new AccountHolder();
  private final CreateAccountHolderRequest createAccountHolderRequest = new CreateAccountHolderRequest();

  @Autowired
  private MockMvc mvc;

  @MockBean
  private AccountHolderService accountHolderService;

  @MockBean
  private AccountHolderMapper accountHolderMapper;

  @MockBean
  private GetBeaconsByAccountHolderIdService getBeaconsByAccountHolderIdService;

  @MockBean
  private BeaconsResponseFactory responseFactory;

  @BeforeEach
  public final void before() {
    accountHolder.setId(accountHolderId);
    accountHolder.setAuthId(authId);
  }

  @Nested
  class RequestAccountHolderByAuthId {

    @Test
    void shouldRequestAccountHolderIdFromServiceByAuthId() throws Exception {
      given(accountHolderService.getByAuthId(authId)).willReturn(accountHolder);

      mvc.perform(
        get("/spring-api/account-holder/auth-id/" + authId)
          .contentType(MediaType.APPLICATION_JSON)
      );

      verify(accountHolderService, times(1)).getByAuthId(authId);
    }

    @Test
    void shouldReturn200WhenAccountHolderIdFound() throws Exception {
      given(accountHolderService.getByAuthId(authId)).willReturn(accountHolder);

      mvc
        .perform(
          get("/spring-api/account-holder/auth-id/" + authId)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    }

    @Test
    void shouldReturnTheAccountHolderId() throws Exception {
      given(accountHolderService.getByAuthId(authId)).willReturn(accountHolder);

      mvc
        .perform(
          get("/spring-api/account-holder/auth-id/" + authId)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(jsonPath("$.id", is(accountHolderId.toString())));
    }

    @Test
    void shouldReturn404IfAccountHolderNotFound() throws Exception {
      given(accountHolderService.getByAuthId(authId)).willReturn(null);

      mvc
        .perform(
          get("/spring-api/account-holder/auth-id/" + authId)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound());
    }
  }

  @Nested
  class RequestCreateAccountHolder {

    @Test
    void shouldReturn201IfSuccessful() throws Exception {
      WrapperDTO<AccountHolderDTO> newAccountHolderDTO = new WrapperDTO<>();
      String newAccountHolderRequest = new ObjectMapper()
        .writeValueAsString(newAccountHolderDTO);
      mvc
        .perform(
          post("/spring-api/account-holder")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newAccountHolderRequest)
        )
        .andExpect(status().isCreated());
    }

    @Test
    void shouldMapDTOToCreateAccountHolderRequest() throws Exception {
      WrapperDTO<AccountHolderDTO> newAccountHolderDTO = new WrapperDTO<>();
      String newAccountHolderRequest = new ObjectMapper()
        .writeValueAsString(newAccountHolderDTO);

      mvc.perform(
        post("/spring-api/account-holder")
          .contentType(MediaType.APPLICATION_JSON)
          .content(newAccountHolderRequest)
      );

      verify(accountHolderMapper, times(1))
        .toCreateAccountHolderRequest(newAccountHolderDTO.getData());
    }

    @Test
    void shouldCallTheAccountHolderServiceToCreateANewResource()
      throws Exception {
      WrapperDTO<AccountHolderDTO> newAccountHolderDTO = new WrapperDTO<>();
      String newAccountHolderHttpRequestBody = new ObjectMapper()
        .writeValueAsString(newAccountHolderDTO);
      given(
        accountHolderMapper.toCreateAccountHolderRequest(
          newAccountHolderDTO.getData()
        )
      )
        .willReturn(createAccountHolderRequest);

      mvc.perform(
        post("/spring-api/account-holder")
          .contentType(MediaType.APPLICATION_JSON)
          .content(newAccountHolderHttpRequestBody)
      );

      verify(accountHolderService, times(1)).create(createAccountHolderRequest);
    }
  }

  @Nested
  class RequestGetAccountHolderById {

    @Test
    void shouldRequestAccountHolderFromServiceByAuthId() throws Exception {
      given(accountHolderService.getById(accountHolderId))
        .willReturn(accountHolder);

      mvc.perform(
        get("/spring-api/account-holder/" + accountHolderId)
          .contentType(MediaType.APPLICATION_JSON)
      );

      verify(accountHolderService, times(1)).getById(accountHolderId);
    }

    @Test
    void shouldReturn200WhenAccountHolderIdFound() throws Exception {
      given(accountHolderService.getById(accountHolderId))
        .willReturn(accountHolder);

      mvc
        .perform(
          get("/spring-api/account-holder/" + accountHolderId)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    }

    @Test
    void shouldMapAccountHolderToAWrapperDTO() throws Exception {
      given(accountHolderService.getById(accountHolderId))
        .willReturn(accountHolder);

      mvc.perform(
        get("/spring-api/account-holder/" + accountHolderId)
          .contentType(MediaType.APPLICATION_JSON)
      );

      verify(accountHolderMapper, times(1)).toWrapperDTO(accountHolder);
    }

    @Test
    void shouldReturn404IfAccountHolderNotFound() throws Exception {
      given(accountHolderService.getByAuthId(authId)).willReturn(null);

      mvc
        .perform(
          get("/spring-api/account-holder/" + accountHolderId)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound());
    }
  }

  @Nested
  class RequestGetBeaconsByAccountHolderId {

    @Test
    void shouldReturn200ForAValidAccountHolderIdUUID() throws Exception {
      mvc
        .perform(
          get("/spring-api/account-holder/" + accountHolderId + "/beacons")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    }

    @Test
    void shouldMapBeaconsToAWrapperDTO() throws Exception {
      final var beacons = Collections.singletonList(new Beacon());
      given(getBeaconsByAccountHolderIdService.execute(accountHolderId))
        .willReturn(beacons);

      mvc.perform(
        get("/spring-api/account-holder/" + accountHolderId + "/beacons")
          .contentType(MediaType.APPLICATION_JSON)
      );

      verify(responseFactory, times(1)).buildDTO(beacons);
    }
  }
}
