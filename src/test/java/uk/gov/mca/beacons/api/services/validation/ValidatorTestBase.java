package uk.gov.mca.beacons.api.services.validation;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.validation.Errors;

public final class ValidatorTestBase {

  public static void assertFieldErrors(
    Errors errors,
    String field,
    String errorCode
  ) {
    final var fieldError = errors.getFieldErrors(field);

    var found = false;
    for (final var error : fieldError) {
      if (errorCode.equals(error.getCode())) {
        found = true;
        break;
      }
    }

    assertTrue(
      found,
      format(
        "Could not find %s error for field %s in %s",
        errorCode,
        field,
        errors
      )
    );
  }

  public static void assertNoFieldErrors(Errors errors, String field) {
    final var fieldError = errors.getFieldErrors(field);
    assertTrue(fieldError.isEmpty());
  }

  public static void assertNoFieldErrors(Errors errors) {
    assertTrue(errors.getFieldErrors().isEmpty(), "Found errors in " + errors);
  }
}