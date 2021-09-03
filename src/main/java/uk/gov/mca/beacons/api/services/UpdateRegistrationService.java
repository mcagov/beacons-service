package uk.gov.mca.beacons.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.dto.UpdateRegistrationRequest;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

@Service
@Transactional
public class UpdateRegistrationService {

  private final BeaconsService beaconsService;

  @Autowired
  public UpdateRegistrationService(BeaconsService beaconsService) {
    this.beaconsService = beaconsService;
  }

  public Beacon update(UpdateRegistrationRequest request) {
    return beaconsService.update(request.getBeaconId(), request.getBeacon());
  }
}
