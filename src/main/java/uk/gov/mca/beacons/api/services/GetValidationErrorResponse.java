package uk.gov.mca.beacons.api.services;

import org.springframework.validation.Errors;
import uk.gov.mca.beacons.api.dto.ErrorResponseDTO;

public interface GetValidationErrorResponse {
  ErrorResponseDTO fromBindingErrors(Errors errors);
}
