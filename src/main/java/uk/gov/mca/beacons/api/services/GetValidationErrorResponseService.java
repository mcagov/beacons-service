package uk.gov.mca.beacons.api.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import uk.gov.mca.beacons.api.dto.ErrorResponseDTO;
import uk.gov.mca.beacons.api.validation.ValidationError;

@Service
public class GetValidationErrorResponseService {

  public ErrorResponseDTO fromBindingErrors(Errors errors) {
    final var errorResponse = new ErrorResponseDTO();

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
