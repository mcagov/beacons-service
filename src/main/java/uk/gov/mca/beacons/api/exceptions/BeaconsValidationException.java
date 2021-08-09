package uk.gov.mca.beacons.api.exceptions;

import org.springframework.validation.Errors;

/**
 * Exception that is handled by the {@link RestResponseEntityExceptionHandler} and returns the error response DTO back to the client
 * based on the validation errors that were raised.
 */
public class BeaconsValidationException extends RuntimeException {

  private final Errors errors;

  public BeaconsValidationException(Errors errors) {
    this.errors = errors;
  }

  public Errors getErrors() {
    return errors;
  }
}
