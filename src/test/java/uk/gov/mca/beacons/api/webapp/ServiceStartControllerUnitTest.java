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

@WebMvcTest(controllers = ServiceStartController.class)
@AutoConfigureMockMvc
@Import(WebMvcTestConfiguration.class)
public class ServiceStartControllerUnitTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testServiceStartPage() throws Exception {
    mockMvc
      .perform(get("/"))
      .andExpect(status().isOk())
      .andExpect(view().name("service-start"))
      .andExpect(
        content()
          .string(
            containsString("Maritime and Coastguard Agency: Register a beacon")
          )
      );
  }
}
