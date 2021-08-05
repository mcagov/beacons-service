package uk.gov.mca.beacons.api.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.dto.BeaconSearchResultDTO;
import uk.gov.mca.beacons.api.gateways.LegacyBeaconGateway;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.mappers.BeaconSearchResultMapper;

@Service
@Transactional
public class LegacyBeaconService {

  private final LegacyBeaconGateway legacyBeaconGateway;
  private final BeaconSearchResultMapper beaconSearchResultMapper;

  @Autowired
  public LegacyBeaconService(
    LegacyBeaconGateway legacyBeaconGateway,
    BeaconSearchResultMapper beaconSearchResultMapper
  ) {
    this.legacyBeaconGateway = legacyBeaconGateway;
    this.beaconSearchResultMapper = beaconSearchResultMapper;
  }

  public List<BeaconSearchResultDTO> findAllBeaconSearchResult() {
    final List<LegacyBeacon> allLegacyBeacons = legacyBeaconGateway.findAll();
    final List<BeaconSearchResultDTO> allLegacyBeaconsSearchResults = beaconSearchResultMapper.getLegacyBeaconSearchResults(
      allLegacyBeacons
    );
    return allLegacyBeaconsSearchResults;
  }

  public LegacyBeacon create(LegacyBeacon beacon) {
    return legacyBeaconGateway.save(beacon);
  }

  public void deleteAll() {
    legacyBeaconGateway.deleteAll();
  }
}
