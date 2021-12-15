package uk.gov.mca.beacons.api.beacon.mappers;

import org.springframework.stereotype.Component;
import uk.gov.mca.beacons.api.beacon.domain.Beacon;
import uk.gov.mca.beacons.api.beacon.domain.BeaconStatus;
import uk.gov.mca.beacons.api.beacon.rest.CreateBeaconDTO;

@Component("BeaconMapperV2")
public class BeaconMapper {

  public Beacon fromDTO(CreateBeaconDTO dto) {
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

    return beacon;
  }
}
