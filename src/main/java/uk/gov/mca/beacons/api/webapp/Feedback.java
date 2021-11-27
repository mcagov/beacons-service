package uk.gov.mca.beacons.api.webapp;

import java.util.UUID;
import lombok.Data;

@Data
public class Feedback {

  private UUID id;
  private SatisfactionRating satisfactionRating;
  private String howCouldWeImproveThisService;
}
