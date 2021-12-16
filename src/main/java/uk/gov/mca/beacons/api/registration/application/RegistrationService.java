package uk.gov.mca.beacons.api.registration.application;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.beacon.application.BeaconService;
import uk.gov.mca.beacons.api.beacon.domain.Beacon;
import uk.gov.mca.beacons.api.beaconowner.application.BeaconOwnerService;
import uk.gov.mca.beacons.api.beaconowner.domain.BeaconOwner;
import uk.gov.mca.beacons.api.beaconuse.application.BeaconUseService;
import uk.gov.mca.beacons.api.beaconuse.domain.BeaconUse;
import uk.gov.mca.beacons.api.emergencycontact.application.EmergencyContactService;
import uk.gov.mca.beacons.api.emergencycontact.domain.EmergencyContact;
import uk.gov.mca.beacons.api.registration.domain.Registration;

@Transactional
@Service("CreateRegistrationServiceV2")
public class RegistrationService {

  private final BeaconService beaconService;
  private final BeaconOwnerService beaconOwnerService;
  private final BeaconUseService beaconUseService;
  private final EmergencyContactService emergencyContactService;

  @Autowired
  public RegistrationService(
    BeaconService beaconService,
    BeaconOwnerService beaconOwnerService,
    BeaconUseService beaconUseService,
    EmergencyContactService emergencyContactService
  ) {
    this.beaconService = beaconService;
    this.beaconOwnerService = beaconOwnerService;
    this.beaconUseService = beaconUseService;
    this.emergencyContactService = emergencyContactService;
  }

  // TODO: Add back the claim process
  public Registration register(Registration registration) {
    Beacon savedBeacon = beaconService.create(registration.getBeacon());
    registration.setBeaconId(savedBeacon.getId());
    BeaconOwner savedBeaconOwner = beaconOwnerService.create(
      registration.getBeaconOwner()
    );
    List<BeaconUse> savedBeaconUses = beaconUseService.createAll(
      registration.getBeaconUses()
    );
    List<EmergencyContact> savedEmergencyContacts = emergencyContactService.createAll(
      registration.getEmergencyContacts()
    );

    return Registration
      .builder()
      .beacon(savedBeacon)
      .beaconOwner(savedBeaconOwner)
      .beaconUses(savedBeaconUses)
      .emergencyContacts(savedEmergencyContacts)
      .build();
  }
}
