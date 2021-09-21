package uk.gov.mca.beacons.api.controllers;

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
import uk.gov.mca.beacons.api.dto.DeleteLegacyBeaconRequestDTO;
import uk.gov.mca.beacons.api.services.DeleteLegacyBeaconService;
import uk.gov.mca.beacons.api.services.LegacyBeaconService;
import uk.gov.mca.beacons.api.services.NoteService;

@WebMvcTest(controllers = LegacyBeaconController.class)
@AutoConfigureMockMvc
@Import(WebMvcTestConfiguration.class)
class LegacyBeaconControllerUnitTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LegacyBeaconService legacyBeaconService;

  @MockBean
  private DeleteLegacyBeaconService deleteLegacyBeaconService;

  @MockBean
  private NoteService noteService;

  @Nested
  class DeleteLegacyBeacon {

    private UUID legacyBeaconId;
    private UUID userId;

    @BeforeEach
    void init() {
      legacyBeaconId = UUID.randomUUID();
      userId = UUID.randomUUID();
    }
  }
}
