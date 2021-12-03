package uk.gov.mca.beacons.api.webapp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.mca.beacons.api.WebMvcTestConfiguration;

@WebMvcTest(controllers = CookiePolicyController.class)
@AutoConfigureMockMvc
@Import(WebMvcTestConfiguration.class)
public class CookiePolicyControllerUnitTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testHideCookieBanner_setsACookie() throws Exception {
    mockMvc
      .perform(post("/help/cookies/hide-banner"))
      .andExpect(cookie().value("hide-cookie-banner", "true"));
  }
}
