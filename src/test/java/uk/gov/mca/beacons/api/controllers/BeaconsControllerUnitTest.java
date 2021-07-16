package uk.gov.mca.beacons.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.mca.beacons.api.WebMvcTestConfiguration;
import uk.gov.mca.beacons.api.dto.BeaconDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.mappers.BeaconMapper;
import uk.gov.mca.beacons.api.mappers.BeaconsResponseFactory;
import uk.gov.mca.beacons.api.services.BeaconsService;

@WebMvcTest(controllers = BeaconsController.class)
@AutoConfigureMockMvc
@Import(WebMvcTestConfiguration.class)
class BeaconsControllerUnitTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BeaconsService beaconsService;

  @MockBean
  private BeaconMapper beaconMapper;

  @MockBean
  private BeaconsResponseFactory beaconsResponseFactory;

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Nested
  class BeaconPatchUpdate {

    private WrapperDTO<BeaconDTO> dto;
    private BeaconDTO beaconDTO;
    private UUID beaconId;
    private Beacon beaconToUpdate;

    @BeforeEach
    void init() {
      dto = new WrapperDTO<>();
      beaconDTO = new BeaconDTO();
      dto.setData(beaconDTO);
      beaconId = UUID.randomUUID();
      beaconDTO.setId(beaconId);
      final var beaconToUpdate = new Beacon();
      beaconToUpdate.setId(beaconId);
    }

    @Test
    void shouldCallThroughToTheBeaconsServiceIfAValidPatchRequest()
      throws Exception {
      given(beaconMapper.fromDTO(any(BeaconDTO.class)))
        .willReturn(beaconToUpdate);

      mockMvc
        .perform(
          patch("/beacons/" + beaconId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(OBJECT_MAPPER.writeValueAsString(dto))
        )
        .andExpect(status().isOk());

      then(beaconsService).should(times(1)).update(beaconId, beaconToUpdate);
    }

    @Test
    void shouldThrowAnErrorIfTheIdWithinTheJsonDoesNotMatchThePathVariable()
      throws Exception {
      beaconDTO.setId(UUID.randomUUID());
      given(beaconMapper.fromDTO(any(BeaconDTO.class)))
        .willReturn(beaconToUpdate);

      mockMvc
        .perform(
          patch("/beacons/" + beaconId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(OBJECT_MAPPER.writeValueAsString(dto))
        )
        .andExpect(status().isConflict());

      then(beaconsService).should(never()).update(beaconId, beaconToUpdate);
    }
  }
}
