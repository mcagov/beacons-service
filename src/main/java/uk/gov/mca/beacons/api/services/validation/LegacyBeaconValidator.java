package uk.gov.mca.beacons.api.services.validation;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;

/**
 * Validator class that implements Springs {@link Validator} interface for validating a legacy beacon domain object.
 *
 * <p>
 * See Spring Validation Docs: https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#validation
 */
@Service
public class LegacyBeaconValidator implements Validator {

  private static final String FIELD_REQUIRED = "field.required";

  @Override
  public boolean supports(Class<?> clazz) {
    return LegacyBeacon.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(
      errors,
      "beacon[createdDate]",
      FIELD_REQUIRED,
      "Created date must not be null"
    );

    ValidationUtils.rejectIfEmptyOrWhitespace(
      errors,
      "beacon[lastModifiedDate]",
      FIELD_REQUIRED,
      "Last modified date must not be null"
    );
  }
}
