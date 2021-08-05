package uk.gov.mca.beacons.api.services;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;

public class LegacyBeaconValidationService implements Validator {

  private static final String FIELD_REQUIRED = "field.required";

  @Override
  public boolean supports(Class<?> clazz) {
    return LegacyBeacon.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(
      errors,
      "beacon[hexId]",
      FIELD_REQUIRED
    );
  }
}
