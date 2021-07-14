package uk.gov.mca.beacons.api.controllers;

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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.mca.beacons.api.WebMvcTestConfiguration;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.dto.NoteDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.mappers.NoteMapper;
import uk.gov.mca.beacons.api.services.GetUserService;
import uk.gov.mca.beacons.api.services.NoteService;

@WebMvcTest(controllers = NoteController.class)
@AutoConfigureMockMvc
@Import(WebMvcTestConfiguration.class)
class NoteControllerUnitTest {

  private final UUID noteId = UUID.randomUUID();
  private final Note note = new Note();

  @Autowired
  private MockMvc mvc;

  @MockBean
  private NoteService noteService;

  @MockBean
  private NoteMapper noteMapper;

  @MockBean
  private GetUserService getUserService;

  @BeforeEach
  public final void before() {
    note.setId(noteId);
  }

  @Nested
  class RequestCreateNote {

    @Test
    void shouldReturn201IfSuccessful() throws Exception {
      final WrapperDTO<NoteDTO> newNoteDTO = new WrapperDTO<>();
      final String newNoteRequest = new ObjectMapper()
        .writeValueAsString(newNoteDTO);
      mvc
        .perform(
          post("/note")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newNoteRequest)
        )
        .andExpect(status().isCreated());
    }
  }
}
