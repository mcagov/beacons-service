package uk.gov.mca.beacons.api.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.exceptions.BeaconsValidationException;
import uk.gov.mca.beacons.api.gateways.AccountHolderGateway;
import uk.gov.mca.beacons.api.gateways.EventGateway;
import uk.gov.mca.beacons.api.gateways.LegacyBeaconGateway;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.services.validation.LegacyBeaconValidator;

@Service
@Transactional
public class LegacyBeaconService {

  private final LegacyBeaconGateway legacyBeaconGateway;
  private final LegacyBeaconValidator legacyBeaconValidator;
  private final AccountHolderGateway accountHolderGateway;
  private final EventGateway eventGateway;

  @Autowired
  public LegacyBeaconService(
    LegacyBeaconGateway legacyBeaconGateway,
    LegacyBeaconValidator legacyBeaconValidator,
    AccountHolderGateway accountHolderGateway,
    EventGateway eventGateway
  ) {
    this.legacyBeaconGateway = legacyBeaconGateway;
    this.legacyBeaconValidator = legacyBeaconValidator;
    this.accountHolderGateway = accountHolderGateway;
    this.eventGateway = eventGateway;
  }

  public LegacyBeacon create(LegacyBeacon beacon) {
    validate(beacon);
    return legacyBeaconGateway.save(beacon);
  }

  public void deleteAll() {
    legacyBeaconGateway.deleteAll();
  }

  public Optional<LegacyBeacon> findById(UUID id) {
    return legacyBeaconGateway.findById(id);
  }

  private void validate(LegacyBeacon beacon) {
    final var errors = new BeanPropertyBindingResult(beacon, "legacyBeacon");
    legacyBeaconValidator.validate(beacon, errors);

    if (errors.hasFieldErrors()) {
      throw new BeaconsValidationException(errors);
    }
  }

  public Optional<List<LegacyBeacon>> findMatchingLegacyBeacons(Beacon beacon) {
    // TODO: Change RegistrationController integration tests to use TestSeeder methods, so test data is seeded
    // with valid relationships (e.g. AccountHolder).  This guard clause can then be removed.
    if (
      beacon.getHexId() == null || beacon.getAccountHolderId() == null
    ) return Optional.empty();

    AccountHolder accountHolder = accountHolderGateway.getById(
      beacon.getAccountHolderId()
    );

    return Optional.ofNullable(
      legacyBeaconGateway.findAllByHexIdAndEmail(
        beacon.getHexId(),
        accountHolder.getEmail()
      )
    );
  }

  public void claim(LegacyBeacon legacyBeacon) throws Exception {
    AccountHolder accountHolder = accountHolderGateway.getByEmail(
      legacyBeacon.getAssociatedEmailAddress()
    );

    legacyBeacon.claimFor(accountHolder);

    legacyBeaconGateway.save(legacyBeacon);
  }
}
