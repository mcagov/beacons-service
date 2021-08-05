package uk.gov.mca.beacons.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.exceptions.BeaconsValidationException;
import uk.gov.mca.beacons.api.gateways.LegacyBeaconGateway;
import uk.gov.mca.beacons.api.services.validation.LegacyBeaconValidator;

@Service
@Transactional
public class LegacyBeaconService {

  private final LegacyBeaconGateway legacyBeaconGateway;
  private final LegacyBeaconValidator legacyBeaconValidator;

  @Autowired
  public LegacyBeaconService(
    LegacyBeaconGateway legacyBeaconGateway,
    LegacyBeaconValidator legacyBeaconValidator
  ) {
    this.legacyBeaconGateway = legacyBeaconGateway;
    this.legacyBeaconValidator = legacyBeaconValidator;
  }

  public LegacyBeacon create(LegacyBeacon beacon) {
    validate(beacon);
    return legacyBeaconGateway.save(beacon);
  }

  private void validate(LegacyBeacon beacon) {
    final var errors = new BeanPropertyBindingResult(beacon, "legacyBeacon");
    legacyBeaconValidator.validate(beacon, errors);

    if (errors.hasFieldErrors()) {
      throw new BeaconsValidationException(errors);
    }
  }

  public void deleteAll() {
    legacyBeaconGateway.deleteAll();
  }
}
