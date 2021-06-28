package uk.gov.mca.beacons.api.validation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationError {

  private String field;
  private String description;
}
