package uk.gov.mca.beacons.api.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import uk.gov.mca.beacons.api.validation.ValidationError;

@Getter
public class ErrorResponseDTO {

  private final List<ValidationError> errors = new ArrayList<>();

  public void addValidationError(ValidationError error) {
    errors.add(error);
  }
}
