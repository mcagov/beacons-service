package uk.gov.mca.beacons.service.accounts;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.mca.beacons.service.dto.AccountHolderDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.mappers.AccountHolderMapper;
import uk.gov.mca.beacons.service.model.AccountHolder;

@WebMvcTest(controllers = AccountHolderController.class)
@AutoConfigureMockMvc
class AccountHolderControllerUnitTest {

  private final UUID accountHolderId = UUID.fromString(
    "432e083d-7bd8-402b-9520-05da24ad143f"
  );
  private final String authId = "04c4dbf3-ca7c-4df9-98b6-fb2ccf422526";
  private final AccountHolder accountHolder = new AccountHolder();

  @Autowired
  private MockMvc mvc;

  @MockBean
  private GetAccountHolderByAuthIdService getAccountHolderByAuthIdService;

  @MockBean
  private CreateAccountHolderService createAccountHolderService;

  @MockBean
  private AccountHolderMapper accountHolderMapper;

  @BeforeEach
  public final void before() {
    accountHolder.setId(accountHolderId);
    accountHolder.setAuthId(authId);
  }

  @Test
  void requestAccountHolderIdByAuthId_shouldRequestAccountHolderIdFromServiceByAuthId()
    throws Exception {
    given(getAccountHolderByAuthIdService.execute(authId))
      .willReturn(accountHolder);

    mvc.perform(
      get("/account-holder/auth-id/" + authId)
        .contentType(MediaType.APPLICATION_JSON)
    );

    verify(getAccountHolderByAuthIdService, times(1)).execute(authId);
  }

  @Test
  void requestAccountHolderIdByAuthId_shouldReturn200WhenAccountHolderIdFound()
    throws Exception {
    given(getAccountHolderByAuthIdService.execute(authId))
      .willReturn(accountHolder);

    mvc
      .perform(
        get("/account-holder/auth-id/" + authId)
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());
  }

  @Test
  void requestAccountHolderIdByAuthId_shouldReturnTheAccountHolderId()
    throws Exception {
    given(getAccountHolderByAuthIdService.execute(authId))
      .willReturn(accountHolder);

    mvc
      .perform(
        get("/account-holder/auth-id/" + authId)
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(jsonPath("$.id", is(accountHolderId.toString())));
  }

  @Test
  void requestAccountHolderIdByAuthId_shouldReturn404IfAccountHolderNotFound()
    throws Exception {
    given(getAccountHolderByAuthIdService.execute(authId)).willReturn(null);

    mvc
      .perform(
        get("/account-holder/auth-id/" + authId)
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  void requestCreateAccountHolder_shouldReturn201IfSuccessful()
    throws Exception {
    WrapperDTO<AccountHolderDTO> newAccountHolderDTO = new WrapperDTO<>();
    String newAccountHolderRequest = new ObjectMapper()
      .writeValueAsString(newAccountHolderDTO);
    mvc
      .perform(
        post("/account-holder")
          .contentType(MediaType.APPLICATION_JSON)
          .content(newAccountHolderRequest)
      )
      .andExpect(status().isCreated());
  }

  @Test
  void requestCreateAccountHolder_shouldMapDTOToDomainAccountHolder()
    throws Exception {
    WrapperDTO<AccountHolderDTO> newAccountHolderDTO = new WrapperDTO<>();
    String newAccountHolderRequest = new ObjectMapper()
      .writeValueAsString(newAccountHolderDTO);

    mvc.perform(
      post("/account-holder")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newAccountHolderRequest)
    );

    verify(accountHolderMapper, times(1))
      .fromDTO(newAccountHolderDTO.getData());
  }

  @Test
  void requestCreateAccountHolder_shouldCallTheAccountHolderServiceToCreateANewResource()
    throws Exception {
    WrapperDTO<AccountHolderDTO> newAccountHolderDTO = new WrapperDTO<>();
    String newAccountHolderRequest = new ObjectMapper()
      .writeValueAsString(newAccountHolderDTO);
    given(accountHolderMapper.fromDTO(newAccountHolderDTO.getData()))
      .willReturn(accountHolder);

    mvc.perform(
      post("/account-holder")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newAccountHolderRequest)
    );

    verify(createAccountHolderService, times(1)).execute(accountHolder);
  }
}
