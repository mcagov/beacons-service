package uk.gov.mca.beacons.service.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.mca.beacons.service.accounts.*;
import uk.gov.mca.beacons.service.beacons.BeaconsResponseFactory;
import uk.gov.mca.beacons.service.dto.AccountHolderDTO;
import uk.gov.mca.beacons.service.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.mappers.AccountHolderMapper;
import uk.gov.mca.beacons.service.mappers.BeaconPersonMapper;
import uk.gov.mca.beacons.service.model.AccountHolder;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconPerson;

import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BeaconPersonController.class)
@AutoConfigureMockMvc
class BeaconPersonControllerUnitTest {

  private final UUID beaconPersonId = UUID.fromString(
          "432e083d-7bd8-402b-9520-05da24ad143f"
  );

  private final BeaconPerson beaconPerson = new BeaconPerson();

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CreateBeaconPersonService createBeaconPersonService;

  @MockBean
  private BeaconPersonMapper beaconPersonMapper;

  @BeforeEach
  public final void before() {
    beaconPerson.setId(beaconPersonId);
  }

  @Nested
  class RequestCreateBeaconPerson {

    @Test
    void shouldReturn201IfSuccessful() throws Exception {
      WrapperDTO<BeaconPersonDTO> newBeaconPersonDTO = new WrapperDTO<>();
      String newBeaconPersonRequest = new ObjectMapper()
              .writeValueAsString(newBeaconPersonDTO);
      mvc
              .perform(
                      post("/person")
                              .contentType(MediaType.APPLICATION_JSON)
                              .content(newBeaconPersonRequest)
              )
              .andExpect(status().isCreated());
    }

    @Test
    void shouldMapDTOToDomainAccountHolder() throws Exception {
      WrapperDTO<BeaconPersonDTO> newBeaconPersonDTO = new WrapperDTO<>();
      String newBeaconPersonRequest = new ObjectMapper()
              .writeValueAsString(newBeaconPersonDTO);

      mvc.perform(
              post("/person")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(newBeaconPersonRequest)
      );

      verify(beaconPersonMapper, times(1))
              .fromDTO(newBeaconPersonDTO.getData());
    }

    @Test
    void shouldCallTheAccountHolderServiceToCreateANewResource()
            throws Exception {
      WrapperDTO<BeaconPersonDTO> newBeaconPersonDTO = new WrapperDTO<>();
      String newBeaconPersonRequest = new ObjectMapper()
              .writeValueAsString(newBeaconPersonDTO);
      given(beaconPersonMapper.fromDTO(newBeaconPersonDTO.getData()))
              .willReturn(beaconPerson);

      mvc.perform(
              post("/person")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(newBeaconPersonRequest)
      );

      verify(createBeaconPersonService, times(1)).execute(beaconPerson);
    }
  }
}
