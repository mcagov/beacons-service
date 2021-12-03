package uk.gov.mca.beacons.api.webapp.feedback;

import java.io.IOException;

public interface EmailGateway {
  void send(FeedbackEmail feedbackEmail) throws IOException;
}
