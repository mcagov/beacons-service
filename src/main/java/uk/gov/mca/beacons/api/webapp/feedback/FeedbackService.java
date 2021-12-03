package uk.gov.mca.beacons.api.webapp.feedback;

import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

  private final EmailGateway emailGateway;

  @Autowired
  public FeedbackService(EmailGateway emailGateway) {
    this.emailGateway = emailGateway;
  }

  public void record(Feedback feedback) throws IOException {
    sendEmailToBeaconRegistryTeam(feedback);
  }

  private void sendEmailToBeaconRegistryTeam(Feedback feedback)
    throws IOException {
    emailGateway.send(
      FeedbackEmail
        .builder()
        .recipientEmailAddress("ukbeacons@mcga.gov.uk")
        .referenceId(UUID.randomUUID())
        .satisfactionRating(feedback.getSatisfactionRating())
        .howCouldWeImproveThisService(
          feedback.getHowCouldWeImproveThisService()
        )
        .build()
    );
  }
}
