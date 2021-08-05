package uk.gov.mca.beacons.api.exceptions;

import org.springframework.validation.Errors;

public class BeaconsValidationException extends RuntimeException {

  private final Errors errors;

  public BeaconsValidationException(Errors errors) {
    this.errors = errors;
  }

  public Errors getErrors() {
    return errors;
  }
}
