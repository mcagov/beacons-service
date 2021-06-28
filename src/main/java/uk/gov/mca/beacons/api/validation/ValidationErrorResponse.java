package uk.gov.mca.beacons.api.validation;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ValidationErrorResponse {

  private final List<ValidationError> errors = new ArrayList<>();
  private final String errorSummary;

  public ValidationErrorResponse(String errorSummary) {
    this.errorSummary = errorSummary;
  }

  public void addValidationError(ValidationError error) {
    errors.add(error);
  }
}
