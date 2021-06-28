package uk.gov.mca.beacons.api.validation;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

@Service
public class GetValidationErrorResponseService {

  public ValidationErrorResponse fromBindingErrors(Errors errors) {
    final var errorResponse = new ValidationErrorResponse(
      "Validation failed. " + errors.getErrorCount() + " error(s)"
    );
    for (FieldError fieldError : errors.getFieldErrors()) {
      final var validationError = ValidationError
        .builder()
        .field(fieldError.getField())
        .description(fieldError.getDefaultMessage())
        .build();
      errorResponse.addValidationError(validationError);
    }
    return errorResponse;
  }
}
