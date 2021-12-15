package uk.gov.mca.beacons.api.beacon.mappers;

import org.springframework.stereotype.Component;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolderId;
import uk.gov.mca.beacons.api.beacon.domain.Beacon;
import uk.gov.mca.beacons.api.beacon.domain.BeaconStatus;
import uk.gov.mca.beacons.api.beacon.rest.BeaconRegistrationDTO;

@Component("BeaconMapperV2")
public class BeaconMapper {

  public Beacon fromDTO(BeaconRegistrationDTO dto) {
    Beacon beacon = new Beacon();
    beacon.setHexId(dto.getHexId());
    beacon.setManufacturer(dto.getManufacturer());
    beacon.setModel(dto.getModel());
    beacon.setManufacturerSerialNumber(dto.getManufacturerSerialNumber());
    beacon.setReferenceNumber(dto.getReferenceNumber());
    beacon.setChkCode(dto.getChkCode());
    beacon.setBatteryExpiryDate(dto.getBatteryExpiryDate());
    beacon.setLastServicedDate(dto.getLastServicedDate());
    beacon.setMti(dto.getMti());
    beacon.setSvdr(dto.getSvdr());
    beacon.setCsta(dto.getCsta());
    beacon.setBeaconType(dto.getBeaconType());
    beacon.setProtocol(dto.getProtocol());
    beacon.setCoding(dto.getCoding());
    beacon.setBeaconStatus(BeaconStatus.NEW);
    beacon.setAccountHolderId(new AccountHolderId(dto.getAccountHolderId()));

    return beacon;
  }

  public BeaconRegistrationDTO toBeaconRegistrationDTO(Beacon beacon) {
    return BeaconRegistrationDTO
      .builder()
      .hexId(beacon.getHexId())
      .manufacturer(beacon.getManufacturer())
      .model(beacon.getModel())
      .manufacturerSerialNumber(beacon.getManufacturerSerialNumber())
      .referenceNumber(beacon.getReferenceNumber())
      .chkCode(beacon.getChkCode())
      .batteryExpiryDate(beacon.getBatteryExpiryDate())
      .lastServicedDate(beacon.getLastServicedDate())
      .mti(beacon.getMti())
      .svdr(beacon.getSvdr())
      .csta(beacon.getCsta())
      .beaconType(beacon.getBeaconType())
      .protocol(beacon.getProtocol())
      .coding(beacon.getCoding())
      .accountHolderId(beacon.getAccountHolderId().unwrap())
      .build();
  }
}
