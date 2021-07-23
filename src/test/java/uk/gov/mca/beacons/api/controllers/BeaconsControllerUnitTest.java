package uk.gov.mca.beacons.api.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.mca.beacons.api.WebMvcTestConfiguration;
import uk.gov.mca.beacons.api.domain.BackOfficeUser;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.domain.User;
import uk.gov.mca.beacons.api.dto.BeaconDTO;
import uk.gov.mca.beacons.api.dto.DeleteBeaconRequestDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.mappers.BeaconMapper;
import uk.gov.mca.beacons.api.mappers.BeaconsResponseFactory;
import uk.gov.mca.beacons.api.mappers.NoteMapper;
import uk.gov.mca.beacons.api.services.BeaconsService;
import uk.gov.mca.beacons.api.services.DeleteBeaconService;
import uk.gov.mca.beacons.api.services.GetUserService;
import uk.gov.mca.beacons.api.services.NoteService;

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

  @MockBean
  private DeleteBeaconService deleteBeaconService;

  @MockBean
  private NoteService noteService;

  @MockBean
  private NoteMapper noteMapper;

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

  @Nested
  class DeleteBeacon {

    private UUID beaconId;
    private UUID userId;

    @BeforeEach
    public void init() {
      beaconId = UUID.randomUUID();
      userId = UUID.randomUUID();
    }

    @Test
    void shouldDeleteTheBeacon() throws Exception {
      final var deleteBeaconRequest = DeleteBeaconRequestDTO
        .builder()
        .beaconId(beaconId)
        .reason("Unused on my boat anymore")
        .userId(userId)
        .build();

      mockMvc
        .perform(
          patch("/beacons/" + beaconId + "/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content(OBJECT_MAPPER.writeValueAsString(deleteBeaconRequest))
        )
        .andExpect(status().isOk());

      final var deleteBeaconRequestCaptor = ArgumentCaptor.forClass(
        DeleteBeaconRequestDTO.class
      );
      then(deleteBeaconService)
        .should(times(1))
        .delete(deleteBeaconRequestCaptor.capture());
      final var deleteBeaconRequestValue = deleteBeaconRequestCaptor.getValue();
      assertThat(deleteBeaconRequestValue.getBeaconId(), is(beaconId));
      assertThat(
        deleteBeaconRequestValue.getReason(),
        is("Unused on my boat anymore")
      );
      assertThat(deleteBeaconRequestValue.getUserId(), is(userId));
    }

    @Test
    void shouldNotAcceptTheJsonPayloadIfTheReasonIsNull() throws Exception {
      final var deleteBeaconRequest = DeleteBeaconRequestDTO
        .builder()
        .beaconId(beaconId)
        .userId(userId)
        .build();

      mockMvc
        .perform(
          patch("/beacons/" + beaconId + "/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content(OBJECT_MAPPER.writeValueAsString(deleteBeaconRequest))
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors.length()", is(1)))
        .andExpect(jsonPath("$.errors[0].field", is("reason")))
        .andExpect(
          jsonPath(
            "$.errors[0].description",
            is("Reason for deleting a beacon must be defined")
          )
        );
      then(deleteBeaconService).should(never()).delete(deleteBeaconRequest);
    }

    @Test
    void shouldNotDeleteTheBeaconIfTheIdInThePathDoesNotMatchTheRequestBody()
      throws Exception {
      final var differentBeaconId = UUID.randomUUID();
      final var deleteBeaconRequest = DeleteBeaconRequestDTO
        .builder()
        .beaconId(beaconId)
        .reason("Unused on my boat anymore")
        .userId(userId)
        .build();

      mockMvc
        .perform(
          patch("/beacons/" + differentBeaconId + "/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content(OBJECT_MAPPER.writeValueAsString(deleteBeaconRequest))
        )
        .andExpect(status().isBadRequest());
      then(deleteBeaconService).should(never()).delete(deleteBeaconRequest);
    }
  }

  @Nested
  class GetNotes {

    @Test
    void shouldRequestNotesFromNoteServiceByBeaconId() throws Exception {
      UUID beaconId = UUID.randomUUID();
      final Note firstNote = Note.builder().beaconId(beaconId).build();
      final Note secondNote = Note.builder().beaconId(beaconId).build();

      final List<Note> foundNotes = List.of(firstNote, secondNote);

      given(noteService.findAllByBeaconId(beaconId)).willReturn(foundNotes);

      mockMvc
        .perform(
          get("/beacons/" + beaconId + "/notes")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andReturn();

      verify(noteService, times(1)).findAllByBeaconId(beaconId);
    }

    @Test
    void shouldReturn200WhenThereAreNotesForABeaconId() throws Exception {
      UUID beaconId = UUID.randomUUID();
      final Note firstNote = Note.builder().beaconId(beaconId).build();
      final Note secondNote = Note.builder().beaconId(beaconId).build();

      final List<Note> foundNotes = List.of(firstNote, secondNote);

      given(noteService.findAllByBeaconId(beaconId)).willReturn(foundNotes);

      mockMvc
        .perform(
          get("/beacons/" + beaconId + "/notes")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    }

    @Test
    void shouldReturn200WhenThereAreNoNotesForABeaconId() throws Exception {
      UUID beaconId = UUID.randomUUID();

      given(noteService.findAllByBeaconId(beaconId))
        .willReturn(Collections.emptyList());

      mockMvc
        .perform(
          get("/beacons/" + beaconId + "/notes")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    }
  }
}
