package uk.gov.mca.beacons.api.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

class LegacyBeaconValidationServiceUnitTest {

  private LegacyBeaconValidationService legacyBeaconValidationService;

  @BeforeEach
  void init() {
    legacyBeaconValidationService = new LegacyBeaconValidationService();
  }

  @Test
  void testSupports() {
    assertTrue(legacyBeaconValidationService.supports(LegacyBeacon.class));
    assertFalse(legacyBeaconValidationService.supports(Beacon.class));
  }

  @Nested
  class Validate {

    @Test
    void shouldHaveErrorsIfTheHexIdIsNotDefined() {
      final var beacon = LegacyBeacon
        .builder()
        .beacon(Map.of("manufacturer", "ASOS"))
        .build();
      final var errors = new BeanPropertyBindingResult(beacon, "legacyBeacon");

      legacyBeaconValidationService.validate(beacon, errors);

      assertFieldErrors(errors, "beacon[hexId]", "field.required");
    }

    @Test
    void shouldNotHaveAnyErrorsIfTheHexIdIsDefined() {
      final var beacon = LegacyBeacon
        .builder()
        .beacon(Map.of("hexId", "123456789"))
        .build();
      final var errors = new BeanPropertyBindingResult(beacon, "legacyBeacon");

      legacyBeaconValidationService.validate(beacon, errors);

      assertNoFieldErrors(errors, "beacon[hexId]");
    }
  }

  private void assertFieldErrors(
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
      "Could not find " +
      errorCode +
      " error for field " +
      field +
      " in " +
      errors
    );
  }

  private void assertNoFieldErrors(Errors errors, String field) {
    final var fieldError = errors.getFieldErrors(field);

    assertTrue(fieldError.isEmpty());
  }
}
