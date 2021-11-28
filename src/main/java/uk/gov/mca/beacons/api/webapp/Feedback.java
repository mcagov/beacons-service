package uk.gov.mca.beacons.api.webapp;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class Feedback {

  private UUID id;

  @NotNull(message = "Select a satisfaction rating")
  private SatisfactionRating satisfactionRating;

  @Size(min = 1, message = "Tell us how we could improve this service")
  @Size(max = 1200, message = "Enter fewer than 1200 characters")
  private String howCouldWeImproveThisService;
}
