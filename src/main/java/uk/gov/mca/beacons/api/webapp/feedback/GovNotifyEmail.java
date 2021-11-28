package uk.gov.mca.beacons.api.webapp.feedback;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GovNotifyEmail {

  private final String templateId;
  private final String to;
  Map<String, String> personalisation;
}
