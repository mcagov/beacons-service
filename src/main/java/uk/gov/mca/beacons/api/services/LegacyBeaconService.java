package uk.gov.mca.beacons.api.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.dto.BeaconSearchResultDTO;
import uk.gov.mca.beacons.api.exceptions.BeaconsValidationException;
import uk.gov.mca.beacons.api.gateways.LegacyBeaconGateway;
import uk.gov.mca.beacons.api.mappers.BeaconSearchResultMapper;
import uk.gov.mca.beacons.api.services.validation.LegacyBeaconValidator;

@Service
@Transactional
public class LegacyBeaconService {

  private final LegacyBeaconGateway legacyBeaconGateway;
  private final BeaconSearchResultMapper beaconSearchResultMapper;
  private final LegacyBeaconValidator legacyBeaconValidator;

  @Autowired
  public LegacyBeaconService(
    LegacyBeaconGateway legacyBeaconGateway,
    BeaconSearchResultMapper beaconSearchResultMapper,
    LegacyBeaconValidator legacyBeaconValidator
  ) {
    this.legacyBeaconGateway = legacyBeaconGateway;
    this.beaconSearchResultMapper = beaconSearchResultMapper;
    this.legacyBeaconValidator = legacyBeaconValidator;
  }

  public List<BeaconSearchResultDTO> findAllBeaconSearchResult() {
    final List<LegacyBeacon> allLegacyBeacons = legacyBeaconGateway.findAll();
    return beaconSearchResultMapper.getLegacyBeaconSearchResults(
      allLegacyBeacons
    );
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
