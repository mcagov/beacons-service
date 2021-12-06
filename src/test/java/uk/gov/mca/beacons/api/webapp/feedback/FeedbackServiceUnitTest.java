package uk.gov.mca.beacons.api.webapp.feedback;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceUnitTest {

  @InjectMocks
  FeedbackService feedbackService;

  @Mock
  EmailGateway emailGateway;

  @Test
  void test_WhenAskedToRecordFeedback_ThenSendsEmailToRegistryTeam()
    throws Exception {
    Feedback feedback = Feedback
      .builder()
      .satisfactionRating(SatisfactionRating.VERY_SATISFIED)
      .howCouldWeImproveThisService("Insightful feedback")
      .build();

    feedbackService.record(feedback);

    verify(emailGateway, times(1))
      .send(
        argThat((FeedbackEmail email) ->
          email.getRecipientEmailAddress().equals("ukbeacons@mcga.gov.uk")
        )
      );
  }

  @Test
  void test_WhenAskedToRecordFeedback_ThenEmailContainsSatisfactionRating()
    throws Exception {
    Feedback feedback = Feedback
      .builder()
      .satisfactionRating(SatisfactionRating.VERY_SATISFIED)
      .howCouldWeImproveThisService("Insightful feedback")
      .build();

    feedbackService.record(feedback);

    verify(emailGateway, times(1))
      .send(
        argThat((FeedbackEmail email) ->
          email
            .getSatisfactionRating()
            .equals(SatisfactionRating.VERY_SATISFIED)
        )
      );
  }

  @Test
  void test_WhenAskedToRecordFeedback_ThenEmailContainsHowCouldWeImproveThisService()
    throws Exception {
    Feedback feedback = Feedback
      .builder()
      .satisfactionRating(SatisfactionRating.VERY_SATISFIED)
      .howCouldWeImproveThisService("Insightful feedback")
      .build();

    feedbackService.record(feedback);

    verify(emailGateway, times(1))
      .send(
        argThat((FeedbackEmail email) ->
          email.getHowCouldWeImproveThisService().equals("Insightful feedback")
        )
      );
  }
}
