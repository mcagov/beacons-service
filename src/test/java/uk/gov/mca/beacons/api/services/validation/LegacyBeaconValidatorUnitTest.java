package uk.gov.mca.beacons.api.services.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.mca.beacons.api.services.validation.ValidatorTestBase.assertFieldErrors;
import static uk.gov.mca.beacons.api.services.validation.ValidatorTestBase.assertNoFieldErrors;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

class LegacyBeaconValidatorUnitTest {

  private LegacyBeaconValidator legacyBeaconValidator;

  @BeforeEach
  void init() {
    legacyBeaconValidator = new LegacyBeaconValidator();
  }

  @Test
  void testSupports() {
    assertTrue(legacyBeaconValidator.supports(LegacyBeacon.class));
    assertFalse(legacyBeaconValidator.supports(Beacon.class));
  }

  @Nested
  class Validate {

    @Test
    void shouldHaveErrorsIfTheCreatedDateIsNotDefined() {
      final var beacon = LegacyBeacon
        .builder()
        .beacon(Map.of("manufacturer", "ASOS"))
        .build();
      final var errors = new BeanPropertyBindingResult(beacon, "legacyBeacon");

      legacyBeaconValidator.validate(beacon, errors);

      assertFieldErrors(errors, "beacon[createdDate]", "field.required");
    }

    @Test
    void shouldNotHaveErrorsIfTheCreatedDateIsDefined() {
      final var beacon = LegacyBeacon
        .builder()
        .beacon(Map.of("createdDate", "2020-08-01T21:33:13"))
        .build();
      final var errors = new BeanPropertyBindingResult(beacon, "legacyBeacon");

      legacyBeaconValidator.validate(beacon, errors);

      assertNoFieldErrors(errors, "beacon[createdDate]");
    }

    @Test
    void shouldHaveErrorsIfTheLastModifiedDateIsNotDefined() {
      final var beacon = LegacyBeacon
        .builder()
        .beacon(Map.of("manufacturer", "ASOS"))
        .build();
      final var errors = new BeanPropertyBindingResult(beacon, "legacyBeacon");

      legacyBeaconValidator.validate(beacon, errors);

      assertFieldErrors(errors, "beacon[lastModifiedDate]", "field.required");
    }

    @Test
    void shouldNotHaveErrorsIfTheLastModifiedDateIsDefined() {
      final var beacon = LegacyBeacon
        .builder()
        .beacon(Map.of("lastModifiedDate", "2021-08-01T21:33:13"))
        .build();
      final var errors = new BeanPropertyBindingResult(beacon, "legacyBeacon");

      legacyBeaconValidator.validate(beacon, errors);

      assertNoFieldErrors(errors, "beacon[lastModifiedDate]");
    }

    @Test
    void shouldHaveNoErrors() {
      final var beacon = LegacyBeacon
        .builder()
        .beacon(
          Map.of(
            "hexId",
            "Hex me",
            "createdDate",
            "2020-08-01T21:33:13",
            "lastModifiedDate",
            "2021-08-01T21:33:13"
          )
        )
        .build();
      final var errors = new BeanPropertyBindingResult(beacon, "legacyBeacon");

      legacyBeaconValidator.validate(beacon, errors);

      assertNoFieldErrors(errors);
    }
  }
}
