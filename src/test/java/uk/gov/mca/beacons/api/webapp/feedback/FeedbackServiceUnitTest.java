package uk.gov.mca.beacons.api.webapp.feedback;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.webapp.GovNotifyGateway;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceUnitTest {

  @InjectMocks
  FeedbackService feedbackService;

  @Mock
  GovNotifyGateway govNotifyGateway;

  @Test
  void test_WhenAskedToRecordFeedback_ThenSendsEmailToRegistryTeam()
    throws Exception {
    Feedback feedback = Feedback
      .builder()
      .satisfactionRating(SatisfactionRating.VERY_SATISFIED)
      .howCouldWeImproveThisService("Insightful feedback")
      .build();

    feedbackService.record(feedback);

    verify(govNotifyGateway, times(1)).sendEmail(any(), any(), any());
  }
}
