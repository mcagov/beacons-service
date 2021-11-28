package uk.gov.mca.beacons.api.webapp.feedback;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.mca.beacons.api.WebMvcTestConfiguration;
import uk.gov.mca.beacons.api.webapp.feedback.FeedbackFormController;
import uk.gov.mca.beacons.api.webapp.feedback.FeedbackService;
import uk.gov.mca.beacons.api.webapp.feedback.SatisfactionRating;

@WebMvcTest(controllers = FeedbackFormController.class)
@AutoConfigureMockMvc
@Import(WebMvcTestConfiguration.class)
public class FeedbackFormControllerUnitTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FeedbackService feedbackService;

  @Test
  public void test_WhenUserViewsFeedbackForm_ThenFeedbackFormIsShown()
    throws Exception {
    mockMvc
      .perform(get("/help/feedback"))
      .andExpect(status().isOk())
      .andExpect(view().name("feedback-form"))
      .andExpect(
        content().string(containsString("Give feedback on Register a beacon"))
      );
  }

  @Test
  public void test_GivenUserHasNotProvidedASatisfactionRating_WhenUserSubmitsForm_ThenErrorsAreShown()
    throws Exception {
    mockMvc
      .perform(
        post("/help/feedback")
          .param("satisfactionRating", "")
          .param(
            "howCouldWeImproveThisService",
            "Freetext suggestions for improvements"
          )
      )
      .andExpect(view().name("feedback-form"))
      .andExpect(content().string(containsString("There is a problem")))
      .andExpect(content().string(containsString("satisfaction rating")));

    verifyNoInteractions(feedbackService);
  }

  @Test
  public void test_GivenUserHasNotToldUsHowWeCouldImproveThisService_WhenUserSubmitsForm_ThenErrorsAreShown()
    throws Exception {
    mockMvc
      .perform(
        post("/help/feedback")
          .param(
            "satisfactionRating",
            String.valueOf(SatisfactionRating.NEITHER_SATISFIED_OR_DISSATISFIED)
          )
          .param("howCouldWeImproveThisService", "")
      )
      .andExpect(view().name("feedback-form"))
      .andExpect(content().string(containsString("There is a problem")))
      .andExpect(content().string(containsString("improve this service")));

    verifyNoInteractions(feedbackService);
  }

  @Test
  public void test_GivenUserHasProvidedRequiredInformation_WhenUserSubmitsForm_ThenPassToTheFeedbackService()
    throws Exception {
    mockMvc.perform(
      post("/help/feedback")
        .param(
          "satisfactionRating",
          String.valueOf(SatisfactionRating.NEITHER_SATISFIED_OR_DISSATISFIED)
        )
        .param("howCouldWeImproveThisService", "Insightful feedback")
    );

    verify(feedbackService, times(1))
      .record(
        argThat(
          feedback ->
            feedback.getSatisfactionRating() ==
            SatisfactionRating.NEITHER_SATISFIED_OR_DISSATISFIED &&
            feedback
              .getHowCouldWeImproveThisService()
              .equals("Insightful feedback")
        )
      );
  }

  @Test
  public void test_GivenUserHasProvidedRequiredInformation_WhenUserSubmitsForm_ThenShowASuccessMessage()
    throws Exception {
    mockMvc
      .perform(
        post("/help/feedback")
          .param(
            "satisfactionRating",
            String.valueOf(SatisfactionRating.NEITHER_SATISFIED_OR_DISSATISFIED)
          )
          .param("howCouldWeImproveThisService", "Insightful feedback")
      )
      .andExpect(status().isOk())
      .andExpect(view().name("feedback-received"))
      .andExpect(
        content()
          .string(containsString("Thank you for submitting your feedback"))
      );
  }
}
