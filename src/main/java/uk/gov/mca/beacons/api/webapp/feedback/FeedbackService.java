package uk.gov.mca.beacons.api.webapp.feedback;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.webapp.GovNotifyGateway;

@Service
public class FeedbackService {

  private final GovNotifyGateway govNotifyGateway;

  @Autowired
  public FeedbackService(GovNotifyGateway govNotifyGateway) {
    this.govNotifyGateway = govNotifyGateway;
  }

  public void record(Feedback feedback) throws IOException {
    sendEmailToBeaconRegistryTeam(feedback);
  }

  private void sendEmailToBeaconRegistryTeam(Feedback feedback)
    throws IOException {
    govNotifyGateway.sendEmail(
      GovNotifyEmail
        .builder()
        .to("ukbeacons@mcga.gov.uk")
        .personalisation(
          Map.of(
            "referenceId",
            UUID.randomUUID().toString(),
            "satisfactionRating",
            feedback.getSatisfactionRating().getDisplayValue(),
            "howCouldWeImproveThisService",
            feedback.getHowCouldWeImproveThisService()
          )
        )
        .build()
    );
  }
}
