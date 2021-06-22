package uk.gov.mca.beacons.service.person;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.mca.beacons.service.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.mappers.BeaconPersonMapper;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.owner.CreateOwnerService;

@WebMvcTest(controllers = OwnerControllerUnitTest.class)
@AutoConfigureMockMvc
class OwnerControllerUnitTest {

  private final UUID beaconPersonId = UUID.fromString(
    "432e083d-7bd8-402b-9520-05da24ad143f"
  );

  private final BeaconPerson beaconPerson = new BeaconPerson();

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CreateOwnerService createOwnerService;

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

      verify(createOwnerService, times(1)).execute(beaconPerson);
    }
  }
}
