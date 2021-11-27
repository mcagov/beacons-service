package uk.gov.mca.beacons.api.webapp;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.mca.beacons.api.WebMvcTestConfiguration;

@WebMvcTest(controllers = FeedbackFormController.class)
@AutoConfigureMockMvc
@Import(WebMvcTestConfiguration.class)
public class FeedbackFormControllerUnitTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testFeedbackFormPage() throws Exception {
    mockMvc
      .perform(get("/help/feedback"))
      .andExpect(status().isOk())
      .andExpect(view().name("feedback-form"))
      .andExpect(
        content().string(containsString("Give feedback on Register a beacon"))
      );
  }
}
