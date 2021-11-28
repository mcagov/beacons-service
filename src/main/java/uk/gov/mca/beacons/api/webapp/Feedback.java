package uk.gov.mca.beacons.api.webapp;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Feedback {

  private UUID id;

  @NotNull(message = "Select a satisfaction rating")
  private SatisfactionRating satisfactionRating;

  @NotNull(message = "Tell us how we could improve this service")
  private String howCouldWeImproveThisService;
}
