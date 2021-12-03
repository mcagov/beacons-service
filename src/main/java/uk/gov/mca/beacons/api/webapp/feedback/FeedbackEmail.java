package uk.gov.mca.beacons.api.webapp.feedback;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FeedbackEmail {

  private final String recipientEmailAddress;
  private final UUID referenceId;
  private final SatisfactionRating satisfactionRating;
  private final String howCouldWeImproveThisService;
}
